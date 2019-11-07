package speakerrecognition.mstep;

import matrixes.Matrixes;
import speakerrecognition.data.MStep;
import utils.MathConstants;

public class MStepProcessor {
    public static MStep do_mstep(double[][] data, double[][] responsibilities, double min_covar, double[][] covars) throws Exception {
        try {
            double[] weights = Matrixes.sum(responsibilities, 0);
            double[][] weighted_X_sum = Matrixes.multiplyByMatrix(Matrixes.transpose(responsibilities), data);
            double[] inverse_weights = Matrixes.invertElements(Matrixes.addValue(weights, 10 * MathConstants.EPS));
            weights = Matrixes.addValue(Matrixes.multiplyByValue(weights, 1.0 / (Matrixes.sum(weights) + 10 * MathConstants.EPS)), MathConstants.EPS);
            double [][] means = Matrixes.multiplyByValue(weighted_X_sum, inverse_weights);
            covar_mstep_diag(means, data, responsibilities, weighted_X_sum, inverse_weights, min_covar);
            return new MStep(weights, means, covars);
        } catch (Exception myEx) {
            myEx.printStackTrace();
            throw myEx;
        }
    }

    public static double[][] covar_mstep_diag(double[][] means, double[][] X, double[][] responsibilities, double[][] weighted_X_sum, double[] norm, double min_covar) {
        double[][] temp = null;
        try {
            double[][] avg_X2 = Matrixes.multiplyByValue(Matrixes.multiplyByMatrix(Matrixes.transpose(responsibilities), Matrixes.multiplyMatrixesElByEl(X, X)), norm);
            double[][] avg_means2 = Matrixes.power(means, 2);
            double[][] avg_X_means = Matrixes.multiplyByValue(Matrixes.multiplyMatrixesElByEl(means, weighted_X_sum), norm);
            temp = Matrixes.addValue(Matrixes.addMatrixes(Matrixes.substractMatrixes(avg_X2, Matrixes.multiplyByValue(avg_X_means, 2)), avg_means2), min_covar);
        } catch (Exception myEx) {
            System.out.println("An exception encourred: " + myEx.getMessage());
            myEx.printStackTrace();
            System.exit(1);
        }
        return temp;
    }
}
