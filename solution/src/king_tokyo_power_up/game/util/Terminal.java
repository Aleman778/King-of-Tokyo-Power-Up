package king_tokyo_power_up.game.util;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
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
    private OutputStreamWriter output;


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
                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                this.output = new OutputStreamWriter(socket.getOutputStream(), "UTF8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Busy wait for the next line from a scanner.
     * @return the next line entered
     */
    public String nextLine() {
        if (scanner != null) {
            while (!scanner.hasNextLine()) {
                // Busy wait until next response
                // Sleep zzz... reduce CPU usage by allowing other processes to run.
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String nextLine = scanner.nextLine();
            if (socket != null) { // Sockes needs end line to properly send the message.
                nextLine += "\n";
            }
            return nextLine;
        }
        return "";
    }


    /**
     * Writes the line input from the scanner to the
     * connect socket or prints to this client (if not connected).
     * The terminal has to be interactive.
     */
    public void writeLine() {
        System.out.print(name + "> ");
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
                output.write(message);
                output.flush();
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
    public String readString() throws IOException {
        if (socket != null) {
            return input.readLine();
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
     * @throws SocketException only for socket terminals, handle connection resets
     * @return the integer read will be within the set bounds or the default value.
     */
    public int readInt(int min, int max, int def, String err) throws IOException {
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
    public InetAddress readInetAddress(InetAddress def, String err) throws IOException {
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


    /**
     * Sets the name of this client.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Closes the terminal, cannot be used after this unless it
     * is not connected or interactive.
     */
    public void close() {
        if (scanner != null)
            scanner.close();
        if (socket != null) {
            try {
                socket.close();
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
