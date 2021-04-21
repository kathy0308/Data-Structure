package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/*
public class PercolationStats {
   public PercolationStats(int N, int T, PercolationFactory pf)
   public double mean()
   public double stddev()
   public double confidenceLow()
   public double confidenceHigh()
}
 */

public class PercolationStats {
    private double[] temp;
    private int trial;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        trial = T;
        temp = new double[T];

        for (int i = 0; i < T; i++) {
            int openedNum = 0;
            Percolation test = pf.make(N);
            while (!test.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!test.isOpen(row, col)) {
                    test.open(row, col);
                    openedNum++;
                }
            }
            temp[i] = ((double) openedNum) / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(temp);
    }

    public double stddev() {
        return StdStats.stddev(temp);
    }

    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(trial));
    }

    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trial);
    }
}
