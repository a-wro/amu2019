package file.data;


import utils.MyException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;


public class WavFile extends AbstractFile {
    private byte[] byte_samples;
    private int[] samples;
    private int fs;
    private Path file_path;
    private int samples_num;
    private int channels_num;

    public WavFile() {
    }

    ///////////////// constructor /////////////////////////////////////////////
    public WavFile(String x) {
        this.file_path = Paths.get(x);
    }

    public byte[] getByte_samples() {
        return byte_samples;
    }

    public void setByte_samples(byte[] byte_samples) {
        this.byte_samples = byte_samples;
    }

    public int[] getSamples() {
        return samples;
    }

    public void setSamples(int[] samples) {
        this.samples = samples;
    }

    public int getFs() {
        return fs;
    }

    public void setFs(int fs) {
        this.fs = fs;
    }

    public Path getFile_path() {
        return file_path;
    }

    public void setFile_path(Path file_path) {
        this.file_path = file_path;
    }

    public int getSamples_num() {
        return samples_num;
    }

    public void setSamples_num(int samples_num) {
        this.samples_num = samples_num;
    }

    public int getChannels_num() {
        return channels_num;
    }

    public void setChannels_num(int channels_num) {
        this.channels_num = channels_num;
    }
}


