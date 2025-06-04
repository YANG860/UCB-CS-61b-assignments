import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node() {
            this.key = null;
            this.value = null;
            this.left = null;
            this.right = null;
        }

        public Node(K k, V v) {
            this.set(k, v);
        }

        private void set(K k, V v) {
            this.key = k;
            this.value = v;
            this.right = new Node();
            this.left = new Node();
        }

        public boolean isNullDummy() {
            return (this.value == null && this.key == null && this.left == null && this.right == null) ? true : false;
        }

        public Node getCloest(K k) {
            if (this.isNullDummy())
                return this;
            else if (this.key.equals(k))
                return this;

            else if (k.compareTo(this.key) < 0)
                return this.left.getCloest(k);
            else
                return this.right.getCloest(k);
        }
    }

    int size;
    private Node root;

    public BSTMap() {
        this.size = 0;
        this.root = new Node();
    }

    public void put(K k, V v) {
        Node ans = root.getCloest(k);
        if (ans.isNullDummy()) {
            this.size++;
            ans.set(k, v);
        } else
            ans.value = v;
    }

    public V get(K k) {
        if (k == null) {
            throw new NullPointerException();
        }

        Node ans = root.getCloest(k);
        if (ans.isNullDummy())
            return null;
        else
            return ans.value;
    }

    public boolean containsKey(K k) {
        Node ans = root.getCloest(k);
        return !ans.isNullDummy();
    }

    public void clear() {
        this.size = 0;
        this.root = new Node();
    }

    public int size() {
        return this.size;
    }

    public V remove(K k) {
        throw new UnsupportedOperationException();
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

}