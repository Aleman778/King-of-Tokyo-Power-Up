package king_tokyo_power_up.game.util;

import king_tokyo_power_up.KingTokyoPowerUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Static helper class for dealing with comma separated values files.
 */
public class CSVParser {
    /**
     * Only static access allowed.
     */
    private CSVParser() { }


    /**
     * Parses a file with a comma separated values, the comma can be any delimiter.
     * @param filepath the file path to read from
     * @param delimiter the delimiter to split
     * @return the array of parsed values
     * @throws IOException if there was a problem with reading the file
     */
    public static String[] parse(String filepath, String delimiter) throws IOException {
        InputStream input = KingTokyoPowerUp.class.getResourceAsStream(filepath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        ArrayList<String> result = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            for (String value : line.split(delimiter)) {
                result.add(value.trim());
            }
        }
        return result.toArray(new String[result.size()]);
    }


    /**
     * Parse comma separated values of unique integers from given string
     * @param string the string to parse
     * @param delimiter the delimiter
     * @return integer array of the numbers
     */
    public static int[] parseUniqueInts(String string, String delimiter) {
        Set<Integer> result = new HashSet<>();
        for (String value : string.split(delimiter)) {
            result.add(Integer.parseInt(value.trim()));
        }
        int[] array = new int[result.size()];
        int index = 0;
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            array[index++] = iter.next();
        }
        return array;
    }
}
