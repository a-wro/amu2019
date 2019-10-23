package file.impl;

import file.FileReaderFactory;
import file.data.WavFile;
import file.interfaces.IFileReader;
import utils.MyException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class WavFileReader implements IFileReader {
    public WavFile open(Path path) throws IOException {
        WavFile wavFile = new WavFile();

        RandomAccessFile in = null;
        try{
            in = new RandomAccessFile(path.toString(), "r");
        }
        catch(Exception myEx)
        {
            myEx.printStackTrace();
            System.exit(1);
        }

        byte[] byte_samples = new byte[(int) in.length()];
        wavFile.setByte_samples(byte_samples);

        in.read(byte_samples, 0, (int) (in.length()));

        wavFile.setSamples_num(getSamplesNum(byte_samples[40], byte_samples[41], byte_samples[42], byte_samples[43]));
        wavFile.setChannels_num(getChannelsNum(byte_samples[22], byte_samples[23]));

        int[] samples = new int[wavFile.getSamples_num()/2/wavFile.getChannels_num()];

        wavFile.setFs(getFs(byte_samples[24], byte_samples[25], byte_samples[26], byte_samples[27]));

        int channels_num = wavFile.getChannels_num();
        int samples_num = wavFile.getSamples_num();
        if(channels_num==1){
            for (int i=44;i<(samples_num+44)/2; i++){
                samples[i-44] = toInt(byte_samples[(i-44)*2+45], byte_samples[(i-44)*2+44]);
            }
        }
        else if(channels_num==2){
            int j=44;
            for (int i=44;i<(samples_num+44)/2; i+=2){
                samples[j-44] = (toInt(byte_samples[(i-44)*2+45], byte_samples[(i-44)*2+44])+toInt(byte_samples[(i-44)*2+47], byte_samples[(i-44)*2+46]))/2;
                j++;
            }

        }
        else{
            System.out.println("Too much channels, only 1 or 2 are supported");
            return null;
        }
        wavFile.setSamples(samples);

        in.close();
        return wavFile;

    }

    public static int[] getSamples(String resourcePath) throws IOException {
        try {
            WavFileReader reader = (WavFileReader) FileReaderFactory.getFileReader(WavFileReader.class);
            WavFile wavFile = reader.open(Path.of(resourcePath));
            return wavFile.getSamples();
        } catch (IOException e) {
            // TODO log something that went wrong
            throw e;
        }
    }

    public static int getSamplingFrequency(String resourcePath) throws IOException, MyException {
        try {
            WavFileReader reader = (WavFileReader) FileReaderFactory.getFileReader(WavFileReader.class);
            WavFile wavFile = reader.open(Path.of(resourcePath));
            return wavFile.getFs();
        } catch (IOException e) {
            // TODO log something that went wrong
            throw e;
        }
    }

    //////////// WAV parameters from header ////////////////////////

    private int toInt(byte hb, byte lb){
        return ((int)hb << 8) | ((int)lb & 0xFF);
    }

    private int getFs(byte x1, byte x2, byte x3, byte x4){
        return ((int)x1 & 0xFF | (int)x2 << 8 | (int)x3 << 16 | (int)x4 << 24 );
    }

    private int getSamplesNum(byte x1, byte x2, byte x3, byte x4){
        return ((int)x1 & 0xFF | ((int)x2 << 8) & 0xFF00 | ((int)x3 << 16) & 0xFF0000 | ((int)x4 << 24) & 0xFF000000);
    }

    private int getChannelsNum(byte x1, byte x2){
        return ((int)x2 << 8) | ((int)x1 & 0xFF);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
}
