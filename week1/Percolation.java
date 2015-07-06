package week1;

public class Percolation {
    private final int N;
    private QuickFindUF find;
    private int[] status;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        find = new QuickFindUF(n * n);
        status = new int[n * n];
    }

    public void open(int row, int col) {
        int pid = index(row, col);
        status[pid] = 1;
        switch (col % N) {
            case 0:
                int id = index(row, col - 1);
                find.union(pid, id);
                status[id] = 1;
                break;
            case 1:
                id = index(row, col + 1);
                find.union(pid, id);
                status[id] = 1;
                break;
            default:
                id = index(row, col + 1);
                find.union(pid, id);
                status[id] = 1;
                id = index(row, col - 1);
                find.union(pid, id);
                status[id] = 1;
        }

        switch (row % N) {
            case 0:
                find.union(pid, index(row - 1, col));
                break;
            case 1:
                find.union(pid, index(row + 1, col));
                break;
            default:
                find.union(pid, index(row - 1, col));
                find.union(pid, index(row + 1, col));
        }
    }

    public boolean isOpen(int row, int col) {
        return status[index(row, col)] == 1;
    }

    public boolean isFull(int row, int col) {
        return status[index(row, col)] == 2;
    }

    public boolean percolates() {
        return false;
    }

    private final int index(int i, int j) {
        return (i - 1) * N + j - 1;
    }
}
