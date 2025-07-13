package ngrams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import static spark.Spark.threadPool;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */

    HashMap<String, TimeSeries> wordCountMap;
    TimeSeries totalCount;

    public NGramMap(String wordsFilename, String countsFilename) {

        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec
        // for help.

        this.totalCount = new TimeSeries();
        this.wordCountMap = new HashMap<>();

        // handle counts
        try (BufferedReader reader = new BufferedReader(new FileReader(countsFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.length() == 0)
                    continue;

                handleCountsFileLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // handle words
        try (BufferedReader reader = new BufferedReader(new FileReader(wordsFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0)
                    continue;

                handleWordsFileLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleCountsFileLine(String s) {
        String[] values = s.split(",");

        Integer year = Integer.valueOf(values[0]);
        Double totalWords = Double.valueOf(values[1]);

        this.totalCount.put(year, totalWords);
    }

    private void handleWordsFileLine(String s) {
        String[] values = s.split("\t");

        String word = values[0];
        Integer year = Integer.valueOf(values[1]);
        Double count = Double.valueOf(values[2]);

        TimeSeries ts = this.wordCountMap.get(word);
        if (ts == null) {
            ts = new TimeSeries();
            this.wordCountMap.put(word, ts);
        }

        ts.put(year, count);

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both
     * ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's
     * TimeSeries. In other
     * words, changes made to the object returned by this function should not also
     * affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the
     * data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {

        TimeSeries ts = this.wordCountMap.get(word);
        if (ts == null)
            return new TimeSeries();
        else
            return new TimeSeries(ts, startYear, endYear);

    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a
     * link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by
     * this function
     * should not also affect the NGramMap. This is also known as a "defensive
     * copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries ts = this.wordCountMap.get(word);
        return new TimeSeries(ts);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in
     * all volumes.
     */
    public TimeSeries totalCountHistory() {

        return new TimeSeries(this.totalCount);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD
     * between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files,
     * returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {

        TimeSeries ts = this.countHistory(word, startYear, endYear);
        return ts.dividedBy(this.totalCount);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD
     * compared to all
     * words recorded in that year. If the word is not in the data files, returns an
     * empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries ts = this.countHistory(word);
        return ts.dividedBy(this.totalCount);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between
     * STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame,
     * ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
            int startYear, int endYear) {

        TimeSeries ts = new TimeSeries();

        for (String word : words) {
            ts.add(this.wordCountMap.get(word));
        }

        return ts.dividedBy(this.totalCount);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a
     * word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();

        for (String string : words) {
            ts.plus(this.wordCountMap.get(string));
        }

        return ts.dividedBy(this.totalCount);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
