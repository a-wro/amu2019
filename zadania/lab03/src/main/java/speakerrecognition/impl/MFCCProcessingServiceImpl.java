package speakerrecognition.impl;

import speakerrecognition.data.MFCCWrapper;
import speakerrecognition.interfaces.IMFCCProcessingService;
import speakerrecognition.mfcc.MFCCProcessor;

public class MFCCProcessingServiceImpl implements IMFCCProcessingService {
    @Override
    public double[][] melfb(int p, int n, int fs) {
        return MFCCProcessor.melfb(p, n, fs);
    }

    @Override
    public MFCCWrapper extract_MFCC(MFCCWrapper oldWrapper) {
        return MFCCProcessor.extract_MFCC(oldWrapper);
    }
}
