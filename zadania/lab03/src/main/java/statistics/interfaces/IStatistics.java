package statistics.interfaces;

public interface IStatistics {
    double getMean(double [] data);

    double[] getMean(double[][] data);

    double getVariance(double[] data);

    double[] getVariance(double[][] data);

    double getStdDev(double[] data);

    double[] getStdDev(double[][] data);

    double tolerance(double[][] x, double tol);
}
