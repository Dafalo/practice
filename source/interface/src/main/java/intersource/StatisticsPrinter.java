package intersource;

import java.util.Map;

/**
 * This class implements the method print.
 */

public class StatisticsPrinter {
    /**
     * This method print the words from the map
     * @param results map K - word, V - amount of  the same word's entries in the map
     */
    public void print(Iterable<Map.Entry<String, Integer>> results){
        results.forEach(entry -> {
            String word =  entry.getKey();
            int frequency = entry.getValue();
            String result = String.format("Word '%s' occurred %d times.", word, frequency);
            System.out.println(result);
        });
    }
}
