package speakerrecognition.data;

import speakerrecognition.mfcc.MFCCProcessor;

public class MFCCWrapper {
    public static int mfcc_num = 13;
    public static double power_spectrum_floor = 0.0001;
    public static double pre_emph = 0.95;
    private static int melfilter_bands = 40;
    private int frame_len;
    private int frame_shift;
    private int fft_size;// = 256;
    private double[] window = null;
    private double[][] M = null;
    private double[][] melfb_coeffs = null;
    private double[][] mfcc_coeffs = null;
    private int[] samples = null;
    private int fs;
    private double[][] D1 = null;

    public MFCCWrapper(MFCCWrapper other) {
        this.frame_len = other.frame_len;
        this.frame_shift = other.frame_shift;
        this.fft_size = other.fft_size;
        this.window = other.window;
        this.M = other.M;
        this.melfb_coeffs = other.melfb_coeffs;
        this.mfcc_coeffs = other.mfcc_coeffs;
        this.samples = other.samples;
        this.fs = other.fs;
        this.D1 = other.D1;
    }

    public MFCCWrapper(int[] x, int y) {
        this.fs = y;
        this.samples = x;
        this.frame_len = 256;
        this.fft_size = this.frame_len;
        this.frame_shift = setFrameShift(fs);
        window = MFCCProcessor.hamming(frame_len);

        this.melfb_coeffs = MFCCProcessor.melfb(melfilter_bands, fft_size, fs);

        this.D1 = MFCCProcessor.dctmatrix(melfilter_bands);

        if (this.melfb_coeffs == null) System.out.println("Cannot initialize melfilter bank");
    }

    private int setFrameShift(int sample_rate) {
        return (int) (0.0125 * (double) (sample_rate));
    }

    public int getFrame_len() {
        return frame_len;
    }


    public int getFrame_shift() {
        return frame_shift;
    }

    public double[] getWindow() {
        return window;
    }

    public double[][] getMelfb_coeffs() {
        return melfb_coeffs;
    }

    public double[][] getMfcc_coeffs() {
        return mfcc_coeffs;
    }

    public void setMfcc_coeffs(double[][] mfcc_coeffs) {
        this.mfcc_coeffs = mfcc_coeffs;
    }

    public int[] getSamples() {
        return samples;
    }

    public double[][] getD1() {
        return D1;
    }

    public void setMfcc_coeffs(int index, double[] coeffs) {
        this.mfcc_coeffs[index] = coeffs;
    }

}