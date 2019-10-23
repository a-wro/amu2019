package cluster.interfaces;

public interface ICluster {
    double[][] getMeansOfClustersFor2DdataByGMM(double[][] data, int numOfClusters) throws Exception;

    double[][] getMeansOfClustersFor2DdataByKMeans(double[][] data, int numOfClusters);
}
