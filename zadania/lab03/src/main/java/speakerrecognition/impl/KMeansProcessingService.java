package speakerrecognition.impl;


import matrixes.Matrixes;
import speakerrecognition.data.KMeansWrapper;
import statistics.impl.StatisticsImpl;

import java.util.Arrays;

public class KMeansProcessingService {

    public static KMeansWrapper fit(KMeansWrapper oldWrapper) {
        KMeansWrapper fitWrapper = new KMeansWrapper(oldWrapper);
        double[][] cluster_centers = null;
        int[] labels = null;
        double inertia = 0;
        double[][] data = fitWrapper.getData();
        //int n_iter = 0;
        //double[] result = null;
        //double[][] centers = new double[this.numOfClusters][this.numOfCols];

        try {

            ////////// substracting mean //////////////
            double[] X_mean = StatisticsImpl.getMean(data);
            for (int i = 0; i < fitWrapper.getNumOfRows(); i++) {
                for (int j = 0; j < fitWrapper.getNumOfCols(); j++) {
                    data[i][j] -= X_mean[j];
                }
            }


            ////////// numpy einsum //////////////
            double[] x_squared_norms = Matrixes.einsum(data);

            for (int i = 0; i < fitWrapper.getN_init(); i++) {
                Kmeans_single kmeans_single = new Kmeans_single(fitWrapper.getData(), fitWrapper.getNumOfRows(),
                        fitWrapper.getNumOfCols(), fitWrapper.getNumOfClusters(), x_squared_norms,
                        fitWrapper.getMax_iter(), fitWrapper.getTol());

                cluster_centers = kmeans_single.get_best_centers().clone();
                inertia = kmeans_single.get_best_inertia();
                labels = kmeans_single.get_best_labels().clone();

                if (inertia < fitWrapper.getBest_inertia()) {
                    fitWrapper.setBest_labels(labels);
                    fitWrapper.setBest_inertia(inertia);
                    fitWrapper.setBest_cluster_centers(cluster_centers.clone());

                }

            }

            fitWrapper.setBest_cluster_centers(Matrixes.addValue(cluster_centers, X_mean));
        } catch (Exception myEx) {
            //System.out.println("An exception encourred: " + myEx.getMessage());
            myEx.printStackTrace();
            System.exit(1);
        }



        return fitWrapper;
    }


    private static double[][] centers_dense(double[][] X, int[] labels, int n_clusters, double[] distances) {
        double[][] result = new double[n_clusters][X[0].length];
        for (int j = 0; j < X[0].length; j++) {
            double[] sum = new double[n_clusters];
            for (int k = 0; k < n_clusters; k++) {
                int samples_num = 0;
                for (int z = 0; z < labels.length; z++) {
                    if (labels[z] == k) {
                        sum[k] += X[z][j];
                        samples_num += 1;
                    }
                }
                sum[k] /= samples_num;

            }
            for (int i = 0; i < n_clusters; i++)
                result[i][j] = sum[i];
        }
        return result;

    }

    private static double[][] init_centroids(double[][] data, int numOfRows, int numOfCols, int n_clusters, double[] x_sq_norms) {
        double[][] centers = new double[n_clusters][numOfCols];

        try {
            int n_local_trials = 2 + (int) (Math.log(n_clusters));
            /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
            int center_id = (int) Math.floor(Math.random() * numOfRows);
            for (int i = 0; i < numOfCols; i++) {
                centers[0][i] = data[center_id][i];
            }
            double[] closest_dist_sq = Matrixes.euclidean_distances(centers[0], data, x_sq_norms);
            double current_pot = Matrixes.sum(closest_dist_sq);

            for (int c = 1; c < n_clusters; c++) {
                /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
                double[] rand_vals = Matrixes.genRandMatrix(current_pot, n_local_trials);
                double[] closest_dist_sq_cumsum = Matrixes.cumsum(closest_dist_sq);
                int[] candidate_ids = Matrixes.searchsorted(closest_dist_sq_cumsum, rand_vals);
                double[][] data_candidates = new double[n_local_trials][numOfCols];

                for (int z = 0; z < n_local_trials; z++) {
                    for (int j = 0; j < numOfCols; j++) {
                        data_candidates[z][j] = data[candidate_ids[z]][j];
                    }
                }

                int best_candidate = -1;
                double best_pot = 99999999;
                double[] best_dist_sq = null;

                double[][] distance_to_candidates = Matrixes.euclidean_distances(data_candidates, data, x_sq_norms);

                for (int trial = 0; trial < n_local_trials; trial++) {
                    double[] new_dist_sq = Matrixes.minimum(closest_dist_sq, Matrixes.select_row(distance_to_candidates, trial));
                    double new_pot = Matrixes.sum(new_dist_sq);

                    if (best_candidate == -1 | new_pot < best_pot) {
                        best_candidate = candidate_ids[trial];
                        best_pot = new_pot;
                        best_dist_sq = Arrays.copyOf(new_dist_sq, new_dist_sq.length);
                    }
                }
                double[] center_temp = Arrays.copyOf(data[best_candidate], data[best_candidate].length);
                for (int ii = 0; ii < centers[0].length; ii++) {
                    centers[c][ii] = center_temp[ii];
                }
                current_pot = best_pot;
                closest_dist_sq = Arrays.copyOf(best_dist_sq, best_dist_sq.length);
                //System.out.println("temp");

            }
        } catch (Exception myEx) {
            //System.out.println("An exception encourred: " + myEx.getMessage());
            myEx.printStackTrace();
            System.exit(1);
        }

        return centers;

    }

