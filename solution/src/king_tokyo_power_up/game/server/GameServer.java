package king_tokyo_power_up.game.server;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The game server handles communications with other clients and also holds the game state.
 */
public class GameServer {
    /**
     * The state of the game.
     */
    private Game game;

    /**
     * The server socket host.
     */
    private ServerSocket socket;

    /**
     * The scanner is used to provide user input when configuring server.
     */
    private Terminal terminal;

    /**
     * This game server player capacity.
     */
    private int maxPlayers = 3;

    /**
     * The number of players that have joined the server.
     */
    private int joinedPlayers = 0;

    /**
     * The host address of this server.
     */
    private InetAddress address;

    /**
     * The port used by this server.
     */
    private int port;


    /**
     * Constructs a new game server.
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
        terminal.writeString("Waiting for other players, press [ENTER] to start!");
        Thread thread = new Thread(() -> {
            while (joinedPlayers < maxPlayers) {
                try {
                    Socket s = socket.accept();

                    joinedPlayers += 1;
                    terminal.writeString("Player joined!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        while (true) {
            terminal.readString();
            if (joinedPlayers < Game.MIN_PLAYERS) {
                terminal.writeString("There is not enough players in this server minimum is " + Game.MIN_PLAYERS + "./n");
            } else {
                break;
            }
        }

        game.setup(joinedPlayers);
    }


    /**
     * Close this server.
     */
    public void close() {



    }

    /**
     * Configure the server.
     */
    public void configure() {
        terminal.writeString("\n\nLet's configure the server (press [ENTER] to use default settings)\n");
        terminal.writeString("How many players are playing (from 2 to 6)? (default is " + Game.DEFAULT_PLAYERS + ")\n");
        String errMessage = "Choose a number between " + Game.MIN_PLAYERS + " and " + Game.MAX_PLAYERS + ".\n";
        maxPlayers = terminal.readInt(Game.MIN_PLAYERS, Game.MAX_PLAYERS, Game.DEFAULT_PLAYERS, errMessage);

        terminal.writeString("Please enter the internet address of this server: (default is localhost)\n");
        address = terminal.readInetAddress(null, "Not a valid IP address\n");

        terminal.writeString("Please enter the port number of this server: (default is 2048)\n");
        port = terminal.readInt(0, 0xFFFF, 2048, "Not a valid port number should be between 0 and " + 0xFFFF + ".\n");
        System.out.println(maxPlayers + ", " + address + ", " + port);
    }
}
