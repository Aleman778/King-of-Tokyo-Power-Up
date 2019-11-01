package king_tokyo_power_up.game.util;

import java.io.IOException;
import java.util.Scanner;

/**
 * Dummy terminal used for testing.
 * Can contain predetermined responses.
 */
public class TerminalTest extends Terminal {

    private int index;
    public String[] lines;


    /**
     * Creates a new terminal with provided list of predetermined responses.
     */
    public TerminalTest(String[] lines) {
        super("", null);
        this.lines = lines;
    }

    /**
     * Use predetermined responses to answer questions from game.
     * @return next line
     */
    @Override
    public String nextLine() {
        if (lines == null) return "";
        else if (index >= lines.length) return "";
        else return lines[index++];
    }


    @Override
    public String readString() {
        return nextLine();
    }
}
