package probability.interfaces;

import speaker.data.SpeakerModel;
import utils.MyException;

public interface IProbability {
    double getLogProbabilityOfDataUnderModel(SpeakerModel model, double[][] dataToBeTested) throws MyException;
}
