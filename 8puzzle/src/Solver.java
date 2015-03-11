import java.util.Comparator;

public class Solver {

    private MinPQ<Node> queue;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        queue = new MinPQ<>(new NodeComparator());

        queue.insert(new Node(initial, 0, null));

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        return null;
    }

    private class Node {
        private final Board board;
        private final int moves;
        private final Node previous;

        Node(Board board, int moves, Node previous){
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node first, Node second) {
            int firstHamming = first.moves + first.board.hamming();

            int secondHamming = second.moves + second.board.hamming();

            if (firstHamming > secondHamming)
                return 1;
            else if (firstHamming < secondHamming)
                return -1;
            else {
                int firstManhattam = first.moves + first.board.manhattan();

                int secondManhattam = second.moves + second.board.manhattan();

                if(firstManhattam > secondManhattam)
                    return 1;
                else if (firstManhattam < secondManhattam)
                    return -1;
                else
                    return 0;
            }
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        System.out.println("hamming: " + initial.hamming());
        System.out.println("manhattan: " + initial.manhattan());
        System.out.println("dimension: " + initial.dimension());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}