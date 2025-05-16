import java.util.ArrayList;

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
    Node sentinel;

    public LinkedListDeque61B() {
        this.size = 0;
        this.sentinel = new Node(null);
    }

    public void addFirst(T x) {
        this.size++;

        Node newNode = new Node(x);
        Node R = this.sentinel.next;

        this.sentinel.next = newNode;
        newNode.prev = this.sentinel;

        R.prev = newNode;
        newNode.next = R;

    }

    public void addLast(T x) {
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

    public T get(int x) {
        if (this.size - 1 < x || x < 0) {
            return null;
        } else {
            Node p = this.sentinel;
            for (int i = 0; i < x; i++) {
                p = p.next;
            }
            return p.item;
        }
    }

    public T getRecursive(int x) {
        return this.get(x);
    }

}
