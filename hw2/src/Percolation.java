
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import net.sf.saxon.expr.instruct.Block;

public class Percolation {
    // TODO: Add any necessary instance variables.

    public enum Block {
        OPEN, BLOCKED, WALL, WATER, END;
    }

    int size;
    int openSites;
    boolean percolates;
    Block[][] map;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = N;
        this.openSites = 0;
        this.percolates = false;
        this.map = new Block[size + 2][size + 2];
        initMap();
    }

    private void initMap() {
        for (int i = 0; i < size + 2; i++) {
            for (int j = 0; j < size + 2; j++) {
                if (i == 0) {
                    this.map[i][j] = Block.WATER;
                } else if (i == size + 1) {
                    this.map[i][j] = Block.END;
                } else if (j == 0 || j == size + 1) {
                    this.map[i][j] = Block.WALL;
                } else {
                    this.map[i][j] = Block.BLOCKED;
                }
            }
        }
    }

    public void checkOutOfBound(int row, int col) {
        boolean valid = (0 <= row && row <= this.size - 1) && (0 <= col && col <= this.size - 1);
        if (!valid)
            throw new IndexOutOfBoundsException();
    }

    public void open(int row, int col) {
        checkOutOfBound(row, col);
        if (this.isOpen(row, col)) {
            return;
        }

        this.openSites++;
        this.map[row + 1][col + 1] = Block.OPEN;
        boolean nextToWater = (isWater(row - 1, col)
                || isWater(row, col - 1)
                || isWater(row + 1, col)
                || isWater(row, col + 1));

        if (nextToWater)
            update(row, col);
    }

    public void update(int row, int col) {
        this.map[row + 1][col + 1] = Block.WATER;
        if (checkUpdate(row, col - 1)) {
            update(row, col - 1);
        }
        if (checkUpdate(row, col + 1)) {
            update(row, col + 1);
        }
        if (checkUpdate(row - 1, col)) {
            update(row - 1, col);
        }
        if (checkUpdate(row + 1, col)) {
            update(row + 1, col);
        }
    }

    private boolean checkUpdate(int row, int col) {

        Block b = this.map[row + 1][col + 1];
        if (b == Block.END)
            this.percolates = true;

        if (b == Block.OPEN)
            return true;
        else
            return false;
    }

    public boolean isOpen(int row, int col) {
        checkOutOfBound(row, col);
        Block b = this.map[row + 1][col + 1];
        return b != Block.BLOCKED;
    }

    public boolean isFull(int row, int col) {
        checkOutOfBound(row, col);
        return isWater(row, col);
    }

    private boolean isWater(int row, int col) {
        Block b = this.map[row + 1][col + 1];
        return b == Block.WATER;
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        return this.percolates;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
