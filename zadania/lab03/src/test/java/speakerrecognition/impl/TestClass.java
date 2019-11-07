package speakerrecognition.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import file.FileReaderFactory;
import file.data.WavFile;
import file.impl.WavFileReader;
import org.junit.Test;

import speaker.data.SpeakerModel;
import speakerrecognition.data.GMMWrapper;
import speakerrecognition.data.MFCCWrapper;
import speakerrecognition.gmm.GMMProcessor;
import speakerrecognition.interfaces.ISpeakerRecognition;
import speakerrecognition.mfcc.MFCCProcessor;
import utils.MyException;


public class TestClass {
	
	private ISpeakerRecognition ISpeakerRecognition = new SpeakerRecognitionImpl();
	
	@Test
	public void testCase() throws IOException, MyException {
		
		//given

		WavFileReader reader = (WavFileReader) FileReaderFactory.getFileReader(WavFileReader.class);
		WavFile wavFile = reader.open(Paths.get("src/test/resources/training/speaker1_2.WAV"));
		int[] x = wavFile.getSamples();
		int fs = wavFile.getFs();
		MFCCWrapper mfcc = new MFCCWrapper(x, fs);
		double[][] speaker_mfcc = MFCCProcessor.extract_MFCC(mfcc).getMfcc_coeffs();
		GMMWrapper gmm = new GMMWrapper(speaker_mfcc, 32);
		gmm = GMMProcessor.fitGMM(gmm);

		SpeakerModel speakerModel1 = new SpeakerModel(gmm.getMeans(), gmm.getCovars(), gmm.getWeights(), "speaker1model");

		WavFile wavFile2 = reader.open(Paths.get("src/test/resources/training/speaker2_2.WAV"));
		int[] x2 = wavFile2.getSamples();
		int fs2 = wavFile2.getFs();
		MFCCWrapper mfcc2 = new MFCCWrapper(x2, fs2);
		double[][] speaker_mfcc2 = MFCCProcessor.extract_MFCC(mfcc2).getMfcc_coeffs();
		GMMWrapper gmm2 = new GMMWrapper(speaker_mfcc2, 32);
		gmm2 = GMMProcessor.fitGMM(gmm2);
		SpeakerModel speakerModel2 = new SpeakerModel(gmm2.getMeans(), gmm2.getCovars(), gmm2.getWeights(), "speaker2model");

		List<SpeakerModel> speakerModels = Arrays.asList(speakerModel1, speakerModel2);

		//when
		
		System.out.println(ISpeakerRecognition.recognize(speakerModels, "src/test/resources/test/speaker1_1.WAV"));
		System.out.println(ISpeakerRecognition.recognize(speakerModels, "src/test/resources/test/speaker2_1.WAV"));
		
		
		ISpeakerRecognition.printLogProbsForRecognition(speakerModels, "src/test/resources/test/speaker1_1.WAV");
		ISpeakerRecognition.printLogProbsForRecognition(speakerModels, "src/test/resources/test/speaker2_1.WAV");
		
		//then

	}
	
}
