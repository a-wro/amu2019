package speakerrecognition.impl;

import speakerrecognition.data.KMeansWrapper;
import speakerrecognition.interfaces.IKMeansProcessingService;
import speakerrecognition.kmeans.KMeansProcessor;

public class KMeansProcessingServiceImpl implements IKMeansProcessingService {
    @Override
    public KMeansWrapper fit(KMeansWrapper oldWrapper) {
        return KMeansProcessor.fit(oldWrapper);
    }
}
