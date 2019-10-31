package king_tokyo_power_up.game.client;

import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * The king_tokyo_power_up.game client is used by players to interact
 * with the server via a connected terminal.
 */
public class GameClient {
    /**
     * Used to communicate with the server.
     */
    protected Terminal terminal;

    /**
     * The host address of this server.
     */
    protected InetAddress address;

    /**
     * The port used by this server.
     */
    protected int port = 2048;


    /**
     * Creates a new king_tokyo_power_up.game client.
     */
    public GameClient(Scanner scanner) {
        terminal = new Terminal("", scanner);
    }


    /**
     * Starts the client connects to the server.
     */
    public void start() {
        boolean keepAlive = connect();
        while (keepAlive) {
            // Sleep zzz... reduce CPU usage by allowing other processes to run.
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            keepAlive = communication();
        }
    }


    /**
     * Connects to the server.
     * @return true if connected successfully, false otherwise
     */
    protected boolean connect() {
        try {
            Socket socket;
            if (address != null) {
                socket = new Socket(address, port);
            } else {
                socket = new Socket("localhost", port);
            }
            terminal.connect(socket);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            String name = terminal.readString();
            terminal.setName(name);
            System.out.println("You are " + name + "!\nWaiting for other players to join...");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * The communication protocol with the server.
     * @return true if connection should keep being alive
     */
    protected boolean communication() {
        try {
            String message = terminal.readString();
            if (message.contains("QUERY")) {
                terminal.writeLine();
            } else if (message.equals("EXIT")) {
                return false;
            } else {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Closes the client i.e. closes the terminal
     * and its connection with the server.
     */
    public void close() {
        terminal.close();
    }


    /**
     * Configures the connection details.
     */
    public void configure() {
        try {
            terminal.writeString("\n\nLet's configure the client (press [ENTER] to use default settings)\n");
            terminal.writeString("Please enter the internet address of this server: (default is localhost)\n");
            address = terminal.readInetAddress(null, "Not a valid IP address\n");

            terminal.writeString("Please enter the port number of this server: (default is 2048)\n");
            port = terminal.readInt(0, 0xFFFF, 2048, "Not a valid port number should be between 0 and " + 0xFFFF + ".\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
