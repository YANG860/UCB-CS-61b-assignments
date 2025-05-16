import java.lang.Math;
import java.util.ArrayList;
import java.util.List;;

public class ArrayDeque61B<T> implements Deque61B<T> {

    int cap, size, head, tail;
    double expandFactor, shrinkFactor;

    T[] arr;

    ArrayDeque61B() {
        this.size = 0;
        this.cap = 8;

        this.tail = 0;
        this.head = this.cap - 1;

        this.expandFactor = 0.75;
        this.shrinkFactor = 0.25;

        this.arr = (T[]) new Object[8];
    }

    @Override
    public void addLast(T x) {
        if (this.isTooLarge()) {
            this.expand();
        }

        this.size++;
        this.arr[this.tail] = x;
        this.tail = (this.tail + 1) % this.cap;

    }

    @Override
    public void addFirst(T x) {
        if (this.isTooLarge()) {
            this.expand();
        }

        this.size++;
        this.arr[this.head] = x;
        this.head = Math.floorMod(this.head, cap);
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
        if (this.isTooSmall()) {
            this.shrink();
        }

        this.head = (this.head + 1) % this.cap;
        return this.arr[this.head];
    }

    @Override
    public T removeLast() {
        if (this.isTooSmall()) {
            this.shrink();
        }

        this.tail = Math.floorMod(tail - 1, this.cap);
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
        this.cap = this.cap * 2;
        T[] newArr = (T[]) new Object[this.cap];

        for (int i = 0; i < this.size; i++) {
            newArr[i] = this.removeFirst();
        }

        this.arr = newArr;
        this.head = this.cap - 1;
        this.tail = this.size;

    }

    public void shrink() {
        this.cap = this.cap / 2 + 1;
        T[] newArr = (T[]) new Object[this.cap];

        for (int i = 0; i < this.size; i++) {
            newArr[i] = this.removeFirst();
        }

        this.arr = newArr;
        this.head = this.cap - 1;
        this.tail = this.size;
    }

}
