package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

public class HistoryTextHandler extends NgordnetQueryHandler {

    NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        StringBuilder builder = new StringBuilder();

        for (String word : q.words()) {
            builder.append(word);
            builder.append(": ");
            builder.append(toJson(word, q.startYear(), q.endYear()));
            builder.append("\n");
        }
        return builder.toString();
    }

    private String toJson(String word, int start, int end) {
        if (start > end) {
            return "{}";
        }

        StringBuilder builder = new StringBuilder();
        TimeSeries ts = ngm.weightHistory(word, start, end);

        builder.append("{");
        for (int year = start; year <= end; year++) {
            Double data = ts.get(year);
            if (data == null)
                continue;

            builder.append(year);
            builder.append("=");

            builder.append(data.toString());
            builder.append(", ");
        }

        builder.delete(builder.length() - 2, builder.length());
        builder.append("}");
        return builder.toString();
    }

}
