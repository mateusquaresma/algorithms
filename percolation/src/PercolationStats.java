public class PercolationStats {
    // perform T independent experiments on an N-by-N grid

    private double[] fractions;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Invalid parameters");

        fractions = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);

            int count = 0;

            while (!p.percolates()) {

                int row = StdRandom.uniform(N) + 1;
                int column = StdRandom.uniform(N) + 1;

                if (!p.isOpen(row, column)) {
                    p.open(row, column);
                    count++;
                }

            }

            double gridSize = N * N;

            double fraction = count / gridSize;
            //System.out.println("count: " + count + " fraction: " + fraction);
            fractions[i] = fraction;
        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(fractions.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(fractions.length));
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);
        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println(stats.confidenceLo());
        System.out.println(stats.confidenceHi());
    }

}
