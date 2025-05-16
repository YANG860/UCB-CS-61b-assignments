package deque;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Objects;

public class ArrayDeque61B<T> implements Deque61B<T> {

    int cap, size, head, tail, modcount;
    double expandFactor, shrinkFactor;

    T[] arr;

    public ArrayDeque61B() {
        this.modcount = 0;
        this.size = 0;
        this.cap = 8;

        this.tail = 0;
        this.head = this.cap - 1;

        this.expandFactor = 0.75;
        this.shrinkFactor = 0.25;

        this.arr = (T[]) new Object[8];
    }

    private void modHook() {
        this.modcount = (this.modcount + 1) % 2147483647;
    }

    private void addHook() {
        modHook();
        if (this.isTooLarge()) {
            this.expand();
        }
        this.size++;
    }

    private void removeHook() {
        modHook();
        if (this.isTooSmall()) {
            this.shrink();
        }
        this.size--;
    }

    @Override
    public void addLast(T x) {
        if (x==null) {
            throw new NullPointerException();
        }

        addHook();
        this.arr[this.tail] = x;
        this.tail = (this.tail + 1) % this.cap;
    }

    @Override
    public void addFirst(T x) {
        addHook();
        this.arr[this.head] = x;
        this.head = (this.head - 1 + this.cap) % this.cap;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T get(int index) {
        if (this.isEmpty()) {
            return null;
        }

        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        return this.arr[(this.head + 1 + index) % this.cap];
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<T>(this.size);
        for (int i = 0; i < this.size; i++) {
            returnList.add(this.get(i));
        }
        return returnList;
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        removeHook();
        this.head = (this.head + 1) % this.cap;
        return this.arr[this.head];
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        removeHook();
        this.tail = (this.tail - 1 + this.cap) % this.cap;
        return this.arr[this.tail];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    public boolean isTooLarge() {
        return this.expandFactor < (double) this.size / this.cap;
    }

    public boolean isTooSmall() {
        return this.shrinkFactor > (double) this.size / this.cap;
    }

    public void expand() {

        int newCap = this.cap * 2;
        T[] newArr = (T[]) new Object[newCap];

        for (int i = 0; i < this.size; i++) {
            newArr[i] = this.removeFirst();
        }

        this.cap = newCap;
        this.arr = newArr;
        this.head = this.cap - 1;
        this.tail = this.size;
    }

    public void shrink() {
        int newCap = this.cap / 2 + 1;
        T[] newArr = (T[]) new Object[newCap];

        for (int i = 0; i < this.size; i++) {
            newArr[i] = this.get(i);
        }

        this.cap = newCap;
        this.arr = newArr;
        this.head = this.cap - 1;
        this.tail = this.size;
    }

    private class ArrayDeque61BIterator implements Iterator<T> {

        int index;
        int size;
        int expectedModcount;

        ArrayDeque61BIterator() {
            this.index = 0;
            this.size = ArrayDeque61B.this.size;
            this.expectedModcount = ArrayDeque61B.this.modcount;
        }

        public void checkModify() {
            if (!(this.expectedModcount == ArrayDeque61B.this.modcount)) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            checkModify();
            return this.index < this.size;
        }

        @Override
        public T next() {
            checkModify();
            this.index++;
            return ArrayDeque61B.this.get(index - 1);
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof ArrayDeque61B other))
            return false;

        if (this.size() != other.size())
            return false;

        Iterator<?> iter1 = this.iterator();
        Iterator<?> iter2 = other.iterator();

        while (iter1.hasNext()) {
            if (!(Objects.equal(iter1.next(), iter2.next()))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Iterator<T> iter = this.iterator();
        builder.append("[");
        while (iter.hasNext()) {

            builder.append(iter.next().toString());
            if (iter.hasNext()) {
                builder.append(", ");
            }

        }
        builder.append("]");
        return builder.toString();
    }

}
