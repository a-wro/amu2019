package speakerrecognition.impl;


import matrixes.Matrixes;
import org.jtransforms.fft.DoubleFFT_1D;
import speakerrecognition.data.MFCCWrapper;


public class MFCCProcessingService {
///////////////// computation of mel filterbank ////////////////

	public static double[][] melfb(int p, int n, int fs){
		// p - number of filterbanks
		// n - length of fft
		// fs - sample rate 
		
		double f0 = 700/(double)fs;
		int fn2 = (int)Math.floor((double)n/2);
		double lr = Math.log((double)1+0.5/f0)/(p+1);
		double[] CF = arange(1,p+1);
		
		for(int i=0;i<CF.length;i++){
			CF[i] = fs*f0*(Math.exp(CF[i]*lr)-1);
			//CF[i] = (Math.exp(CF[i]*lr));
		}
		
		double[] bl = {0, 1, p, p+1};
		
		for(int i=0;i<bl.length;i++){
			bl[i] = n*f0*(Math.exp(bl[i]*lr)-1);
		}
		
		int b1 = (int)Math.floor(bl[0])+1;
		int b2 = (int)Math.ceil(bl[1]);
		int b3 = (int)Math.floor(bl[2]);
		int b4 = Math.min(fn2, (int)Math.ceil(bl[3]))-1;
		double[] pf = arange(b1, b4+1);
		
		for(int i=0;i<pf.length;i++){
			pf[i] = Math.log(1+pf[i]/f0/(double)n)/lr;
		}
		
		double[] fp = new double[pf.length];
		double[] pm = new double[pf.length];
		
		for(int i=0;i<fp.length;i++){
			fp[i] = Math.floor(pf[i]);
			pm[i] = pf[i] - fp[i];
		}
		
		double[][] M = new double[p][1+fn2];
		int r=0;
		
		for(int i=b2-1;i<b4;i++){
			r = (int)fp[i]-1;
			M[r][i+1] += 2* (1-pm[i]);
		}
		
		for(int i=0;i<b3; i++){
			r = (int)fp[i];
			M[r][i+1] += 2* pm[i];
		}
		
		/////////// normalization part //////////
		
		//int xx = M.length;
		double[] temp_row = null;
		double row_energy = 0;
		//System.out.println(Integer.toString(M.length));
		for (int i=0;i<M.length;i++){
			temp_row = M[i];
			row_energy = energy(temp_row);
			if(row_energy < 0.0001)
				temp_row[i] = i;
			else{
				while(row_energy>1.01){
					temp_row = Matrixes.row_mul(temp_row, 0.99);
					row_energy = energy(temp_row);
				}
				while(row_energy<0.99){
					temp_row = Matrixes.row_mul(temp_row, 1.01);
					row_energy = energy(temp_row);
				}
			}
			M[i] = temp_row;
			
		}
		return M;
	}

//////////////////////////////////////////////////////////////////////////////////////////

