import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class AudioProcessor implements Runnable {
    private AudioSignal inputSignal, outputSignal;
    private TargetDataLine audioInput;
    private SourceDataLine audioOutput;
    public Hero hero;

    public AudioProcessor(TargetDataLine audioInput, SourceDataLine audioOutput, Hero hero) 
    {
        this.audioInput = audioInput;
        this.audioOutput = audioOutput;
        this.inputSignal = new AudioSignal();
        this.outputSignal = new AudioSignal();
        this.hero = hero;
    }

    /**
     * Thread se chargeant de lancer l'enregistrement et de traiter le signal. 
     */
    @Override
    public void run() {
        Record();
        Process_Audio(hero);
    }

    public void Record() {
        System.out.println("Record");
        try {
            audioInput.open();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        audioInput.start();
        inputSignal.recordFrom(audioInput,2);
        audioInput.stop();
        audioInput.close();	
        System.out.println("Record stop");
                
    }

    public void Process_Audio(Hero hero){
        try {
            audioOutput.open();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        audioOutput.start();
        Complex[] fft = outputSignal.applyFT(); 
        outputSignal.Interpretation(fft,hero);
        audioOutput.stop();
        audioOutput.close();
    }

    public void PlayBack() {
        System.out.println("Play back");

        try {
            audioOutput.open();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        audioOutput.start();
        outputSignal.playTo(audioOutput); 
        audioOutput.stop();
        audioOutput.close();
        System.out.println("Play back stop");

    }


    public static void main(String[] args) throws LineUnavailableException {
        AudioIO.printAudioMixers();

        TargetDataLine inLine = AudioIO.obtainAudioInput("Internal Microphone (AMD Audio ", 44100);
        SourceDataLine outLine = AudioIO.obtainAudioOutput("Haut-parleurs (Realtek(R) Audio)", 44100);

        inLine.open();
        inLine.start();
        outLine.open();
        outLine.start();

       // AudioProcessor as = new AudioProcessor(inLine, outLine, hero);

        System.out.println("A new thread appear !");
       // new Thread(as).start();            
        System.out.println("Thread finished !");
    }

}