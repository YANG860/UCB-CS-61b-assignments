import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import deque.*;

public class ArrayDeque61BTest {
    private ArrayDeque61B<Integer> deque;

    @BeforeEach
    void setUp() {
        deque = new ArrayDeque61B<>();
    }

    // 测试基本操作
    @Test
    void testAddFirstAndRemoveFirst() {
        deque.addFirst(1);
        deque.addFirst(2);
        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    void testAddLastAndRemoveLast() {
        deque.addLast(1);
        deque.addLast(2);
        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.removeLast()).isEqualTo(1);
        assertThat(deque.isEmpty()).isTrue();
    }

    // 测试扩容/缩容
    @Test
    void testAutoExpandAndShrink() {
        // 触发扩容（默认容量8，扩容阈值0.75）
        for (int i = 0; i < 7; i++) { // 7/8 = 0.875 > 0.75
            deque.addLast(i);
        }
        assertThat(deque.size()).isEqualTo(7);

        // 触发缩容（缩容阈值0.25）
        for (int i = 0; i < 5; i++) { // 剩余2/8 = 0.25
            deque.removeLast();
        }
        assertThat(deque.size()).isEqualTo(2);
    }

    // 测试迭代器
    @Test
    void testIterato r() {
        deque.addLast(1);
        deque.addLast(2);
        Iterator<Integer> iter = deque.iterator();
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.next()).isEqualTo(1);
        assertThat(iter.next()).isEqualTo(2);
        assertThat(iter.hasNext()).isFalse();
    }

    // 测试并发修改检测
    @Test
    void testConcurrentModification() {
        deque.addLast(1);
        Iterator<Integer> iter = deque.iterator();
        deque.addLast(2); // 修改队列
        assertThrows(ConcurrentModificationException.class, iter::next);
    }

    // 测试equals和toString
    @Test
    void testEqualsAndToString() {
        ArrayDeque61B<Integer> other = new ArrayDeque61B<>();
        deque.addLast(1);
        other.addLast(1);
        assertThat(deque.equals(other)).isTrue();
        assertThat(deque.toString()).isEqualTo("[1]");
    }

    @Test
    public void testAddLast(){
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.get(0)).isEqualTo(1);

    }

} 