    private static class Kmeans_single {
        private int[] best_labels = null;
        private double[][] best_centers = null;
        private double best_inertia = Double.MAX_VALUE;
        private double[] distances = null;
        private int numOfRows;
        private int numOfCols;

        Kmeans_single(double[][] data, int numOfRows, int numOfCols, int n_clusters, double[] x_sq_norms, int max_iter, double tol) {

            try {

                double[][] centers = init_centroids(data, numOfRows, numOfCols, n_clusters, x_sq_norms);
                this.distances = new double[data.length];

                for (int i = 0; i < max_iter; i++) {
                    double[][] centers_old = centers.clone();
                    LabelsInertia labelsInertia = new LabelsInertia(data, x_sq_norms, centers, this.distances);
                    int[] labels = labelsInertia.getLabels().clone();
                    double inertia = labelsInertia.getInertia();
                    this.distances = labelsInertia.getDistances().clone();

                    centers = centers_dense(data, labels, n_clusters, distances);

                    if (inertia < best_inertia) {
                        this.best_labels = labels.clone();
                        this.best_centers = centers.clone();
                        this.best_inertia = inertia;
                    }

                    if (Matrixes.squared_norm(Matrixes.substractMatrixes(centers_old, centers)) <= tol)
                        break;

                }
            } catch (Exception myEx) {
                //System.out.println("An exception encourred: " + myEx.getMessage());
                myEx.printStackTrace();
                System.exit(1);
            }
        }

        public int[] get_best_labels() {
            return this.best_labels;
        }

        public double[][] get_best_centers() {
            return this.best_centers;
        }

        public double get_best_inertia() {
            return this.best_inertia;
        }
    }

    private static class LabelsInertia {
        private double[][] data = null;
        private int[] labels = null;
        private double[][] centers = null;
        private double[] distances = null;
        private double[] x_squared_norms = null;
        private double inertia = 0;

        LabelsInertia(double[][] X, double[] x_squared_norms, double[][] centers, double[] distances) {
            this.centers = centers;
            this.x_squared_norms = x_squared_norms;
            this.distances = distances;
            this.data = X;

            int n_samples = data.length;
            int[] labels = new int[n_samples];
            labels = Matrixes.addValue(labels, -1);

            LabelsPrecomputeDence result = new LabelsPrecomputeDence(this.data, this.x_squared_norms, this.centers, this.distances);
            this.labels = result.labels.clone();
            this.inertia = result.inertia;
            this.distances = result.distances.clone();

        }

        private int[] getLabels() {
            return this.labels.clone();
        }

        private double getInertia() {
            return this.inertia;
        }

        private double[] getDistances() {
            return this.distances.clone();
        }

        private class LabelsPrecomputeDence {
            private double[][] data = null;
            private int[] labels = null;
            private double[][] centers = null;
            private double[] distances = null;
            private double[] x_squared_norms = null;
            private double inertia = 0;

            LabelsPrecomputeDence(double[][] X, double[] x_squared_norms, double[][] centers, double[] distances) {
                this.centers = centers;
                this.x_squared_norms = x_squared_norms;
                this.distances = distances; ////////// huston, problem - k_means.py line 490, niejawne zwracanie
                this.data = X;

                int n_samples = data.length;
                int k = centers.length;
                double[][] all_distances = Matrixes.euclidean_distances(centers, X, x_squared_norms);
                this.labels = new int[n_samples];
                this.labels = Matrixes.addValue(this.labels, -1);
                double[] mindist = new double[n_samples];
                mindist = Matrixes.addValue(mindist, Double.POSITIVE_INFINITY);

                for (int center_id = 0; center_id < k; center_id++) {
                    double[] dist = all_distances[center_id];
                    for (int i = 0; i < labels.length; i++) {
                        if (dist[i] < mindist[i]) {
                            this.labels[i] = center_id;
                        }
                        mindist[i] = Math.min(dist[i], mindist[i]);
                    }
                }
                if (n_samples == this.distances.length)
                    this.distances = mindist;
                this.inertia = Matrixes.sum(mindist);
            }
        }
    }


}
