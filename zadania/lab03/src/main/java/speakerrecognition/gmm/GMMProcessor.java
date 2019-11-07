package speakerrecognition.gmm;

import matrixes.Matrixes;
import speakerrecognition.data.GMMWrapper;
import speakerrecognition.data.KMeansWrapper;
import speakerrecognition.data.MStep;
import speakerrecognition.data.ScoreSamples;
import speakerrecognition.kmeans.KMeansProcessor;
import speakerrecognition.mstep.MStepProcessor;
import statistics.Statistics;
import utils.MyException;

// https://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/distribution/fitting/MultivariateNormalMixtureExpectationMaximization.html
// https://www.ee.washington.edu/techsite/papers/documents/UWEETR-2010-0002.pdf

/**
 * TODO - porobic interfejsy do GMM, KMeans, MFCC itd, te klasy tutaj przeniesc do innych pakietów
 * i niech impementacje korzystają
 * np
 * interfaces
 *   IGMMProcessingService
 * impl
 *   GMMProcessingServiceImpl
 *  gmm
 *    GMMProcessor (aktualna klasa ze statycznymi funkcjami)
 *    ----------------------------------------------------------------------------------------------------------------
 *    mozna zamienic GMMWrapper itd na mniejszy obiekt agregujący potrzebne parametry jako parametr zamiast oldWrapper
 *
 *    dodatkowo: mozna sprobowac zrobic krotsze metody, ogarnac nazwy zmiennych, czesciowo przeniesc do springa
 *
 *
 */

public class GMMProcessor {
	public static GMMWrapper fitGMM(GMMWrapper oldWrapper) throws MyException {
		double change = 0;
		GMMWrapper fitWrapper = new GMMWrapper(oldWrapper);

		try {
			int numOfCols = fitWrapper.getNumOfCols();
			double[][] cv = new double[numOfCols][numOfCols];
			double max_log_prob = Double.NEGATIVE_INFINITY;

			for (int i = 0; i < fitWrapper.getN_init(); i++) {
				KMeansWrapper kMeans = new KMeansWrapper(fitWrapper.getObservations(), fitWrapper.getNumOfComponents());
				kMeans = KMeansProcessor.fit(kMeans);
				fitWrapper.setMeans(kMeans.getBest_cluster_centers());
				fitWrapper.setWeights(
						Matrixes.fillWith(fitWrapper.getWeights(), (double) 1 / fitWrapper.getNumOfComponents()));

				fitWrapper.setCovars(Matrixes.cov(Matrixes.transpose(fitWrapper.getObservations()))); // np.cov(X.T),
																										// gmm.py line
																										// 450
				cv = Matrixes.eye(fitWrapper.getObservations()[0].length, fitWrapper.getMin_covar()); // self.min_covar
																										// *
																										// np.eye(X.shape[1])
				fitWrapper.setCovars(Matrixes.addMatrixes(fitWrapper.getCovars(), cv));
				fitWrapper.setCovars(Matrixes.duplicate(Matrixes.chooseDiagonalValues(fitWrapper.getCovars()),
						fitWrapper.getNumOfComponents()));

				for (int j = 0; j < fitWrapper.getN_init(); j++) {
					fitWrapper.setPrev_log_likelihood(fitWrapper.getCurrent_log_likelihood());
					ScoreSamples scoreSamples = new ScoreSamples(fitWrapper.getObservations(), fitWrapper.getMeans(),
							fitWrapper.getCovars(), fitWrapper.getWeights(), fitWrapper.getNumOfComponents());
					fitWrapper.setLog_likelihoods(scoreSamples.getLogprob());
					fitWrapper.setResponsibilities(scoreSamples.getResponsibilities());
					fitWrapper.setCurrent_log_likelihood(Statistics.getMean(fitWrapper.getLog_likelihoods()));

					if (!Double.isNaN(fitWrapper.getPrev_log_likelihood())) {
						change = Math.abs(fitWrapper.getCurrent_log_likelihood() - fitWrapper.getPrev_log_likelihood());
						if (change < fitWrapper.getTol()) {
							fitWrapper.setConverged(true);
							break;
						}
					}

					/// do m-step - gmm.py line 509
					MStep mstep = MStepProcessor.do_mstep(fitWrapper.getObservations(), fitWrapper.getResponsibilities(),
							fitWrapper.getMin_covar(), fitWrapper.getCovars());
					fitWrapper.setWeights(mstep.getWeights());
					fitWrapper.setMeans(mstep.getMeans());
					fitWrapper.setCovars(mstep.getCovars());

				}

				if (fitWrapper.getCurrent_log_likelihood() > max_log_prob) {
					max_log_prob = fitWrapper.getCurrent_log_likelihood();
					fitWrapper.setBest_means(fitWrapper.getBestMeans());
					fitWrapper.setBest_covars(fitWrapper.getCovars());
					fitWrapper.setBest_weights(fitWrapper.getBest_weights());
				}
			}

			if (Double.isInfinite(max_log_prob))
				System.out
						.println("EM algorithm was never able to compute a valid likelihood given initial parameters");
		} catch (Exception myEx) {
			// TODO log something that went wrong
			myEx.printStackTrace();
			MyException e = new MyException(myEx);
			throw e;
		}

		return fitWrapper;
	}
}
