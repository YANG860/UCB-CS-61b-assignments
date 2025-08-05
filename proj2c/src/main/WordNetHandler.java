package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import wordnet.*;

public class WordNetHandler extends NgordnetQueryHandler {

    NGramMap ngm;
    WordNet wn;

    public WordNetHandler(NGramMap ngm, WordNet wn) {
        this.ngm = ngm;
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {

        List<String> hyponymGroup = this.wn.searchWordGroup(q.words(), q.ngordnetQueryType()==NgordnetQueryType.HYPONYMS);

        if (q.k() == 0)
            return hyponymGroup.toString();
        else
            return kBiggest(q.k(), hyponymGroup).toString();

    }

    public List<String> kBiggest(int k, List<String> words) {
        Map<String, Integer> m = new HashMap<>();

        for (String word : words) {
            m.put(word, this.ngm.countHistory(word).sum().intValue());
        }

        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> m.get(a) - m.get(b));
        for (String word : words) {
            pq.offer(word);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        List<String> ans = new ArrayList<>(pq);
        ans.sort(Comparator.naturalOrder());
        return ans;
    }

}
