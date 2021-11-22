import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/*
Valentin VIAL 2G1 TD3 TP6.
This object contains :
- Peripherals for recording and playing back audio.
- all user's voice characteristics.
*/
public class AudioVoiceSetup  implements Runnable {

    static double decision_OU_BF;
    static double decision_OU_HF;

    static double decision_I_BF;
    static double decision_I_HF;

    private int Choix; //Choix = 0 -> etalonnage d'un son grave 'OU' / Choix = 1 -> etalonnage d'un son aigu 'I'

    private TargetDataLine audioInput;
    private SourceDataLine audioOutput;

    private AudioSignal inputSignal;
    private AudioSignal outputSignal;

    static String Audio_Output_Device;
    static String Audio_Input_Device;

    AudioVoiceSetup(String audioInput_name, String audioOutput_name, int Choix) throws LineUnavailableException{
        AudioIO.printAudioMixers();
        System.out.println(Audio_Input_Device+" , "+Audio_Output_Device);
        audioInput = AudioIO.obtainAudioInput(Audio_Input_Device, 44100); //"Internal Microphone (AMD Audio Device)"
        audioOutput = AudioIO.obtainAudioOutput(Audio_Output_Device, 44100); //"Haut-parleurs (Realtek(R) Audio)"

        this.inputSignal = new AudioSignal();
        this.outputSignal = new AudioSignal();
        this.Choix = Choix;

        audioInput.open();
        audioInput.start();
        audioOutput.open();
        audioOutput.start(); 
    }

    /**
     * Thread se chargeant de lancer l'enregistrement et de traiter le signal. 
     */
    @Override
    public void run() {
        Record();
        Etalonnage(Choix);        
    }

    private void Record() {
        System.out.println("Record son de type +"+Choix);
        try {
            audioInput.open();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        audioInput.start();
        inputSignal.recordFrom(audioInput,3);
        inputSignal.playTo(audioOutput);
        audioInput.flush();
        audioInput.stop();
        audioInput.close();	
        System.out.println("Record stop");
    }

    private void Etalonnage(int Choix){
        double[] result = new double[2];
        try {
            audioOutput.open();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        audioOutput.start();
        Complex[] fft = outputSignal.applyFT(); 
        result = outputSignal.Etalonnage(fft); 
        audioOutput.flush();
        audioOutput.stop();
        audioOutput.close();
        switch (Choix) {
            case 0: //Son grave
                decision_OU_BF = result[0];
                decision_OU_HF = result[1];
                break;
            case 1: //Son aigu
                decision_I_BF = result[0];
                decision_I_HF = result[1];
                break;
            default:
                break;
        }
        System.out.println("Seuils BF : " + result[0]+" Seuil HF : "+result[1]);
    }
    
    public static void setAudio_Input_Device(String audio_Input_Device) {
        Audio_Input_Device = audio_Input_Device;
    }
    public static void setAudio_Output_Device(String audio_Output_Device) {
        Audio_Output_Device = audio_Output_Device;
    }
    public static String getAudio_Input_Device() {
        return Audio_Input_Device;
    }
    public static String getAudio_Output_Device() {
        return Audio_Output_Device;
    }    
    public static double getDecision_I_BF() {
        return decision_I_BF;
    }
    public static double getDecision_I_HF() {
        return decision_I_HF;
    }
    public static double getDecision_OU_BF() {
        return decision_OU_BF;
    }
    public static double getDecision_OU_HF() {
        return decision_OU_HF;
    }
    
}