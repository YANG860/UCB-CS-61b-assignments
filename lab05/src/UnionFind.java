import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    // TODO: Instance variables

    List<Integer> arr;
    int cap;

    /*
     * Creates a UnionFind data structure holding N items. Initially, all
     * items are in disjoint sets.
     */
    public UnionFind(int N) {
        this.cap = N;
        this.arr = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            this.arr.add(-1);
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -1 * this.arr.get(find(v));
    }

    /*
     * Returns the parent of V. If V is the root of a tree, returns the
     * negative size of the tree for which V is the root.
     */
    public int parent(int v) {
        return this.arr.get(v);
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /*
     * Returns the root of the set V belongs to. Path-compression is employed
     * allowing for fast search-time. If invalid items are passed into this
     * function, throw an IllegalArgumentException.
     */
    public int find(int v) {

        if (v >= this.cap) {
            throw new IllegalArgumentException();
        }
        if (this.arr.get(v) < 0) {
            return v;
        } else {
            this.arr.set(v, find(this.arr.get(v)));
            return this.arr.get(v);
        }
    }

    /*
     * Connects two items V1 and V2 together by connecting their respective
     * sets. V1 and V2 can be any element, and a union-by-size heuristic is
     * used. If the sizes of the sets are equal, tie break by connecting V1's
     * root to V2's root. Union-ing an item with itself or items that are
     * already connected should not change the structure.
     */
    public void union(int v1, int v2) {
        if (v1 == v2) {
            return;
        }

        int newSize = sizeOf(v2) + sizeOf(v1);
        newSize = -1 * newSize;
        if (sizeOf(v1) <= sizeOf(v2)) {

            this.arr.set(find(v2), newSize);
            this.arr.set(find(v1), find(v2));
        } else {
            this.arr.set(find(v1), newSize);
            this.arr.set(find(v2), find(v1));
        }
    }

}
