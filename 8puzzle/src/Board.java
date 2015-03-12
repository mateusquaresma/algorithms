public class Board {

    private static final String TO_STRING_FORMAT = "%2d ";

    private static final String NEW_LINE = "\n";

    private short hammingCount = -1;

    private short dimension;

    private short[] goalInline;

    private short[] inline;

    private short manhattanDistance = -1;

    private short x0 = -1;

    private short y0 = -1;

    // construct a board from an N-by-N array of blocks
    public Board(int[][] blocks) {
        this.dimension = (short) blocks.length;

        inline = new short[dimension * dimension];
        goalInline = new short[dimension * dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                if (i == j && i == dimension - 1)
                    goalInline[map(i, j)] = 0;
                else
                    goalInline[map(i, j)] = (short) goal(i, j);

                if (blocks[i][j] == 0) {
                    x0 = (short) i;
                    y0 = (short) j;
                }

                inline[map(i, j)] = (short) blocks[i][j];
            }
        }
    }

    private Board(short[] blocks, short[] goalBlocks, int x0, int y0, int
            x1, int y1) {

        dimension = (short) Math.sqrt(goalBlocks.length);
        inline = new short[dimension * dimension];

        goalInline = goalBlocks;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                inline[map(i, j)] = blocks[map(i, j)];
            }
        }

        short temp = inline[map(x0, y0)];
        inline[map(x0, y0)] = inline[map(x1, y1)];
        inline[map(x1, y1)] = temp;
    }

    private short manhattan(int i, int j, short value) {
        for (int m = 0; m < dimension; m++)
            for (int n = 0; n < dimension; n++)
                if (goalInline[map(m, n)] == value)
                    return (short) (Math.abs(m - i) + Math.abs(n - j));

        throw new RuntimeException();
    }

    // (where blocks[i][j] = block in row i, column j)
    // board dimension N
    public int dimension() {

        return dimension;
    }

    // number of blocks out of place
    public int hamming() {

        if (hammingCount < 0) {
            hammingCount = 0;
            for (int m = 0; m < dimension; m++)
                for (int n = 0; n < dimension; n++)
                    if (inline[map(m, n)] != 0
                            && inline[map(m, n)] != goalInline[map(m, n)])
                        hammingCount++;
        }

        return hammingCount;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanDistance < 0) {
            manhattanDistance = 0;
            for (int i = 0; i < dimension; i++)
                for (int j = 0; j < dimension; j++)
                    if (inline[map(i, j)] != 0)
                        manhattanDistance += manhattan(i, j, inline[map(i, j)]);
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {

        return this.equals(inline, goalInline);
    }

    // a board that is obtained by exchanging two adjacent blocks in the same
    // row
    public Board twin() {
        Board twin;
        if (inline[map(0, 0)] != 0 && inline[map(0, 1)] != 0)
            twin = new Board(inline, goalInline, 0, 0, 0, 1);
        else
            twin = new Board(inline, goalInline, 1, 0, 1, 1);

        twin.x0 = this.x0;
        twin.y0 = this.y0;
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;

        if (!(y instanceof Board))
            return false;

        Board other = (Board) y;

        if (this.dimension != other.dimension)
            return false;

        return equals(this.inline, other.inline);
    }

    private boolean equals(short[] thisBlocks, short[] otherBlocks) {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (thisBlocks[map(i, j)] != otherBlocks[map(i, j)])
                    return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Queue<Board> boards = new Queue<>();

        int i = this.x0;
        int j = this.y0;

        // posso mover para a esquerda
        if (j > 0) {
            Board neighbor = new Board(inline, goalInline, i, j, i, j - 1);
            neighbor.x0 = (short) i;
            neighbor.y0 = (short) (j - 1);
            boards.enqueue(neighbor);
        }

        // posso mover para a direita
        if (j < dimension - 1) {
            Board neighbor = new Board(inline, goalInline, i, j, i, j + 1);
            neighbor.x0 = (short) i;
            neighbor.y0 = (short) (j + 1);
            boards.enqueue(neighbor);
        }

        // posso mover para cima
        if (i > 0) {
            Board neighbor = new Board(inline, goalInline, i, j, i - 1, j);
            neighbor.x0 = (short) (i - 1);
            neighbor.y0 = (short) j;
            boards.enqueue(neighbor);
        }

        // posso mover para baixo
        if (i < dimension - 1) {
            Board neighbor = new Board(inline, goalInline, i, j, i + 1, j);
            neighbor.x0 = (short) (i + 1);
            neighbor.y0 = (short) j;
            boards.enqueue(neighbor);
        }
        return boards;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + NEW_LINE);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format(TO_STRING_FORMAT, inline[map(i, j)]));
            }
            s.append(NEW_LINE);
        }
        return s.toString();
    }

    private int goal(int i, int j) {
        if (i == 0)
            return j + 1;
        if (i == 1)
            return dimension + j + 1;
        if (i == dimension - 1 && j == dimension - 1)
            return 0;

        return i * dimension + j + 1;
    }

    /**
     * Maps the row (i) and the column (j) to a unique id
     *
     * @param i - row
     * @param j - column
     * @return the id mapped by i and j
     */
    private int map(int i, int j) {
        if (i == 0)
            return j;
        if (i == 1)
            return dimension + j;
        return i * dimension + j;
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}