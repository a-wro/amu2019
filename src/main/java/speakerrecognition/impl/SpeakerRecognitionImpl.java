package speakerrecognition.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import file.data.WavFile;
import file.impl.WavFileReader;
import speaker.data.SpeakerModel;
import speakerrecognition.data.MFCCWrapper;
import speakerrecognition.interfaces.ISpeakerRecognition;
import utils.MyException;

public class SpeakerRecognitionImpl implements ISpeakerRecognition {

	public double[][] computeMFCC(int[] soundSamples, int fs) {
		MFCCWrapper mfcc = new MFCCWrapper(soundSamples, fs);
		double[][] speaker_mfcc = MFCCProcessingService.extract_MFCC(mfcc).getMfcc_coeffs();
		return speaker_mfcc;
	}

	public String recognize(List<SpeakerModel> speakerModels, String resourceSoundSpeechFilePath) throws IOException, MyException {
		WavFileReader wavFileReader = new WavFileReader();
		double finalScore = Long.MIN_VALUE;
		String bestFittingModelName = "";
		for(SpeakerModel model : speakerModels){
			WavFile wavFile1 = wavFileReader.open(Path.of(resourceSoundSpeechFilePath));
			int[] x3 = wavFile1.getSamples();
			int fs3 = wavFile1.getFs();
			MFCCWrapper mfcc3 = new MFCCWrapper(x3, fs3);
			double[][] speaker_mfcc3 =  MFCCProcessingService.extract_MFCC(mfcc3).getMfcc_coeffs();
			double scoreForTest1 = model.getScore(speaker_mfcc3);
			if(scoreForTest1 > finalScore){
				finalScore = scoreForTest1;
				bestFittingModelName = model.getName();
			}
			
		}
			String recogResult = "Test speech from file "+resourceSoundSpeechFilePath + " is most similar to model "+ bestFittingModelName;
		return recogResult;
	}

	public void printLogProbsForRecognition(List<SpeakerModel> speakerModels, String resourceSoundSpeechFilePath)
			throws IOException, MyException {
		WavFileReader wavFileReader = new WavFileReader();
		for(SpeakerModel model : speakerModels){
			WavFile wavFile1 = wavFileReader.open(Path.of(resourceSoundSpeechFilePath));
			int[] x3 = wavFile1.getSamples();
			int fs3 = wavFile1.getFs();
			MFCCWrapper mfcc3 = new MFCCWrapper(x3, fs3);
			double[][] speaker_mfcc3 =  MFCCProcessingService.extract_MFCC(mfcc3).getMfcc_coeffs();
			double scoreForTest1 = model.getScore(speaker_mfcc3);
			System.out.println("Test speech from file "+resourceSoundSpeechFilePath + " is similar to model "+ model.getName()+" with log probability "+scoreForTest1);
			
		}
		
	}

}
