import java.util.Comparator;

public class Solver {

    private final boolean isSolvable;

    private MinPQ<Node> mainQueue;

    private MinPQ<Node> twinQueue;

    private Stack<Board> solution;

    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        mainQueue = new MinPQ<>(new NodeComparator());

        twinQueue = new MinPQ<>(new NodeComparator());

        mainQueue.insert(new Node(initial, 0, null));

        twinQueue.insert(new Node(initial.twin(), 0, null));

        Node node = mainQueue.min();

        Node twin = twinQueue.min();

        while (!node.board.isGoal() && !twin.board.isGoal()) {

            node = mainQueue.delMin();

            for (Board b : node.board.neighbors()) {
                if (node.previous == null || !b.equals(node.previous.board))
                    mainQueue.insert(new Node(b, node.moves + 1, node));
            }

            twin = twinQueue.delMin();

            for (Board b : twin.board.neighbors()) {
                if (twin.previous == null || !b.equals(twin.previous.board))
                    twinQueue.insert(new Node(b, twin.moves + 1, twin));
            }
        }

        isSolvable = node.board.isGoal();

        moves = node.moves;

        if (isSolvable) {

            solution = new Stack<>();

            solution.push(node.board);

            while (node.previous != null) {
                solution.push(node.previous.board);
                node = node.previous;
            }
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return moves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        return solution;
    }

    private class Node {
        private final Board board;
        private final int moves;
        private final Node previous;

        Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node first, Node second) {
            /*int firstHamming = first.moves + first.board.hamming();

            int secondHamming = second.moves + second.board.hamming();

            if (firstHamming > secondHamming)
                return 1;
            else if (firstHamming < secondHamming)
                return -1;
            else {
                int firstManhattam = first.moves + first.board.manhattan();

                int secondManhattam = second.moves + second.board.manhattan();

                if (firstManhattam > secondManhattam)
                    return 1;
                else if (firstManhattam < secondManhattam)
                    return -1;
                else
                    return 0;
            }*/

            int firstManhattam = first.moves + first.board.manhattan();

            int secondManhattam = second.moves + second.board.manhattan();

            if (firstManhattam > secondManhattam)
                return 1;
            else if (firstManhattam < secondManhattam)
                return -1;
            else
                return 0;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        //Stopwatch sw = new Stopwatch();

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        /*System.out.println("hamming: " + initial.hamming());
        System.out.println("manhattan: " + initial.manhattan());
        System.out.println("dimension: " + initial.dimension());
        System.out.println(initial);
        System.out.println(initial.twin());*/

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

        //System.out.println(String.format("time elapsed = %ss", sw
        // .elapsedTime()));
    }
}