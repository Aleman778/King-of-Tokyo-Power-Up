package king_tokyo_power_up.game.util;

import king_tokyo_power_up.KingTokyoPowerUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
                result.add(value);
            }
        }
        return result.toArray(new String[result.size()]);
    }
}
