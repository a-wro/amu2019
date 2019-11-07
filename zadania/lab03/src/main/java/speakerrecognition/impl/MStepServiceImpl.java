package speakerrecognition.impl;

import speakerrecognition.data.MStep;
import speakerrecognition.interfaces.IMStepService;

public class MStepServiceImpl implements IMStepService {
    @Override
    public MStep do_mstep(double[][] data, double[][] responsibilities, double min_covar, double[][] covars) throws Exception {
        return null;
    }

    @Override
    public double[][] covar_mstep_diag(double[][] means, double[][] X, double[][] responsibilities, double[][] weighted_X_sum, double[] norm, double min_covar) {
        return new double[0][];
    }
}
