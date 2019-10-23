package king_tokyo_powerup.game.utils;

/**
 * Utilities class for formatting strings.
 */
public class Formatting {
    /**
     * Should not be instantiated only static access.
     */
    private Formatting() { }

    /**
     * Returns a string with n spaces used for more easily
     * formatting strings.
     * Taken from:
     * https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
     * @param n the number of spaces, needs to be positive
     * @return a string with n spaces
     */
    public static String getSpaces(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot create " + n + " spaces, requires positive number");
        }
        return new String(new char[n]).replace('\0', ' ');
    }
}
