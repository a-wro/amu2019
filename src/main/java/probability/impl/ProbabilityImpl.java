package probability.impl;

import probability.interfaces.IProbability;
import speaker.data.SpeakerModel;
import utils.MyException;

public class ProbabilityImpl implements IProbability {
    public double getLogProbabilityOfDataUnderModel(SpeakerModel model, double[][] dataToBeTested) throws MyException {
        return model.getScore(dataToBeTested);
    }
}
