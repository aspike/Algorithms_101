/**
 * Percolation data structure
 */
public class Percolation {
    private static final int CLOSED = 0;    // variable that represents CLOSED cell
    private static final int OPEN = 1;      // variable that represents OPEN cell
    private static final int FULL = 2;      // variable that represents FULL cell
    private final int N;                    // size of grid N * N
    private WeightedQuickUnionUF grid;      // actually grid
    private int[] status;                   // array with statuses for each cell
    private int firstRowComponent = -1;     // virtual component for first row
    private int lastRowComponent = -1;      // virtual component for last row

    /**
     * @param n - size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        grid = new WeightedQuickUnionUF(n * n);
        status = new int[n * n];
    }

    /**
     * method that opens cell
     *
     * @param i - row
     * @param j - column
     */
    public void open(int i, int j) {
        validate(i, j);
        int pid = index(i, j), id;
        status[pid] = OPEN;
        if (N == 1) {
            firstRowComponent = pid;
            lastRowComponent = pid;
            return;
        }

        switch (j % N) {
            case 0:                 // last col
                id = index(i, j - 1);
                if (status[id] != CLOSED) grid.union(pid, id);
                break;
            case 1:                 // first col
                id = index(i, j + 1);
                if (status[id] != CLOSED) grid.union(pid, id);
                break;
            default:
                id = index(i, j + 1);
                if (status[id] != CLOSED) grid.union(pid, id);
                id = index(i, j - 1);
                if (status[id] != CLOSED) grid.union(pid, id);
        }

        switch (i % N) {
            case 0:                 // last row
                id = index(i - 1, j);
                if (lastRowComponent == -1) {
                    lastRowComponent = pid;
                } else {
                    grid.union(pid, lastRowComponent);
                }
                if (status[id] != CLOSED) grid.union(pid, id);
                break;
            case 1:                 // first row
                status[pid] = FULL;
                id = index(i + 1, j);
                if (firstRowComponent == -1) {
                    firstRowComponent = pid;
                } else {
                    grid.union(pid, firstRowComponent);
                }
                if (status[id] != CLOSED) grid.union(pid, id);
                break;
            default:
                id = index(i - 1, j);
                if (status[id] != CLOSED) grid.union(pid, id);
                id = index(i + 1, j);
                if (status[id] != CLOSED) grid.union(pid, id);
        }
    }

    /**
     * @param i - row
     * @param j - column
     * @return true if cell is opened and false otherwise
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return status[index(i, j)] >= OPEN;
    }

    /**
     * @param i - row
     * @param j - column
     * @return true if cell is full and false otherwise
     */
    public boolean isFull(int i, int j) {
        validate(i, j);
        if (firstRowComponent == -1) return false;
        int id = index(i, j);
        if (status[id] == FULL) {
            return true;
        } else if (grid.connected(id, firstRowComponent)) {

            status[id] = FULL;
            return true;
        }
        return false;
    }

    /**
     * Method to check if system percolates
     *
     * @return true if system percolates and false if not
     */
    public boolean percolates() {
        if (firstRowComponent == -1 || lastRowComponent == -1) return false;
        boolean connected = grid.connected(firstRowComponent, lastRowComponent);
        return connected;
    }

    /**
     * Helper method used to validate input args for each method in class
     *
     * @param i - row
     * @param j - column
     */
    private void validate(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException("Arguments are: " + i + " , " + j);
        }
    }

    /**
     * Helper method to convert two-dimensional coord into one-dimensional
     *
     * @param i - row
     * @param j - column
     * @return index of cell in array
     */
    private int index(int i, int j) {
        return (i - 1) * N + j - 1;
    }
}
