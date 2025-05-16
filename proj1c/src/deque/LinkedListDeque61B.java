package deque;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import com.google.common.base.Objects;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class Node {

        Node prev;
        Node next;
        T item;

        public Node(T x) {
            this.next = this;
            this.prev = this;
            this.item = x;
        }
    }

    int size;
    int modcount;
    Node sentinel;

    public LinkedListDeque61B() {
        this.size = 0;
        this.sentinel = new Node(null);
    }

    private void modHook() {
        this.modcount = (this.modcount + 1) % 2147483647;
    }

    public void addFirst(T x) {
        modHook();

        this.size++;

        Node newNode = new Node(x);
        Node R = this.sentinel.next;

        this.sentinel.next = newNode;
        newNode.prev = this.sentinel;

        R.prev = newNode;
        newNode.next = R;

    }

    public void addLast(T x) {

        modHook();

        this.size++;

        Node newNode = new Node(x);
        Node L = this.sentinel.prev;

        this.sentinel.prev = newNode;
        newNode.next = this.sentinel;

        L.next = newNode;
        newNode.prev = L;
    }

    public ArrayList<T> toList() {
        if (this.isEmpty()) {
            return null;
        }

        ArrayList<T> arr = new ArrayList<T>(this.size);
        for (Node i = this.sentinel.next; i != this.sentinel; i = i.next) {
            arr.add(i.item);
        }

        return arr;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public T removeFirst() {
        modHook();

        if (this.isEmpty()) {
            return null;
        }

        this.size--;
        T x = this.sentinel.next.item;

        Node R = this.sentinel.next.next;

        R.prev = this.sentinel;
        this.sentinel.next = R;

        return x;

    }

    public T removeLast() {
        modHook();

        if (this.isEmpty()) {
            return null;
        }

        this.size--;
        if (this.isEmpty()) {
            return null;
        }

        this.size--;
        T x = this.sentinel.prev.item;

        Node L = this.sentinel.prev.prev;

        L.next = this.sentinel;
        this.sentinel.prev = L;
        return x;

    }

    public T get(int index) {
        if (this.isEmpty()) {
            return null;
        }

        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node p = this.sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;

    }

    public T getRecursive(int x) {
        return this.get(x);
    }

    private class LinkedListDeque61BIterator implements Iterator<T> {

        Node p;
        int expectedModcount;

        LinkedListDeque61BIterator() {
            this.p = LinkedListDeque61B.this.sentinel.next;
            this.expectedModcount = LinkedListDeque61B.this.modcount;
        }

        public void checkModify() {
            if (!(this.expectedModcount == LinkedListDeque61B.this.modcount)) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            checkModify();
            return this.p == LinkedListDeque61B.this.sentinel;
        }

        @Override
        public T next() {
            checkModify();
            T x = p.item;
            this.p = this.p.next;
            return x;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDeque61BIterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof LinkedListDeque61B other)) {
            return false;
        }

        if (this.size() != other.size()) {
            return false;
        }

        Iterator<?> iter1 = this.iterator();
        Iterator<?> iter2 = this.iterator();

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
