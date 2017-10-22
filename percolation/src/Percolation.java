import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final byte OPEN = 1;

    private final int N;

    private WeightedQuickUnionUF topConnections;

    private WeightedQuickUnionUF bottomConnections;

    private final boolean[] grid;

    private final int top;

    private final int bottom;

    private final int bottomRow;

    private boolean percolates;


    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be greater than zero");

        this.N = N;

        int length = N * N + 1; // adds two for the virtual top and virtual

        grid = new boolean[length];

        bottomRow = (length - N - 1);

        topConnections = new WeightedQuickUnionUF(length);

        bottomConnections = new WeightedQuickUnionUF(length);

        top = 0;

        bottom = grid.length - 1;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (!isValid(i, j))
            throw new IndexOutOfBoundsException(
                    String.format("cell is out of bounds (%s, %s)", i, j));

        if (isOpen(i, j))
            return;

        int index = map(i, j);

        grid[index] = true;

        /*
         * top row sites are always connected to the top (index < N) and
         * bottom row sites are always connected to the bottom (index >=
         * bottomRow).
         *
         * virtual top == 0
         * virtual bottom == grid.length - 1
         *
         */

        //corner cases
        if (N == 1) {
            percolates = true;
        }


        if (index <= N) {
            topConnections.union(index, top);
        } else if (!percolates && index > bottomRow) {
            bottomConnections.union(index, bottom);
        }


        if (isValid(i, j - 1)) {
            int k = map(i, j - 1);
            if (isOpen(k))
                connect(index, k);
        }


        if (isValid(i, j + 1)) {
            int k = map(i, j + 1);
            if (isOpen(k))
                connect(index, k);
        }

        if (isValid(i - 1, j)) {
            int k = map(i - 1, j);
            if (isOpen(k))
                connect(index, k);
        }

        if (isValid(i + 1, j)) {
            int k = map(i + 1, j);
            if (isOpen(k))
                connect(index, k);
        }

        /*
         * if the current index is connected to both, top and bottom
         * the system percolated at this point.
         * just flag it as percolated.
         */
        if (!percolates()) {
            percolates = topConnections.connected(index, top)
                    && bottomConnections.connected(index, bottom);
        }

        //System.out.println(percolates);
    }

    private void connect(int index, int k) {
        if (!topConnections.connected(index, k))
            topConnections.union(index, k);

        if (!percolates && !bottomConnections.connected(index, k))
            bottomConnections.union(index, k);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (!isValid(i, j))
            throw new IndexOutOfBoundsException(
                    String.format("cell is out of bounds (%s, %s)", i, j));

        return isOpen(map(i, j));
    }

    private boolean isOpen(int index) {
        return grid[index];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (!isValid(i, j))
            throw new IndexOutOfBoundsException(
                    String.format("cell is out of bounds (%s, %s)", i, j));
        int index = map(i, j);
        return isFull(index);
    }

    private boolean isFull(int index) {
        return isOpen(index) && topConnections.connected(index, top);
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    private boolean isValid(int i, int j) {
        return i > 0 && j > 0 && i <= N && j <= N;
    }

    /**
     * Maps the row (i) and the column (j) to a unique id
     *
     * @param i - row
     * @param j - column
     * @return the id mapped by i and j
     */
    private int map(int i, int j) {
        if (i == 1)
            return j;
        if (i == 2)
            return N + j;
        return (i - 1) * N + j;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);

        System.out.println(p.map(1, 5));
        System.out.println(p.map(1, 6));
        System.out.println(p.map(2, 7));


        p.connect(p.map(1, 5), p.map(1, 6));
        p.connect(p.map(1, 6), p.map(2, 7));

        System.out.println(p.map(1, 5));
        System.out.println(p.map(1, 6));
        System.out.println(p.map(2, 7));

    }   // test client (optional)
}
