package king_tokyo_power_up.game.server;

import king_tokyo_power_up.game.state.Game;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

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
    public GameServer() {
        game = new Game();
        terminal = new Terminal("Game Server", true);
    }


    /**
     * Start the server
     */
    public void start() {
        try {
            socket = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Configure the server.
     */
    public void configure() {
        terminal.writeString("Let's configure the server (press [ENTER] to use default settings)\n");
        terminal.writeString("How many players are playing?\n");
        String errMessage = "Choose a number between " + Game.MIN_PLAYERS + " through " + Game.MAX_PLAYERS + ".\n";
        maxPlayers = terminal.readInt(Game.MIN_PLAYERS, Game.MAX_PLAYERS, Game.DEFAULT_PLAYERS, errMessage);

        terminal.writeString("Please enter the IP address of this server: (default is localhost)\n");
        address = terminal.readInetAddress(null, "Not a valid IP address\n");

        terminal.writeString("Please enter the port number of this server: (default is 2048)\n");
        port = terminal.readInt(0, 0xFFFF, 2048, "Not a valid port number should be between 0 and " + 0xFFFF + ".\n");
    }
}
