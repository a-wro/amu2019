package speakerrecognition.impl;

import speakerrecognition.data.GMMWrapper;
import speakerrecognition.gmm.GMMProcessor;
import speakerrecognition.interfaces.IGMMProcessingService;
import utils.MyException;

public class GMMProcessingServiceImpl implements IGMMProcessingService {
    @Override
    public GMMWrapper fitGMM(GMMWrapper oldWrapper) throws MyException {
        try {
            return GMMProcessor.fitGMM(oldWrapper);
        } catch (MyException e) {
            // TODO log something that went wrong
            throw e;
        }
    }
}
