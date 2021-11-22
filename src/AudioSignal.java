import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/*
Valentin VIAL 2G1 TD3 TP6.
Gather recorded voice in a buffer
*/

public class AudioSignal {

    private static double[] sampleBuffer; // floating point representation of audio samples
    static ByteArrayOutputStream out = new ByteArrayOutputStream();

    double pic_BF;
    int ind_BF;
    double pic_HF;
    int ind_HF;

    public boolean recordFrom(TargetDataLine audioInput, int duration) {
        
        byte[] byteBuffer = new byte[audioInput.getBufferSize()*duration];       
        int nbs_bytes_lu=0;
        
        //Gathering audio record in array.
        nbs_bytes_lu = audioInput.read(byteBuffer,0,byteBuffer.length); 
        out.write(byteBuffer, 0, nbs_bytes_lu);

        //Copying the record in a double array ! -> need conversion (see method toDoubleArray below)
        sampleBuffer = new double[nbs_bytes_lu*2];
        sampleBuffer = toDoubleArray(byteBuffer);
        
        //System.out.println(sampleBuffer[0]+"  "+sampleBuffer[1]+"  "+sampleBuffer[2]);

        return true;
    }

    public static double[] toDoubleArray(byte[] byteArray){
        double[] doubles = new double[byteArray.length];
        for (int i=0; i<(doubles.length/2)-1; i++){
            doubles[i] = ((byteArray[2*i]<<8)+byteArray[2*i+1]) / 32768.0;
        }
        return doubles;
    }


    public boolean playTo(SourceDataLine audioOutput) {
        System.out.println("PLay back");
        audioOutput.write(out.toByteArray(),0,out.size());
        out.reset();
        return true;
    }

    public Complex[] applyFT(){
        double[] signal = sampleBuffer;
        //Creating a complex signal to process FFT
        int n = (int) Math.pow(2 ,(int)(Math.log(signal.length)/Math.log(2)));// Puissance de 2 la plus proche

        Complex[] s= new Complex[n];
        
        for (int i = 0; i < s.length; i++) {
            s[i] = new Complex(signal[i], 0.0);
            //s[i] = new Complex(Math.cos(2*Math.PI*1000*i*1/s.length), 0); //-> Cosinus de test => Pic a 1000Hz 
        }
        //0 padding
        for (int i = s.length; i < n; i++) {
            s[i] =  new Complex(0.0, 0.0);
        }

        //Processing audio -> Calculating FFT.
        Complex[] y = FFT.fft(s);   

        // Saving result in a file for plotting using python for example
        PrintWriter writer;
        try {
            writer = new PrintWriter("FFT.txt");
            writer.print("X=[");
            for (int index = 0; index < y.length; index++) {
                double a = y[index].abs();
                writer.print(a+",");
            }
            writer.print("]");
            writer.close();
        } catch (FileNotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return y;
    }
    
    public double[] Etalonnage(Complex[] TFD_signal) {
        double[] result = new double[2];

        double[] module_TF = new double[TFD_signal.length/2];
        double[] BF = new double[module_TF.length/3];
        double[] HF = new double[(module_TF.length-(module_TF.length/3))];

        for (int i = 0; i < module_TF.length; i++) {
            module_TF[i] = TFD_signal[i].abs();
            if(i < module_TF.length/3){
                BF[i] =  module_TF[i];
            }
            else{
                HF[i-(module_TF.length/3)] =  module_TF[i];
            }
        }

        result = FFT.max(BF);
        pic_BF = result[0];
        ind_BF = (int)result[1];

        result = FFT.max(HF);
        pic_HF = result[0];
        ind_HF = (int)result[1];

        double decision_BF = FFT.average(BF,ind_BF, 10);
        double decision_HF = FFT.average(HF, ind_HF, 10); 
        
        System.out.println(" Pic BF : "+pic_BF+" Decision BF : "+decision_BF+" Pic HF : "+pic_HF+" Decision HF : "+decision_HF);
        
        result[0] = decision_BF;
        result[1] = decision_HF;

        return result;
    }

    public void Interpretation(Complex[] TFD_signal, Hero hero) {

        double[] module_TF = new double[TFD_signal.length/2];
        double[] BF = new double[module_TF.length/3];
        double[] HF = new double[(module_TF.length-(module_TF.length/3))];


        for (int i = 0; i < module_TF.length; i++) {
            module_TF[i] = TFD_signal[i].abs();
            if(i < module_TF.length/3){
                BF[i] =  module_TF[i];
            }
            else{
                HF[i-(module_TF.length/3)] =  module_TF[i];
            }
        }
 
        System.out.println("Interpretation avec : ");
        System.out.println(" Son grave " + AudioVoiceSetup.getDecision_OU_BF() + "  " + AudioVoiceSetup.getDecision_OU_HF());
        System.out.println(" Son aigu " + AudioVoiceSetup.getDecision_I_BF() + "  " + AudioVoiceSetup.getDecision_I_HF());
        System.out.println("**********************");

        if(FFT.max(BF)[0] > AudioVoiceSetup.getDecision_OU_BF() ){ // & FFT.max(HF)[0] < AudioVoiceSetup.getDecision_OU_HF()
            System.out.println("OU "+FFT.max(BF)[0] +" -- "+ FFT.max(HF)[0]);
            System.out.println("C'est un son grave");
            hero.jump();
        }
        else if( FFT.max(HF)[0] >  AudioVoiceSetup.getDecision_I_HF()){ //FFT.max(BF)[0] < AudioVoiceSetup.getDecision_I_BF() &
            System.out.println("OU "+FFT.max(BF)[0] +" -- "+ FFT.max(HF)[0]);
            System.out.println("C'est un son aigu");
            hero.slide();
        }
        else{
            System.out.println("Impossible d'interpreter");
            System.out.println("OU "+FFT.max(BF)[0] +" -- "+ FFT.max(HF)[0]);
        }    
    }
}