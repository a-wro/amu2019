package speakerrecognition.interfaces;

import speakerrecognition.data.GMMWrapper;
import utils.MyException;

public interface IGMMProcessingService {
    GMMWrapper fitGMM(GMMWrapper oldWrapper) throws MyException;

}
