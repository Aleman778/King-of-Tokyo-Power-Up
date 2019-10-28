package king_tokyo_power_up.game.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * A terminal is used to communicate to a socket or local machine. This class provides
 * some standard protocols that can be used to correctly communicate with one another.
 */
public class Terminal {
    /**
     * The name of the terminal displays in front of each command.
     */
    private String name;

    /**
     * The scanner is used to take input from the user.
     * If this terminal is not interactive then scanner is null.
     */
    private Scanner scanner;

    /**
     * The socket i.e. connection to another computer.
     */
    private Socket socket;

    /**
     * The input data retrieved from the socket.
     */
    private BufferedReader input;

    /**
     * The output data stream to send to the socket.
     */
    private DataOutputStream output;


    /**
     * Creates a new terminal with provided name and scanner.
     * Use {@link Terminal#connect(Socket)} to communicate with another machine.
     * @param name the name of this terminal
     * @param scanner set scanner if user input is allowed, set null otherwise.
     */
    public Terminal(String name, Scanner scanner) {
        this.name = name;
        this.scanner = scanner;
    }


    /**
     * Connect this terminal to a socket connection.
     * @param socket the socket to connect to
     */
    public void connect(Socket socket) {
        this.socket = socket;
        if (socket != null) {
            try {
                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.output = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String nextLine() {
        if (scanner != null) {
            while (!scanner.hasNextLine()) {
                // Busy wait until next response
            }
            return scanner.nextLine();
        }
        return "";
    }


    /**
     * Writes the line input from the scanner to the
     * connect socket or prints to this client (if not connected).
     * The terminal has to be interactive.
     */
    public void writeLine() {
        writeString(nextLine());
    }


    /**
     * Writes a string to the connected socket,
     * or prints to this client (if not connected).
     * @param message the message to send
     */
    public void writeString(String message) {
        if (socket != null) {
            try {
                output.writeBytes(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print(message);
        }
    }


    /**
     * Reads a string to the connected socket,
     * or from this client (if not connected).
     * For non connected terminal, has to be interactive.
     * @return the string read
     */
    public String readString() {
        if (socket != null) {
            try {
                writeString(name + "> ");
                return input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        } else if (scanner != null) {
            System.out.print(name + "> ");
            return nextLine();
        } else {
            throw new IllegalStateException("Cannot read string, no connection or local interactivity.");
        }
    }


    /**
     * Reads an integer with min and max bound.
     * If provided number is out of bounds or not a number at all
     * the error message is displayed and prompts to reenter.
     * @param min the minimum integer to allow
     * @param max the maximum integer to allow
     * @param def the default number to use if input is empty
     * @param err the error message to possibly display
     * @return the integer read will be within the set bounds or the default value.
     */
    public int readInt(int min, int max, int def, String err) {
        while (true) {
            String answer = readString();
            System.out.println(answer);
            if (answer.isEmpty())
                return def;

            try {
                int number = Integer.parseInt(answer);
                if (number < min || number > max) {
                    writeString(err);
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                writeString(err);
            }
        }
    }


    /**
     * Reads an ip address from string and if nothing is provided
     * then the default address is used instead.
     * @param def the default address
     * @param err the error message to display
     * @return the ip address read
     */
    public InetAddress readInetAddress(InetAddress def, String err) {
        while (true) {
            String answer = readString();
            if (answer.isEmpty()) {
                return def;
            }
            try {
                return InetAddress.getByName(answer);
            } catch (UnknownHostException e) {
                writeString("Not a valid internet address.\n");
            }
        }
    }


    /**
     * Returns the scanner that is being used by this terminal.
     * @return the terminal scanner, null if not interactive
     */
    public Scanner getScanner() {
        return scanner;
    }
}
