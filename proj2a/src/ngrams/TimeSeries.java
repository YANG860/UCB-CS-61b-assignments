package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /**
     * If it helps speed up your code, you can assume year arguments to your
     * NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here.
     */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */

    public TimeSeries(TimeSeries ts) {
        super();
        if (ts == null)
            return;

        for (Integer year : ts.keySet()) {
            Double data = ts.get(year);
            this.put(year, data);
        }
    }

    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        if (ts == null)
            throw new NullPointerException();

        

        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear)
                this.put(year, ts.get(year));

        }
    }

    /**
     * Returns all keySet for this TimeSeries (in any order).
     */
    public List<Integer> years() {

        ArrayList<Integer> arr = new ArrayList<>();
        for (Integer year : this.keySet()) {
            arr.add(year);
        }
        return arr;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {

        ArrayList<Double> arr = new ArrayList<>();
        for (Integer year : this.keySet()) {
            arr.add(this.get(year));
        }
        return arr;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other
     * words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should
     * return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned
     * TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */

    // Returns a new object
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries series = new TimeSeries(this);
        return series.add(ts);
    }

    // No new object
    public TimeSeries add(TimeSeries ts) {

        for (Integer year : ts.keySet()) {

            Double a = this.get(year);
            Double b = ts.get(year);
            if (a == null)
                this.put(year, b);
            else
                this.put(year, a + b);

        }

        return this;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by
     * the
     * value for the same year in TS. Should return a new TimeSeries (does not
     * modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */

    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries series = new TimeSeries(this);

        for (Integer year : this.keySet()) {

            Double a = this.get(year);
            Double b = ts.get(year);

            if (b == 0.0 || b == null)
                throw new IllegalArgumentException();
            else
                series.put(year, a / b);
        }

        return series;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
