package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    Comparator<T> c;

    public MaxArrayDeque61B(Comparator<T> c) {
        this.c = c;
    }

    public T max() {
        if (this.isEmpty()) {
            return null;
        }
        T max = this.get(0);

        Iterator<T> iter = this.iterator();
        while (iter.hasNext()) {
            T ele = iter.next();

            if (c.compare(max, ele) < 0) {
                max = ele;
            }
        }
        return max;

    }

}
