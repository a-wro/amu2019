package cluster.impl;

import cluster.interfaces.ICluster;
import speakerrecognition.data.GMMWrapper;
import speakerrecognition.data.KMeansWrapper;
import speakerrecognition.impl.GMMProcessingService;
import speakerrecognition.impl.KMeansProcessingService;

public class ClusterImpl implements ICluster {
    public double[][] getMeansOfClustersFor2DdataByGMM(double[][] data, int numOfClusters) throws Exception {
        GMMWrapper gmmWrapper = new GMMWrapper(data, numOfClusters);
        try {
            gmmWrapper = GMMProcessingService.fitGMM(gmmWrapper);
            return gmmWrapper.getBestMeans();
        } catch (Exception e) {
            // TODO specify exception, log something that went wrong
            throw e;
        }
    }

    public double[][] getMeansOfClustersFor2DdataByKMeans(double[][] data, int numOfClusters) {
        KMeansWrapper kMeans = new KMeansWrapper(data, numOfClusters);
        kMeans = KMeansProcessingService.fit(kMeans);
        return kMeans.getBest_cluster_centers();
    }
}
