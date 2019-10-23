package speakerrecognition.interfaces;

import java.io.IOException;
import java.util.List;

import utils.MyException;
import speaker.data.SpeakerModel;

public interface ISpeakerRecognition {

	String recognize(List<SpeakerModel> speakerModels, String resourceSoundSpeechFilePath) throws IOException, MyException;
	
	void printLogProbsForRecognition(List<SpeakerModel> speakerModels, String resourceSoundSpeechFilePath) throws IOException, MyException;
}
