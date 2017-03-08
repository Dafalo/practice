package intersource;


import java.util.*;

/**
 * This class implements of methods, which counts a frequency of words and sorts them.
 */
public class StatisticsCounter {
    /**
     * This method gets a massive of strings, map and calculate word's frequency in the titles
     *
     * @param words words from titles
     * @param src   K - word, V - amount of  the same word's entries in the map
     * @return map  where K - word, V - amount of  the same word's entries in the map
     */
    public Map<String, Integer> countFrequency(String[] words, Map<String, Integer> src) {
        Map<String, Integer> map = new HashMap<>(src);
        for (String word : words) {
            if (map.containsKey(word)) {
                int oldVal = map.get(word);
                int newVal = oldVal + 1;
                map.put(word, newVal);
            } else {
                map.put(word, 1);
            }
        }
        return map;
    }

    public Map<String, Integer> countFrequency(String[] words) {
        return countFrequency(words, new HashMap<>());
    }

    /**
     * This method sorts map
     * @param map K - word, V - amount of  the same word's entries in the map
     * @param amount top - number words by the frequencies text
     * @return List which contains requires amount of words
     */
    public List<Map.Entry<String, Integer>> getMostFrequent(Map<String, Integer> map, int amount) {
        List<Map.Entry<String, Integer>> vals = new ArrayList<>(map.entrySet());
        Collections.sort(vals, (a, b) -> Integer.compare(a.getValue(), b.getValue()));
        Collections.reverse(vals);
        return vals.subList(0, amount);
    }

    public List<Map.Entry<String, Integer>> getMostFrequent(Map<String, Integer> map) {
        return getMostFrequent(map, 10);
    }

}
