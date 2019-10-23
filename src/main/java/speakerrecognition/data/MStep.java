package speakerrecognition.data;

public class MStep {
    private double[] weights;
    private double[][] means;
    private double[][] covars;

    public double[] getWeights() {
        return weights;
    }

    public double[][] getMeans() {
        return means;
    }

    public double[][] getCovars() {
        return covars;
    }

    public MStep(double[] weights, double[][] means, double[][] covars) {
        this.weights = weights;
        this.means = means;
        this.covars = covars;
    }
}
