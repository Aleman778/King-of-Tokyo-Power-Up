package king_tokyo_power_up.game.server;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The king_tokyo_power_up.game server handles communications with other clients and also holds the king_tokyo_power_up.game state.
 */
public class GameServer {
    /**
     * The state of the king_tokyo_power_up.game.
     */
    protected Game game;

    /**
     * The server socket host.
     */
    protected ServerSocket socket;

    /**
     * The scanner is used to provide user input when configuring server.
     */
    protected Terminal terminal;

    /**
     * This king_tokyo_power_up.game server player capacity.
     */
    protected int maxPlayers = 2;

    /**
     * The number of players that have joined the server.
     */
    protected int joinedPlayers = 0;

    /**
     * The host address of this server.
     */
    protected InetAddress address;

    /**
     * The port used by this server.
     */
    protected int port = 2048;


    /**
     * Constructs a new king_tokyo_power_up.game server.
     * Use first {@link GameServer#configure()} to configure the server.
     * Use {@link GameServer#start()} to start the server.
     */
    public GameServer(Scanner scanner) {
        game = new Game();
        terminal = new Terminal("Game Server", scanner);
    }


    /**
     * Start the server
     */
    public void start() {
        try {
            socket = new ServerSocket(port, 50, address);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        game.setup(maxPlayers);
        terminal.writeString("Waiting for other players, press [ENTER] to start!\n");
        Thread thread = new Thread(() -> {
            while (joinedPlayers < maxPlayers && !socket.isClosed()) {
                try {
                    Socket s = socket.accept();
                    Monster monster = game.getAllMonsters().get(joinedPlayers);
                    String name = monster.getName();
                    String type = monster.getType().toLowerCase();
                    Terminal terminal = new Terminal(name, null);
                    terminal.connect(s);
                    terminal.writeString(name + "\n");
                    monster.setTerminal(terminal);
                    this.terminal.writeString(name + " the " + type + " monster has joined!\n");
                    joinedPlayers += 1;
                } catch (IOException e) {
                }
            }
        });
        thread.start();

        while (joinedPlayers < maxPlayers) {
            terminal.nextLine();
            if (joinedPlayers < Game.MIN_PLAYERS) {
                terminal.writeString("There is not enough players in this server minimum is " + Game.MIN_PLAYERS + "!\n");
            } else {
                break;
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            thread.join(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("STARTING GAME!!!");
        game.start(joinedPlayers);
    }


    /**
     * Close this server.
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        terminal.close();
    }


    /**
     * Configure the server.
     */
    public void configure() {
        try {
            terminal.writeString("\n\nLet's configure the server (press [ENTER] to use default settings)\n");
            terminal.writeString("How many players are playing (from 2 to 6)? (default is " + Game.DEFAULT_PLAYERS + ")\n");
            String errMessage = "Choose a number between " + Game.MIN_PLAYERS + " and " + Game.MAX_PLAYERS + ".\n";
            maxPlayers = terminal.readInt(Game.MIN_PLAYERS, Game.MAX_PLAYERS, Game.DEFAULT_PLAYERS, errMessage);

            terminal.writeString("Please enter the internet address of this server: (default is localhost)\n");
            address = terminal.readInetAddress(null, "Not a valid IP address\n");

            terminal.writeString("Please enter the port number of this server: (default is 2048)\n");
            port = terminal.readInt(0, 0xFFFF, 2048, "Not a valid port number should be between 0 and " + 0xFFFF + ".\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get the game state object.
     * @return the state of the game
     */
    public Game getGame() {
        return game;
    }
}
