package speakerrecognition.interfaces;

import speakerrecognition.data.MFCCWrapper;

public interface IMFCCProcessingService {
    double[][] melfb(int p, int n, int fs);
    MFCCWrapper extract_MFCC(MFCCWrapper oldWrapper);

    // TODO sprawdzic inne metody;
}
