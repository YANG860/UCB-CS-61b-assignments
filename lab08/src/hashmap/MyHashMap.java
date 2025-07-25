package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * A hash table-backed Map implementation.
 *
 * Assumes null keys will never be inserted, and does not resize down upon
 * remove().
 * 
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }

    }

    static final int defaultCapcity = 16;
    static final double defaultloadFactor = 0.75;
    /* Instance Variables */

    private Collection<Node>[] buckets;
    public int size, capacity;
    public double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(defaultCapcity, defaultloadFactor);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, defaultloadFactor);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor      maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {

        initBuckets(initialCapacity);
        this.capacity = initialCapacity;
        this.size = 0;

        if (loadFactor <= 0 || loadFactor >= 1) {
            throw new IllegalArgumentException();
        }
        this.loadFactor = loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * Note that that this is referring to the hash table bucket itself,
     * not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new ArrayList<>();
    }

    private void initBuckets(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }

        this.buckets = new Collection[capacity];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private int getIndex(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    private double getCurrentLoadFactor() {
        return (double) this.size / this.capacity;
    }

    @Override
    public void put(K key, V value) {

        Collection<Node> c = buckets[getIndex(key)];

        for (Node n : c) {
            if (n.key.equals(key)) {
                n.value = value;
                return;
            }
        }

        this.size++;

        if (getCurrentLoadFactor() >= this.loadFactor) {
            Collection<Node>[] oldBuckets = this.buckets;

            this.capacity = 2 * this.capacity;
            initBuckets(this.capacity);

            for (int i = 0; i < oldBuckets.length; i++) {
                for (Node n : oldBuckets[i]) {
                    this.buckets[getIndex(n.key)].add(n);
                }
            }
        }
        this.buckets[getIndex(key)].add(new Node(key, value));

    }

    @Override
    public V get(K key) {
        // TODO Auto-generated method stub

        for (Node n : buckets[getIndex(key)]) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> kSet = new HashSet<>();

        for (int i = 0; i < buckets.length; i++) {
            for (Node n : buckets[i]) {
                kSet.add(n.key);
            }
        }
        return kSet;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.capacity = defaultCapcity;
        initBuckets(defaultCapcity);
    }

    @Override
    public V remove(K key) {

        Collection<Node> c = buckets[getIndex(key)];
        for (Node n : c) {
            if (n.key.equals(key)) {
                this.size--;
                c.remove(n);
                return n.value;
            }
        }

        return null;

    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean containsKey(K key) {

        for (int i = 0; i < buckets.length; i++) {
            for (Node n : buckets[i]) {
                if (n.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
