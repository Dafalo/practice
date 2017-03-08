package aifimpl;

import intersource.INews;
import intersource.LineParser;
import intersource.StatisticsCounter;
import intersource.StatisticsPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * This class realize the method of interface iNews.
 */
public class AifImpl implements INews {
    private final StatisticsCounter statisticsCounter = new StatisticsCounter();
    private final URL url;

    /**
     *Make new url.
     */
    public AifImpl() {
        final String URL = "http://www.aif.ru/rss/news.php";

        URL url = null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.url = url;
    }

    /**
     * Get map from the getResultsMap, give it to count the word's frequency.
     */
    @Override
    public void getStats() {
        Map<String, Integer> map = getResultMap(url);
        Iterable<Map.Entry<String, Integer>> results = statisticsCounter.getMostFrequent(map, 10);
        print(results);
    }

    /**
     * Get name of the source.
     * @return string name of the source
     */
    @Override
    public String getName() {
        return "Aif";
    }

    /**
     * Get the information from url by BufferReader, which lines start with "<title><![C" and give it to the processLine
     * @param url link of the source
     * @return map where K - word, V - amount of  the same word's entries in the map
     */
    private Map<String, Integer> getResultMap(URL url) {
        LineParser lineParser = new LineParser();
        Map<String, Integer> resultMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("utf-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("<title><![C")) {
                    resultMap = processLine(line, lineParser, resultMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     *
     * @param line string from the title
     * @param lineParser class which has the parse method
     * @param resultMap map where K - word, V - amount of  the same word's entries in the map
     * @return map where K - word, V - amount of  the same word's entries in the map
     */
    private Map<String, Integer> processLine(String line, LineParser lineParser, Map<String, Integer> resultMap) {
        String title = line.substring(16, line.length() - 11);
        String[] words = lineParser.parseLine(title);
        return statisticsCounter.countFrequency(words, resultMap);
    }

    /**
     * Print map by the method print from StatisticsPrinter class.
     * @param resultList map where K - word, V - amount of  the same word's entries in the map
     */
    private void print(Iterable<Map.Entry<String, Integer>> resultList) {
        final StatisticsPrinter statisticsPrinter = new StatisticsPrinter();
        statisticsPrinter.print(resultList);
    }
}
