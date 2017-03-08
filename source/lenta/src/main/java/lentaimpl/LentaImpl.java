package lentaimpl;

import intersource.INews;
import intersource.LineParser;
import intersource.StatisticsCounter;
import intersource.StatisticsPrinter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * This class realize the method of iNews interface.
 */
public class LentaImpl implements INews {

    private final StatisticsCounter statisticsCounter = new StatisticsCounter();
    private final URL url;

    /**
     * Make a new url.
     */
    public LentaImpl() {
        final String URL = "https://api.lenta.ru/lists/latest";

        URL url = null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.url = url;
    }

    /**
     * Call the methods: getResults,print and getProperData.
     */
    @Override
    public void getStats() {
        JsonArray data = getProperData(url);
        Iterable<Map.Entry<String, Integer>> results = getResults(data);
        print(results);
    }

    /**
     * Get name of the source.
     * @return string name of the source
     */
    @Override
    public String getName() {
        return "Lenta";
    }

    /**
     * Call the methods getResultMap and get map.
     * @param data JsonArray contains string from the tag title
     * @return map which contains requires amount of words
     */
    private Iterable<Map.Entry<String, Integer>> getResults(JsonArray data) {
        Map<String, Integer> resultMap = getResultMap(data);
        return statisticsCounter.getMostFrequent(resultMap, 10);
    }

    /**
     * Connects with url and creates JsonObject, JsonReader and opens stream to get the words from array.
     * @param url link of the source
     * @return JsonArray contains string from the array headlines
     */
    private JsonArray getProperData(URL url) {
        JsonArray results = null;
        try (InputStream is = url.openStream()) {
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            results = obj.getJsonArray("headlines");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Extract from JsonArray the meaning by name "title", parse and got string.
     * @param results JsonArray
     * @return map where K - word, V - amount of  the same word's entries in the map
     */
    private Map<String, Integer> getResultMap(JsonArray results) {
        Map<String, Integer> resultMap = new HashMap<>();
        LineParser lineParser = new LineParser();
        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
            String line = result.getJsonObject("info").getString("title");
            String[] words = lineParser.parseLine(line);
            resultMap = statisticsCounter.countFrequency(words, resultMap);
        }
        return resultMap;
    }

    /**
     * Use the method StatisticsPrinter which implements in the StatisticsCounter Class.
     * @param resultList map where K - word, V - amount of  the same word's entries in the map
     */
    private void print(Iterable<Map.Entry<String, Integer>> resultList) {
        final StatisticsPrinter statisticsPrinter = new StatisticsPrinter();
        statisticsPrinter.print(resultList);
    }

}
