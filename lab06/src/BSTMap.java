import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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

        private void clear() {
            this.key = null;
            this.value = null;
            this.right = null;
            this.left = null;
        }

        public boolean isNullDummy() {
            return (this.value == null && this.key == null && this.left == null && this.right == null) ? true : false;
        }

        public Node getInsertNode(K k) {
            if (this.isNullDummy())
                return this;
            else if (this.key.equals(k))
                return this;

            else if (k.compareTo(this.key) < 0)
                return this.left.getInsertNode(k);
            else
                return this.right.getInsertNode(k);
        }

        public Node getCloestNode(K k) {
            if (this.isNullDummy())
                return null;

            else if (this.key.equals(k))
                return this;

            else if (k.compareTo(this.key) < 0) {
                // Left
                if (this.left.isNullDummy())
                    return this;
                else
                    return this.left.getInsertNode(k);

            } else {
                // Right
                if (this.right.isNullDummy())
                    return this;
                else
                    return this.right.getInsertNode(k);
            }
        }

        public void keySetHelper(Set<K> set) {
            if (this.isNullDummy())
                return;

            this.left.keySetHelper(set);
            set.add(this.key);
            this.right.keySetHelper(set);
        }
    }

    int size;
    private Node root;

    public BSTMap() {
        this.size = 0;
        this.root = new Node();
    }

    public void put(K k, V v) {
        if (k == null) {
            throw new NullPointerException();
        }

        Node ans = root.getInsertNode(k);
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

        Node ans = root.getInsertNode(k);
        if (ans.isNullDummy())
            return null;
        else
            return ans.value;
    }

    public boolean containsKey(K k) {
        Node ans = root.getInsertNode(k);
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

        // ???

        if (k == null) {
            throw new NullPointerException();
        }

        Node x = this.root.getInsertNode(k);
        if (x == null || x.key != k)
            return null;

        else {
            this.size--;
            V ans = x.value;

            if (!x.left.isNullDummy()) {
                Node closest = x.left.getCloestNode(k);
                x.key = closest.key;
                x.value = closest.value;

                closest.key = closest.left.key;
                closest.value = closest.left.value;
                closest.right = closest.left.right;
                closest.left = closest.left.left;

            } else if (!x.right.isNullDummy()) {
                Node closest = x.right.getCloestNode(k);
                x.key = closest.key;
                x.value = closest.value;

                closest.key = closest.right.key;
                closest.value = closest.right.value;
                closest.left = closest.right.left;
                closest.right = closest.right.right;

            } else {
                x.clear();
            }
            return ans;
        }

    }

    public Set<K> keySet() {
        Set<K> ans = new TreeSet<>();
        this.root.keySetHelper(ans);
        return ans;
    }

    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }

}