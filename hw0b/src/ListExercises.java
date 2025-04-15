import java.util.List;
import java.util.ArrayList;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int sum = 0;
        for (Integer i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evens = new ArrayList<Integer>();

        for (Integer i : L) {
            if (i % 2 == 0) {
                evens.add(i);
            }
        }

        return evens;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> commons = new ArrayList<Integer>();

        for (Integer i : L1) {
            if (L2.contains(i)) {
                commons.add(i);
            }
        }

        return commons;
    }

    /**
     * Returns the number of occurrences of the given character in a list of
     * strings.
     */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int cnt = 0;
        for (String s : words) {
            for (char ch : s.toCharArray()) {
                if (ch == c) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
