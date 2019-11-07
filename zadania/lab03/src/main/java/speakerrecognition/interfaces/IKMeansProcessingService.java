package speakerrecognition.interfaces;

import speakerrecognition.data.KMeansWrapper;

public interface IKMeansProcessingService {
    KMeansWrapper fit(KMeansWrapper oldWrapper);
}
