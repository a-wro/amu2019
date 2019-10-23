package speakerrecognition.data;

import matrixes.Matrixes;

public class GMMWrapper {
    private int n_init = 10;
    private int n_iter = 10;
    private int numOfRows;
    private int numOfCols;
    private int maxIter;
    private double threshold;
    private int numOfComponents;
    private double[][] observations;
    private double min_covar = 0.001;
    private boolean converged = false;
    private double current_log_likelihood = 0;
    private double prev_log_likelihood = Double.NaN;
    private double tol = 0.001;
    private double[] log_likelihoods = null;
    private double[][] responsibilities = null;
    private double[][] means = null;
    private double[] weights = null;
    private double[][] covars = null;
    private double[][] best_means = null;
    private double[] best_weights = null;
    private double[][] best_covars = null;
    
    public GMMWrapper(GMMWrapper other) {
        this.n_init = other.n_init;
        this.n_iter = other.n_iter;
        this.numOfRows = other.numOfRows;
        this.numOfCols = other.numOfCols;
        this.maxIter = other.maxIter;
        this.threshold = other.threshold;
        this.numOfComponents = other.numOfComponents;
        this.observations = other.observations;
        this.min_covar = other.min_covar;
        this.converged = other.converged;
        this.current_log_likelihood = other.current_log_likelihood;
        this.prev_log_likelihood = other.prev_log_likelihood;
        this.tol = other.tol;
        this.log_likelihoods = other.log_likelihoods;
        this.responsibilities = other.responsibilities;
        this.means = other.means;
        this.weights = other.weights;
        this.covars = other.covars;
        this.best_means = other.best_means;
        this.best_weights = other.best_weights;
        this.best_covars = other.best_covars;
    }

    public GMMWrapper(double[][] data, int compNum) {
        this.observations = data;
        this.numOfRows = data.length;
        this.numOfCols = data[0].length;
        this.numOfComponents = compNum;
        this.means = new double[compNum][data[0].length];
        this.weights = new double[data.length];
        this.covars = new double[compNum][data[0].length];
    }

    public int getNumOfComponents() {
        return numOfComponents;
    }


    public double[][] getObservations() {
        return observations;
    }

    public double getMin_covar() {
        return min_covar;
    }

    public void setConverged(boolean converged) {
        this.converged = converged;
    }

    public double getCurrent_log_likelihood() {
        return current_log_likelihood;
    }

    public void setCurrent_log_likelihood(double current_log_likelihood) {
        this.current_log_likelihood = current_log_likelihood;
    }

    public double getPrev_log_likelihood() {
        return prev_log_likelihood;
    }

    public void setPrev_log_likelihood(double prev_log_likelihood) {
        this.prev_log_likelihood = prev_log_likelihood;
    }

    public double getTol() {
        return tol;
    }

    public double[] getLog_likelihoods() {
        return log_likelihoods;
    }

    public void setLog_likelihoods(double[] log_likelihoods) {
        this.log_likelihoods = log_likelihoods;
    }

    public double[][] getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(double[][] responsibilities) {
        this.responsibilities = responsibilities;
    }

    public double[][] getMeans() {
        return means;
    }

    public void setMeans(double[][] means) {
        this.means = means;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double[][] getCovars() {
        return covars;
    }

    public void setCovars(double[][] covars) {
        this.covars = covars;
    }

    public double[][] getBest_means() {
        return best_means;
    }

    public void setBest_means(double[][] best_means) {
        this.best_means = best_means;
    }

    public double[] getBest_weights() {
        return best_weights;
    }

    public void setBest_weights(double[] best_weights) {
        this.best_weights = best_weights;
    }

    public void setBest_covars(double[][] best_covars) {
        this.best_covars = best_covars;
    }

    public int getN_init() {
        return n_init;
    }

    public double[][] getBestMeans() {
        return this.best_means;
    }

    public int getNumOfCols() {
        return numOfCols;
    }
}
