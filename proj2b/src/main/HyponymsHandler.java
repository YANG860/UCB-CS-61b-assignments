package main;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {

    private class WordNet {

        HashMap<String, ArrayList<Integer>> word2id;
        HashMap<Integer, Node> id2node;

        private class Node {

            ArrayList<String> synset;
            ArrayList<Integer> Hyponyms;
            ArrayList<Integer> Hypernyms;

            public Node(ArrayList<String> synset) {

                this.synset = synset;
                this.Hypernyms = new ArrayList<>(10);
                this.Hyponyms = new ArrayList<>(10);
            }

        }

        public WordNet() {
            this.id2node = new HashMap<>();
            this.word2id = new HashMap<>();
        }

        public void newNode(String synset, Integer id) {

            ArrayList<String> words = new ArrayList<>(Arrays.asList(synset.split(" ")));
            for (String word : words) {

                ArrayList<Integer> idList = this.word2id.get(word);
                if (idList == null) {
                    idList = new ArrayList<>(10);
                    idList.add(id);
                    this.word2id.put(word, idList);
                } else {
                    idList.add(id);
                }

            }

            Node n = new Node(words);
            this.id2node.put(id, n);
        }

        public void setHyponymOf(Integer id, Integer next) {
            this.id2node.get(id).Hyponyms.add(next);
            this.id2node.get(next).Hypernyms.add(id);
        }

        public HashSet<String> getHyponymsOf(String word) {

            HashSet<Integer> accessibleNodes = new HashSet<>();

            for (Integer id : this.word2id.get(word)) {
                getHyponymsHelper(accessibleNodes, id);
            }

            HashSet<String> ans = new HashSet<>();

            for (Integer id : accessibleNodes) {
                for (String synonym : this.id2node.get(id).synset) {
                    ans.add(synonym);
                }

            }
            return ans;
        }

        private void getHyponymsHelper(HashSet<Integer> traversed, Integer id) {
            if (traversed.contains(id)) {
                return;
            }

            traversed.add(id);
            for (Integer next : this.id2node.get(id).Hyponyms) {
                getHyponymsHelper(traversed, next);
            }
        }

        public ArrayList<String> getHyponymGroup(List<String> words) {
            if (words.size() == 0) {
                return new ArrayList<>();
            }

            HashSet<String> ans = getHyponymsOf(words.get(0));
            for (int i = 1; i < words.size(); i++) {
                ans.retainAll(getHyponymsOf(words.get(i)));
            }

            return new ArrayList<>(new TreeSet<>(ans));
        }

    }

    WordNet wn;

    public HyponymsHandler(String synsetFile, String hyponymFile) {

        this.wn = new WordNet();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(synsetFile));
            String line;
            while ((line = reader.readLine()) != null) {

                String[] s = line.split(",");
                this.wn.newNode(s[1], Integer.valueOf(s[0]));
            }
            reader.close();

            reader = new BufferedReader(new FileReader(hyponymFile));

            while ((line = reader.readLine()) != null) {

                String[] s = line.split(",");
                for (int i = 1; i < s.length; i++)
                    this.wn.setHyponymOf(Integer.valueOf(s[0]), Integer.valueOf(s[i]));

            }
            reader.close();

        } catch (Exception e) {
            System.out.println("File Not Fount");
        }

    }

    @Override
    public String handle(NgordnetQuery q) {

        return this.wn.getHyponymGroup(q.words()).toString();
    }

}
