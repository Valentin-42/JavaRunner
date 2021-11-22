import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/*
Valentin VIAL 2G1 TD3 TP6.
AudioGestion : Thread managing audio.
*/
public class AudioGestion {
    
    String audioInput_name;
    String audioOutput_name;
    Hero hero;

    AudioGestion(String audioInput,String audioOutput, Hero hero){
        this.audioInput_name = audioInput;
        this.audioOutput_name = audioOutput;
        this.hero = hero;
    }

    public int ListingInput(){
        Thread thread_listing = new Thread(){
            @Override
            public void run() {

                TargetDataLine inLine;
                SourceDataLine outLine;

                try {
                    inLine = AudioIO.obtainAudioInput(audioInput_name, 44100);
                    outLine = AudioIO.obtainAudioOutput(audioOutput_name, 44100);

                    inLine.open();
                    inLine.start();
                    outLine.open();
                    outLine.start(); 

                    AudioProcessor audioProcessor = new AudioProcessor(inLine, outLine, hero);
                    new Thread(audioProcessor).start();   

                } catch (LineUnavailableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        thread_listing.start();
        return 0;
    }
}
