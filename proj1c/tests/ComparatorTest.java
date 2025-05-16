import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ComparatorTest {

    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    private static class DescOrder implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return -(a - b);
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void orderTest() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new DescOrder());
        mad.addFirst(1);
        mad.addLast(3);
        mad.addLast(-1);
        mad.addLast(7);
        mad.addFirst(12);
        assertThat("").isEqualTo(-1);
    }

}