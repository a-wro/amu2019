package speakerrecognition.interfaces;

import speakerrecognition.data.MStep;

public interface IMStepService {
    MStep do_mstep(double[][] data, double[][] responsibilities, double min_covar, double[][] covars) throws Exception;
    double[][] covar_mstep_diag(double[][] means, double[][] X, double[][] responsibilities,
                                double[][] weighted_X_sum, double[] norm, double min_covar);

}
