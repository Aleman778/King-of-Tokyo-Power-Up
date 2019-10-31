package king_tokyo_power_up.game.server;

import king_tokyo_power_up.game.client.ClientTest;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This is a slightly modified server class to more easily test the Requirements.
 * This makes the server and clients run on a single thread, they are designed to
 * run in separate programs.
 */
public class ServerTest extends GameServer {

    /**
     * Constructs a new king_tokyo_power_up.game server.
     * Use first {@link GameServer#configure()} to configure the server.
     * Use {@link GameServer#start()} to start the server.
     */
    public ServerTest(int maxPlayers) {
        super(null);
        this.maxPlayers = maxPlayers;
        try {
            socket = new ServerSocket(port, 50);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        game.setup(maxPlayers);
    }


    /**
     * The server accepts a new client to connect.
     */
    public void accept() {
        if (joinedPlayers < maxPlayers) {
            new Thread(() -> {
                try {
                    Socket s = socket.accept();
                    Monster monster = game.getAllMonsters().get(joinedPlayers);
                    String name = monster.getName();
                    String type = monster.getType().toLowerCase();
                    Terminal terminal = new Terminal(name, null);
                    terminal.connect(s);
                    terminal.writeString(name + "\n");
                    monster.setTerminal(terminal);
                    joinedPlayers += 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            throw new IllegalStateException("Cannot accept more players!");
        }
    }

    @Override
    public void start() {
        if (joinedPlayers < 2) {
            throw new IllegalStateException("There are not enough players joined!");
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game.exit();
        game.start(joinedPlayers);
    }
}
