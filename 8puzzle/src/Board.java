import java.util.*;

public class Board {

    private int[][] blocks;

    private int[] inline;

    private int blocksOutOfPlace;

    private int dimension;

    private int[][] goalBlocks;

    private int manhattanDistance;

    // construct a board from an N-by-N array of blocks
    public Board(int[][] blocks) {

        this.dimension = blocks.length;

        this.inline = new int[dimension * dimension];

        int k = 0;

        goalBlocks = new int[dimension][dimension];

        blocksOutOfPlace = 0;

        this.blocks = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                goalBlocks[i][j] = goal(i, j);
                this.blocks[i][j] = blocks[i][j];
                /*
                 *   A iteração vai até o penúltimo elemento, pois o último
                 *   tem que ser zero.
                 *   Dessa forma assume-se o 0 na última posição como correto e
                 *   considera o zero como errado caso ele esteja em outra
                 *   posição.
                 *   Funciona mas não está de acordo com o enunciado do
                 *   problema.
                 */
                inline[k] = blocks[i][j];

                if (inline[k] != k + 1)
                    blocksOutOfPlace++;

                k++;
            }
        }

        /**** Manhattan ****/

        manhattanDistance = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (blocks[i][j] > 0)
                    manhattanDistance += manhattan(i, j, blocks[i][j]);

        /**** Manhattan ****/

    }

    private Board(int[][] blocks, int x0, int y0, int x1, int y1){
        // construct a board
        this(blocks);

        // exchange (x0,y0) with (x1,y1)
        int temp = this.blocks[x0][y0];
        this.blocks[x0][y0] = this.blocks[x1][y1];
        this.blocks[x1][y1] = temp;
    }

    private int manhattan(int i, int j, int value) {
        for (int m = 0; m < dimension; m++)
            for (int n = 0; n < dimension; n++)
                if (goalBlocks[m][n] == value)
                    return Math.abs(m - i) + Math.abs(n - j);

        throw new RuntimeException(String.format("value %s not found!", value));
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
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {

        return this.equals(this.blocks, goalBlocks);
    }

    // a board that is obtained by exchanging two adjacent blocks in the same
    // row
    public Board twin() {
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;

        if (!(y instanceof Board))
            return false;

        Board other = (Board) y;

        return equals(this.blocks, other.blocks);
    }

    private boolean equals(int[][] thisBlocks, int[][] otherBlocks) {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (thisBlocks[i][j] != otherBlocks[i][j])
                    return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Queue<Board> queue = new Queue<>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {

                    // posso mover para a esquerda
                    if (j > 0)
                        queue.enqueue(new Board(blocks, i, j, i, j - 1));

                    // posso mover para a direita
                    if (j < dimension - 1)
                        queue.enqueue(new Board(blocks, i, j, i, j + 1));

                    // posso mover para cima
                    if (i > 0)
                        queue.enqueue(new Board(blocks, i, j, i - 1, j));

                    // posso mover para baixo
                    if (i < dimension - 1)
                        queue.enqueue(new Board(blocks, i, j, i + 1, j));

                    return queue;
                }
            }
        }

        return queue;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
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