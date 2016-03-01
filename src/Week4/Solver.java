package Week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by berdar on 01.03.16.
 */
public class Solver {
    private SearchNode solution;
    private boolean isSolvable = false;
    private MinPQ<SearchNode> solutionQueue;
    private MinPQ<SearchNode> twinSolutionQueue;

    /**
     * Immutable data class for MinPQ
     */
    private static class SearchNode {
        private final Board board;
        private int moves;
        private final SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrev() {
            return prev;
        }
    }

    /**
     * @param initial - Week4.Board
     *                find a solutionQueue to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {


        Comparator<SearchNode> comparator = (node1, node2) -> {
            int b1Prior = node1.getBoard().manhattan() + node1.getMoves();
            int b2Prior = node2.getBoard().manhattan() + node2.getMoves();
            if (b1Prior > b2Prior) return 1;
            else if (b1Prior < b2Prior) return -1;
            else return 0;
        };

        solutionQueue = new MinPQ<>(comparator);
        twinSolutionQueue = new MinPQ<>(comparator);

        solutionQueue.insert(new SearchNode(initial, 0, null));
        twinSolutionQueue.insert(new SearchNode(initial.twin(), 0, null));

        do {
            SearchNode node = solutionQueue.delMin();
            SearchNode twinNode = twinSolutionQueue.delMin();

            if (node.getBoard().isGoal()) {
                solution = node;
                isSolvable = true;
                break;
            } else if (twinNode.getBoard().isGoal()) {
                break;
            }

            node.getBoard().neighbors().forEach(
                    b -> {
                        if (node.getPrev() != null && b.equals(node.getPrev().getBoard())) return;
                        solutionQueue.insert(new SearchNode(b, node.getMoves() + 1, node));
                    });


            twinNode.getBoard().neighbors().forEach(
                    b -> {
                        if (twinNode.getPrev() != null && b.equals(twinNode.getPrev().getBoard()))
                            return;
                        twinSolutionQueue.insert(new SearchNode(b, twinNode.getMoves() + 1, twinNode));
                    });
        } while (true);
    }

    /**
     * @return Is the initial board isSolvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * @return Min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (!isSolvable) {
            return -1;
        } else {
            return solution.getMoves();
        }
    }

    /**
     * @return Sequence of boards in a shortest solutionQueue; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (solution == null) return null;
        ArrayList<Board> moves = new ArrayList<>(solution.getMoves());

        SearchNode node = solution;
        do {
            moves.add(node.getBoard());
            node = node.getPrev();

        } while (node != null);
        Collections.reverse(moves);
        return moves;
    }

    /**
     * Solve a slider puzzle
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solutionQueue to standard output
        if (!solver.isSolvable())
            StdOut.println("No solutionQueue possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
