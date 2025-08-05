
package wordnet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class WordNet {

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

    public WordNet(String synsetFile, String hyponymFile) {
        this.id2node = new HashMap<>();
        this.word2id = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(synsetFile));
            String line;
            while ((line = reader.readLine()) != null) {

                String[] s = line.split(",");
                this.newNode(s[1], Integer.valueOf(s[0]));
            }
            reader.close();

            reader = new BufferedReader(new FileReader(hyponymFile));

            while ((line = reader.readLine()) != null) {

                String[] s = line.split(",");
                for (int i = 1; i < s.length; i++)
                    this.setHyponymOf(Integer.valueOf(s[0]), Integer.valueOf(s[i]));
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("File Not Fount");
        }

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

    public Set<String> searchWord(String word, boolean isHyponym) {

        HashSet<Integer> accessibleNodes = new HashSet<>();

        for (Integer id : this.word2id.get(word)) {
            searchHelper(accessibleNodes, id, isHyponym);
        }

        HashSet<String> ans = new HashSet<>();

        for (Integer id : accessibleNodes) {
            for (String synonym : this.id2node.get(id).synset) {
                ans.add(synonym);
            }

        }
        return ans;

    }

    public void searchHelper(HashSet<Integer> traversed, Integer id, boolean isHyponym) {
        if (traversed.contains(id)) {
            return;
        }

        ArrayList<Integer> nextNodes;
        if (isHyponym)
            nextNodes = this.id2node.get(id).Hyponyms;
        else
            nextNodes = this.id2node.get(id).Hypernyms;


        traversed.add(id);
        for (Integer next : nextNodes) {
            searchHelper(traversed, next, isHyponym);
        }
    }

    public List<String> searchWordGroup(List<String> words, boolean isHyponym) {
        if (words.size() == 0) {
            return new ArrayList<>();
        }

        Set<String> ans = searchWord(words.get(0), isHyponym);
        for (int i = 1; i < words.size(); i++) {
            ans.retainAll(searchWord(words.get(i), isHyponym));
        }

        return new ArrayList<>(new TreeSet<>(ans));

    }

}
