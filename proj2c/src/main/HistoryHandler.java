
package main;

import java.util.ArrayList;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.plotting.*;
import ngrams.NGramMap;
import ngrams.TimeSeries;

class HistoryHandler extends NgordnetQueryHandler {

    NGramMap ngm;

    HistoryHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>();

        for (String word : q.words()) {
            lts.add(ngm.weightHistory(word, q.startYear(), q.endYear()));
        }
        
        return Plotter.encodeChartAsString(Plotter.generateTimeSeriesChart(q.words(), lts));
    }

}