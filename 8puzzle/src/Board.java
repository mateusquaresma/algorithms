public class Board {

    private static final String TO_STRING_FORMAT = "%2d ";

    private static final String NEW_LINE = "\n";

    private short[][] blocks;

    private short blocksOutOfPlace;

    private short dimension;

    private short[][] goalBlocks;

    private short[] goalInline;

    private short manhattanDistance = -1;

    private short x0 = -1;

    private short y0 = -1;

    // construct a board from an N-by-N array of blocks
    public Board(int[][] blocks) {

        build(blocks, new short[blocks.length][blocks.length], false);
    }

    private Board(short[][] blocks, short[][] goalBlocks, int x0, int y0, int
            x1, int y1) {
        // construct a board
        build(blocks, goalBlocks);

        // exchange (x0,y0) with (x1,y1)
        short temp = this.blocks[x0][y0];
        this.blocks[x0][y0] = this.blocks[x1][y1];
        this.blocks[x1][y1] = temp;

        this.x0 = (short) x1;
        this.y0 = (short) y1;
    }

    private Board(int x0, int y0, int x1, int y1, short[][] blocks, short[][]
            goalBlocks) {
        // construct a board
        build(blocks, goalBlocks);
        short temp = this.blocks[x0][y0];
        this.blocks[x0][y0] = this.blocks[x1][y1];
        this.blocks[x1][y1] = temp;
    }

    private void build(int[][] blocks, short[][] goalBlocks, boolean
            goalProvided) {
        this.dimension = (short) blocks.length;

        int k = 0;
        blocksOutOfPlace = 0;
        this.goalBlocks = goalBlocks;
        this.blocks = new short[dimension][dimension];
        this.goalInline = new short[dimension * dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                if (!goalProvided) {
                    if (i == j && i == dimension - 1)
                        this.goalBlocks[i][j] = 0;
                    else
                        this.goalBlocks[i][j] = (short) goal(i, j);
                }

                if (blocks[i][j] == 0) {
                    x0 = (short) i;
                    y0 = (short) j;
                }

                this.blocks[i][j] = (short) blocks[i][j];
                /*
                 * A iteração vai até o penúltimo elemento, pois o último tem
                  * que ser zero. Dessa forma assume-se o 0 na
                 * última posição como correto e considera o zero como errado
                  * caso ele esteja em outra posição. Funciona
                 * mas não está de acordo com o enunciado do problema.
                 */
                if (blocks[i][j] != 0 && blocks[i][j] != ++k)
                    blocksOutOfPlace++;
            }
        }
    }

    private void build(short[][] blocks, short[][] goalBlocks) {
        this.dimension = (short) blocks.length;

        int k = 0;
        blocksOutOfPlace = 0;
        this.goalBlocks = goalBlocks;
        this.blocks = new short[dimension][dimension];
        this.goalInline = new short[dimension * dimension];
        this.blocks = blocks;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                /*if (!goalProvided) {
                    if (i == j && i == dimension - 1)
                        this.goalBlocks[i][j] = 0;
                    else
                        this.goalBlocks[i][j] = (short) goal(i, j);
                }*/

                if (blocks[i][j] == 0) {
                    x0 = (short) i;
                    y0 = (short) j;
                }

                //this.blocks[i][j] = blocks[i][j];
                /*
                 * A iteração vai até o penúltimo elemento, pois o último tem
                  * que ser zero. Dessa forma assume-se o 0 na
                 * última posição como correto e considera o zero como errado
                  * caso ele esteja em outra posição. Funciona
                 * mas não está de acordo com o enunciado do problema.
                 */
                if (blocks[i][j] != 0 && blocks[i][j] != ++k)
                    blocksOutOfPlace++;
            }
        }
    }

    private short manhattan(int i, int j, short value) {
        for (int m = 0; m < dimension; m++)
            for (int n = 0; n < dimension; n++)
                if (goalBlocks[m][n] == value)
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

        return blocksOutOfPlace;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanDistance < 0) {
            manhattanDistance = 0;
            for (int i = 0; i < dimension; i++)
                for (int j = 0; j < dimension; j++)
                    if (blocks[i][j] != 0)
                        manhattanDistance += manhattan(i, j, blocks[i][j]);
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {

        return this.equals(blocks, goalBlocks);
    }

    // a board that is obtained by exchanging two adjacent blocks in the same
    // row
    public Board twin() {
        if (blocks[0][0] != 0 && blocks[0][1] != 0)
            return new Board(0, 0, 0, 1, blocks, goalBlocks);
        else
            return new Board(1, 0, 1, 1, blocks, goalBlocks);
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

        return equals(this.blocks, other.blocks);
    }

    private boolean equals(short[][] thisBlocks, short[][] otherBlocks) {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (thisBlocks[i][j] != otherBlocks[i][j])
                    return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Queue<Board> boards = new Queue<>();

        int i = this.x0;
        int j = this.y0;

        // posso mover para a esquerda
        if (j > 0)
            boards.enqueue(new Board(blocks, goalBlocks, i, j, i, j - 1));

        // posso mover para a direita
        if (j < dimension - 1)
            boards.enqueue(new Board(blocks, goalBlocks, i, j, i, j + 1));

        // posso mover para cima
        if (i > 0)
            boards.enqueue(new Board(blocks, goalBlocks, i, j, i - 1, j));

        // posso mover para baixo
        if (i < dimension - 1)
            boards.enqueue(new Board(blocks, goalBlocks, i, j, i + 1, j));

        return boards;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + NEW_LINE);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format(TO_STRING_FORMAT, blocks[i][j]));
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

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}