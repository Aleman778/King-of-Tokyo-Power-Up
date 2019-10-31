package king_tokyo_power_up.game.client;

import king_tokyo_power_up.game.util.Terminal;

import java.util.Scanner;

public class ClientTest extends GameClient {

    /**
     * Creates a new king_tokyo_power_up.game client.
     */
    public ClientTest() {
        super(null);
    }

    /**
     * We only want to connect to the server, do not ask for input.
     * Input can be provided manually using the terminal.
     */
    @Override
    public void start() {
        connect();
    }


    /**
     * Get the terminal of this client.
     * @return the terminal.
     */
    public Terminal getTerminal() {
        return terminal;
    }
}
