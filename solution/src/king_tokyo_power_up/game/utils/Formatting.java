package king_tokyo_power_up.game.utils;

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
     * @param n the number of spaces, needs to be positive
     * @return a string with n spaces
     */
    public static String getSpaces(int n) {
        return getRepeated(n, ' ');
    }


    /**
     * Returns a string with repeated characters n times,
     * used for more easily formatting strings.
     * Idea taken from: https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
     * @param n the number of characters, needs to be positive
     * @param c the character to repeat
     * @return a string containing the given character repeated n times
     */
    public static String getRepeated(int n, char c) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot create " + n + " characters, requires positive number");
        }
        return new String(new char[n]).replace('\0', c);
    }
}
