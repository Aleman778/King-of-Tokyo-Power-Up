package king_tokyo_power_up.game.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class BotClient extends GameClient {
    /**
     * The random number generator to use.
     */
    private Random random;

    /**
     * Creates a new king_tokyo_power_up.game client.
     *
     * @param scanner
     */
    public BotClient(Scanner scanner) {
        super(scanner);
        random = new Random();
    }


    /**
     * Automatically responds to any communication with the server.
     * The BOT is very dumb and does random stuff.
     * @return true if connection should keep being alive
     */
    @Override
    protected boolean communication() {
        try {
            String message = terminal.readString();
            if (message.contains("QUERY")) {
                if (message.contains("ENTER")) {
                    terminal.writeString("\n");
                } else if (message.contains("REROLL")) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 1; i <= 6; i++) {
                        if (random.nextBoolean()) {
                            builder.append(i + ",");
                        }
                    }
                    int lastComma = builder.lastIndexOf(",");
                    if (lastComma >= 0) {
                        builder.deleteCharAt(lastComma);
                    }
                    terminal.writeString(builder.toString() + "\n");
                } else if (message.contains("LEAVE_TOKYO") || message.contains("PLAY_CARD")) {
                    if (random.nextBoolean()) {
                        terminal.writeString("yes\n");
                    } else {
                        terminal.writeString("no\n");
                    }
                } else if (message.contains("TRICK_OR_THREAT")) {
                    if (random.nextBoolean()) {
                        terminal.writeString("trick\n");
                    } else {
                        terminal.writeString("threat\n");
                    }
                } else if (message.contains("SHOP")) {
                    if (random.nextBoolean()) {
                        terminal.writeString(random.nextInt(4) + "\n");
                    } else {
                        terminal.writeString("\n");
                    }
                }
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
}
