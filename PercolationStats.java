import java.util.Random;

/**
 * Class that models percolation process and counts threshold
 */
public class PercolationStats {
    private final double CONFIDENCE_COEF = 1.96;  // confidence coefficient value
    private double[] openSites; // array that holds fraction of open_sites to N
    private Double mean = null;

    /**
     * c-tor and method that performs calculations
     *
     * @param N - size
     * @param T - iterations
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        Random rn = new Random();
        openSites = new double[T];
        int index = 0;

        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(N);
            int opened = 0, row, col;
            while (!perc.percolates()) {
                do {
                    row = rn.nextInt(N) + 1;
                    col = rn.nextInt(N) + 1;
                } while (perc.isOpen(row, col));
                perc.open(row, col);
                ++opened;
            }
            openSites[index++] = opened / (double) (N * N);
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        new PercolationStats(N, T);
    }


    /**
     * @return mean
     */
    public double mean() {
        double sum = 0d;
        for (double val : openSites) {
            sum += val;

        }
        mean = sum / openSites.length;
        return mean;
    }

    /**
     * @return standard deviation
     */
    public double stddev() {
        double cachedMean;
        if (this.mean != null) {
            cachedMean = this.mean;
        } else {
            cachedMean = mean();
        }
        double sum = 0d;
        for (double val : openSites) {
            sum += (val - cachedMean) * (val - cachedMean);
        }
        return Math.sqrt(sum / (openSites.length - 1));
    }

    /**
     * @return confidence lower bound
     */
    public double confidenceLo() {
        double cachedMean;
        if (this.mean != null) {
            cachedMean = this.mean;
        } else {
            cachedMean = mean();
        }
        return cachedMean - ((CONFIDENCE_COEF * stddev()) / Math.sqrt(openSites.length));
    }

    /**
     * @return confidence higher bound
     */
    public double confidenceHi() {
        double cachedMean;
        if (this.mean != null) {
            cachedMean = this.mean;
        } else {
            cachedMean = mean();
        }
        return cachedMean + ((CONFIDENCE_COEF * stddev()) / Math.sqrt(openSites.length));
    }
}
