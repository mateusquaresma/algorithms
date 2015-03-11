import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Board {

    private int[][] blocks;

    private int blocksOutOfPlace;

    private int dimension;

    private int[][] goalBlocks;

    private int manhattanDistance = -1;
    
    private int[] ZERO = new int[2];

    // construct a board from an N-by-N array of blocks
    public Board(int[][] blocks) {

        this.dimension = blocks.length;

        int k = 0;
        blocksOutOfPlace = 0;

        goalBlocks = new int[dimension][dimension];
        this.blocks = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                if (i == j && i == dimension - 1)
                    goalBlocks[i][j] = 0;
                else
                    goalBlocks[i][j] = goal(i, j);

                if(blocks[i][j] == 0) {
                    ZERO[0] = i;
                    ZERO[1] = j;
                }
                
                this.blocks[i][j] = blocks[i][j];
                /*
                 * A iteração vai até o penúltimo elemento, pois o último tem que ser zero. Dessa forma assume-se o 0 na
                 * última posição como correto e considera o zero como errado caso ele esteja em outra posição. Funciona
                 * mas não está de acordo com o enunciado do problema.
                 */
                if (blocks[i][j] != 0 && blocks[i][j] != k + 1)
                    blocksOutOfPlace++;

                k++;
            }
        }
    }

    private Board(int[][] blocks, int x0, int y0, int x1, int y1) {
        // construct a board
        this(blocks);

        // exchange (x0,y0) with (x1,y1)

        int temp = this.blocks[x0][y0];
        this.blocks[x0][y0] = this.blocks[x1][y1];
        this.blocks[x1][y1] = temp;

        /*temp = inline[map(x0, y0)];
        inline[map(x0, y0)] = inline[map(x1, y1)];
        inline[map(x1, y1)] = temp;*/

        ZERO[0] = x1;
        ZERO[1] = y1;
    }

    /*private int map(final int x0, final int y0) {

        return 0;
    }*/

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
        if (manhattanDistance < 0) {
            manhattanDistance = 0;
            for (int i = 0; i < dimension; i++)
                for (int j = 0; j < dimension; j++)
                    if (blocks[i][j] > 0)
                        manhattanDistance += manhattan(i, j, blocks[i][j]);
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {

        return this.equals(this.blocks, goalBlocks);
    }

    // a board that is obtained by exchanging two adjacent blocks in the same
    // row
    public Board twin() {
        if (blocks[0][0] != 0 && blocks[0][1] != 0)
            return new Board(blocks, 0, 0, 0, 1);
        else
            return new Board(blocks, 1, 0, 1, 1);


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

    private boolean equals(int[][] thisBlocks, int[][] otherBlocks) {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (thisBlocks[i][j] != otherBlocks[i][j])
                    return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        List<Board> boards = new ArrayList<>();

        int i = ZERO[0];
        int j = ZERO[1];

        // posso mover para a esquerda
        if (j > 0)
            boards.add(new Board(blocks, i, j, i, j - 1));

        // posso mover para a direita
        if (j < dimension - 1)
            boards.add(new Board(blocks, i, j, i, j + 1));

        // posso mover para cima
        if (i > 0)
            boards.add(new Board(blocks, i, j, i - 1, j));

        // posso mover para baixo
        if (i < dimension - 1)
            boards.add(new Board(blocks, i, j, i + 1, j));

        Collections.sort(boards, new Comparator<Board>() {
            @Override
            public int compare(final Board b1, final Board b2) {
                if (b1.hamming() < b2.hamming())
                    return -1;
                else if (b1.hamming() > b2.hamming())
                    return 1;
                else {
                    if (b1.manhattan() < b2.manhattan())
                        return -1;
                    else if (b1.manhattan() > b2.manhattan())
                        return 1;
                    return 0;
                }
            }
        });
        
        return boards;
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