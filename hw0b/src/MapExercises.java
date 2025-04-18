import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MapExercises {
    /**
     * Returns a map from every lower case letter to the number corresponding to
     * that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Fill in this function.
        Map<Character, Integer> map1 = new HashMap<Character, Integer>();
        char ch = 'a';
        int cnt = 1;
        for (; cnt <= 26;) {
            map1.put(ch, cnt);
            cnt++;
            ch++;
        }

        return map1;
    }

    /**
     * Returns a map from the integers in the list to their squares. For example, if
     * the input list
     * is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to
     * 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Fill in this function.
        Map<Integer, Integer> map2 = new HashMap<Integer, Integer>();
        for (Integer i : nums) {
            map2.put(i, i * i);
        }
        return map2;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {

        // TODO: Fill in this function.

        Map<String, Integer> map3 = new HashMap<String, Integer>();
        for (String word : words) {
            // 使用merge方法统计次数
            map3.merge(word, 1, Integer::sum);
        }

        return map3;
    }
}