	public static MFCCWrapper extract_MFCC(MFCCWrapper oldWrapper){
		MFCCWrapper extractedWrapper = new MFCCWrapper(oldWrapper);
		// https://gist.github.com/jongukim/4037243
		//http://dp.nonoo.hu/projects/ham-dsp-tutorial/05-sine-fft/
		
		if(extractedWrapper.getSamples() != null){
			int frameLen = extractedWrapper.getFrame_len();
			int[] samples = extractedWrapper.getSamples();
			int frameShift = extractedWrapper.getFrame_shift();

			DoubleFFT_1D fftDo = new DoubleFFT_1D(frameLen);
			double[] fft1 = new double[frameLen * 2];
			double[] fft_final = new double[frameLen/2+1];
			int frames_num = (int)((double)(samples.length - frameLen)/(double)(frameShift))+1;
			extractedWrapper.setMfcc_coeffs(new double[frames_num][MFCCWrapper.mfcc_num]);
			double[] frame = new double[frameLen];
							
			for(int i=0;i<frames_num;i++){
				
				for(int j=0;j<frameLen;j++){
					frame[j] = (double)samples[i*frameShift+j];
				}
				
				try{
					frame = Matrixes.row_mul(frame, extractedWrapper.getWindow());
					frame = preemphasis(frame);
					System.arraycopy(frame, 0, fft1, 0, frameLen);
					fftDo.realForwardFull(fft1);

					for(int k=0;k<(frameLen/2+1);k++){
						fft_final[k] = Math.pow(Math.sqrt(Math.pow(fft1[k*2],2)+Math.pow(fft1[k*2+1],2)), 2);
						
						if(fft_final[k]<MFCCWrapper.power_spectrum_floor) fft_final[k]=MFCCWrapper.power_spectrum_floor;
					}
					
					double[] dot_prod = Matrixes.multiplyByMatrix(extractedWrapper.getMelfb_coeffs(), fft_final);
					for(int j=0;j<dot_prod.length;j++){
						dot_prod[j] = Math.log(dot_prod[j]);
					}
					dot_prod = Matrixes.multiplyByMatrix(extractedWrapper.getD1(), dot_prod);
					extractedWrapper.setMfcc_coeffs(i, dot_prod);
				}
				catch(Exception myEx)
		        {
					System.out.println("An exception encourred: " + myEx.getMessage());
		            myEx.printStackTrace();
		            System.exit(1);		            
		        }
				
			}
		}
		else{
			System.out.println("Vector of input samples is null");
		}
		return extractedWrapper;
	}
	
	///////////// math functions ///////////////////////////////////////////////////////////////
		
	public static double[] arange(int x1, int x2){
		double[] temp = null;
		try{
		temp = new double[x2-x1];
			for(int i=0;i<temp.length;i++){
				temp[i] = x1+i;
			}
		
		}
		catch(IndexOutOfBoundsException e){
			System.err.println("IndexOutOfBoundsException: " + e.getMessage());
		}
		return temp;
	}
	
	public static double energy(double[] x){
		double en = 0;
		for(int i=0; i<x.length;i++)
			en = en + Math.pow(x[i], 2);
		return en;
		}
		
	public static double[] preemphasis(double[] x){
		double[] y = new double[x.length];
		y[0] = x[0];
		for(int i=1;i<x.length;i++){
			y[i] = x[i]-MFCCWrapper.pre_emph*x[i-1];
		}
		return y;
	}

	public static double[][] dctmatrix(int n){
		double[][] d1 = new double[n][n];
		double[][] x = Matrixes.meshgrid_ox(n);
		double[][] y = Matrixes.meshgrid_oy(n);
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				x[i][j] = (x[i][j]*2+1)*Math.PI/(2*n);
			}
		}
		
		try{
			d1 = Matrixes.multiplyMatrixesElByEl(x, y);
		}
		catch(Exception myEx)
        {
            //System.out.println("An exception encourred: " + myEx.getMessage());
            myEx.printStackTrace();
            System.exit(1);
        }
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				d1[i][j] = Math.sqrt(2/(double)n)*Math.cos(d1[i][j]);
			}
		}
		for(int i=0;i<n;i++){
			d1[0][i] /= Math.sqrt(2);
		}
		
		double[][] d = new double[MFCCWrapper.mfcc_num][n];
		for(int i=1;i<MFCCWrapper.mfcc_num+1;i++){
			for(int j=0;j<n;j++){
				d[i-1][j] = d1[i][j];
			}
			
		}
		
		return d;
	}

	public static double[] hamming ( int frame_len){
		double[] window_temp = new double[frame_len];
		for (int i = 0; i < window_temp.length; i++) {
			window_temp[i] = 0.54 - 0.46 * Math.cos(2 * Math.PI / (double) frame_len * ((double) i + 0.5));
		}
		return window_temp;
	}
}
