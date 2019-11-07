package speakerrecognition.data;

import matrixes.Matrixes;
import statistics.Statistics;

public class KMeansWrapper {
    private int numOfClusters;
    private int numOfRows;
    private int numOfCols;
    private double[][] data;
    private int n_iter = 0;
    private int n_init = 10;
    private int max_iter = 300;
    private double tol = 0.0001;

    private double[][] best_cluster_centers = null;
    private int[] best_labels = null;
    private double best_inertia = Double.MAX_VALUE;
    private int n_iter_ = 0;


    public void setData(double[][] data) {
        this.data = data;
    }


    public void setBest_cluster_centers(double[][] best_cluster_centers) {
        this.best_cluster_centers = best_cluster_centers;
    }

    public void setBest_labels(int[] best_labels) {
        this.best_labels = best_labels;
    }

    public void setBest_inertia(double best_inertia) {
        this.best_inertia = best_inertia;
    }

    public int getNumOfClusters() {
        return numOfClusters;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    public double[][] getData() {
        return data;
    }

    public int getN_init() {
        return n_init;
    }

    public int getMax_iter() {
        return max_iter;
    }

    public double getTol() {
        return tol;
    }

    public double[][] getBest_cluster_centers() {
        return best_cluster_centers;
    }

    public double getBest_inertia() {
        return best_inertia;
    }

    public KMeansWrapper(double[][] x, int numOfClust) {
        this.numOfClusters = numOfClust;
        double tol = 0.0001;
        this.tol = Statistics.tolerance(x, tol);
        this.numOfRows = x.length;
        this.numOfCols = x[0].length;
        this.data = Matrixes.copy2dArray(x);
        this.best_cluster_centers = new double[numOfClust][x[0].length];
        this.best_labels = new int[x.length];

    }

    public KMeansWrapper(KMeansWrapper other) {
        this.numOfClusters = other.numOfClusters;
        this.numOfRows = other.numOfRows;
        this.numOfCols = other.numOfCols;
        this.data = other.data;
        this.n_iter = other.n_iter;
        this.n_init = other.n_init;
        this.max_iter = other.max_iter;
        this.tol = other.tol;
        this.best_cluster_centers = other.best_cluster_centers;
        this.best_labels = other.best_labels;
        this.best_inertia = other.best_inertia;
        this.n_iter_ = other.n_iter_;
    }
}
