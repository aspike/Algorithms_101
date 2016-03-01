package Week4; /******************************************************************************
 * Compilation:  javac Week4.PuzzleChecker.java
 * Execution:    java Week4.PuzzleChecker filename1.txt filename2.txt ...
 * Dependencies: Week4.Board.java Week4.Solver.java
 * <p>
 * This program creates an initial board from each filename specified
 * on the command line and finds the minimum number of moves to
 * reach the goal state.
 * <p>
 * % java Week4.PuzzleChecker puzzle*.txt
 * puzzle00.txt: 0
 * puzzle01.txt: 1
 * puzzle02.txt: 2
 * puzzle03.txt: 3
 * puzzle04.txt: 4
 * puzzle05.txt: 5
 * puzzle06.txt: 6
 * ...
 * puzzle3x3-impossible: -1
 * ...
 * puzzle42.txt: 42
 * puzzle43.txt: 43
 * puzzle44.txt: 44
 * puzzle45.txt: 45
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PuzzleChecker {

    public static void main(String[] args) throws IOException {

        // for each command-line argument

        Files.walk(Paths.get("8puzzle")).forEach(path -> {
            if (Files.isRegularFile(path)) {
                {
                    // read in the board specified in the filename

                    In in = new In(String.valueOf(path));
                    int N = in.readInt();
                    int[][] tiles = new int[N][N];
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            tiles[i][j] = in.readInt();
                        }
                    }

                    // solve the slider puzzle
                    Board initial = new Board(tiles);
                    Solver solver = new Solver(initial);
                    StdOut.println(path + ": " + solver.moves());
                }
            }
        });
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + solver.moves());
        }
    }
}
