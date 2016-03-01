package Week4;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Created by berdar on 01.03.16.
 */
public class Board {
    private int[][] blocks;

    /**
     * @param blocks an N-by-N array of blocks
     *               Construct a board from blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException("Passed null argument to Week4.Board c-tor");
        int[][] newBlocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < newBlocks.length; i++) {
            newBlocks[i] = Arrays.copyOf(blocks[i], blocks.length);
        }
        this.blocks = newBlocks;
    }

    /**
     * @return Week4.Board dimension N (where blocks[i][j] = block in row i, column j)
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * @return Number of blocks out of place
     */
    public int hamming() {
        int hamming = 0;
        int curr = 1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < dimension(); j++) {
                if (i == j && j == dimension() - 1) break;
                if (blocks[i][j] != curr++) ++hamming;
            }
        }
        return hamming;
    }

    /**
     * @return Sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != 0) {
                    manhattan += Math.abs(i - (blocks[i][j] - 1) / dimension())
                            + Math.abs(j - (blocks[i][j] - 1) % dimension());
                }
            }
        }
        return manhattan;
    }

    /**
     * @return Is this board the goal board?
     */
    public boolean isGoal() {
        int curr = 1;
        for (int[] row : blocks) {
            for (int col : row) {
                if (col != curr++) return false;
                curr %= dimension() * dimension();
            }
        }
        return true;
    }

    /**
     * @return A board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        Random rn = new Random(12);
        int i1, j1, i2, j2;
        do {
            i1 = rn.nextInt(dimension());
            j1 = rn.nextInt(dimension());

            i2 = chooseRandom(i1);
            j2 = chooseRandom(j1);

        } while (blocks[i1][j1] == 0 || blocks[i2][j2] == 0);

        return swap(this, i1, j1, i2, j2);
    }

    /**
     * @param y - board
     * @return Is this board equal y?
     */
    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y.getClass().equals(Board.class))) return false;
        Board board = (Board) y;

        if (board.dimension() != this.dimension()) return false;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board.blocks[i][j] != this.blocks[i][j]) return false;
            }
        }
        return true;
    }

    /**
     * @return All neighboring boards
     */
    public Iterable<Board> neighbors() {
        int empi = 0, empj = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    empi = i;
                    empj = j;
                    break;
                }
            }
        }

        ArrayList<Board> neighbors = new ArrayList<>(4);
        if (empi != 0) neighbors.add(swap(this, empi - 1, empj, empi, empj));
        if (empi != dimension() - 1) neighbors.add(swap(this, empi + 1, empj, empi, empj));

        if (empj != 0) neighbors.add(swap(this, empi, empj - 1, empi, empj));
        if (empj != dimension() - 1) neighbors.add(swap(this, empi, empj + 1, empi, empj));

        return neighbors;
    }

    /**
     * @return String representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension()).append(System.getProperty("line.separator"));
        for (int[] row : blocks) {
            for (int col : row) {
                sb.append(col + " ");
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    /**
     * For unit tests (not graded)
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board b = new Board(blocks);
        System.out.println(b);
        System.out.println(b.twin());

    }

    private static Board swap(Board board, int i1, int j1, int i2, int j2) {
        Board b = new Board(board.blocks);
        int tmp = b.blocks[i2][j2];
        b.blocks[i2][j2] = b.blocks[i1][j1];
        b.blocks[i1][j1] = tmp;
        return b;
    }

    private int chooseRandom(int x) {
        Random rn = new Random(12);
        if (x == dimension() - 1) {
            return x - 1;
        } else if (x == 0) {
            return x + 1;
        } else {
            return rn.nextBoolean() ? x + 1 : x - 1;
        }
    }
}
