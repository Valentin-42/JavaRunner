import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/*
Valentin VIAL 2G1 TD3 TP6.
*/

public class AudioIO {

   
    //Affiche tout les mixers dans la console
    public static void printAudioMixers(){ 
        System.out.println("Mixers:");
        Arrays.stream(AudioSystem.getMixerInfo()).forEach(e -> System.out.println("- name=\"" + e.getName()+ "\" description=\"" + e.getDescription() + " by " + e.getVendor() + "\""));
    }

    //Donne plus d'infos sur le mixer donne en parametre
    public static Mixer.Info getMixerInfo(String mixerName) {
        return Arrays.stream(AudioSystem.getMixerInfo()).filter(e -> e.getName().equalsIgnoreCase(mixerName)).findFirst().get();
    }

    //Retourne la sortie audio correspondant au nom donne en parametre
    public static SourceDataLine obtainAudioOutput(String mixerName, int sampleRate) throws LineUnavailableException{ 
        
        AudioFormat format_audio = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, 2, 4, 44100, false);
        Mixer.Info Sortie_Audio_Utilise = getMixerInfo(mixerName);
        SourceDataLine Source = AudioSystem.getSourceDataLine(format_audio, Sortie_Audio_Utilise);
        Source.open();

        return Source;
    }

    //Retourne l'entrée audio correspondante au nom donne en parametre
    public static TargetDataLine obtainAudioInput(String mixerName, int sampleRate) throws LineUnavailableException{
        
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, 2, 4, 44100, false);
        Mixer.Info Entree_Audio_Utilise = getMixerInfo(mixerName);
        TargetDataLine line = AudioSystem.getTargetDataLine(format,Entree_Audio_Utilise);
        
        line.open();

        return line;
     }

    public static void main(String[] args) {
        
        try {

            SourceDataLine Source = obtainAudioOutput("Haut-parleurs (Realtek(R) Audio)", 44100);
            TargetDataLine Target = obtainAudioInput("Internal Microphone (AMD Audio ", 44100);
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Thread thread_source = new Thread(){
                @Override
                public void run() {
                   Source.start();
                   while (true) {
                    Source.write(out.toByteArray(), 0, out.size());             
                   }
                }
            };

            Thread thread_target = new Thread(){
                @Override
                public void run() {
                    byte[] data = new byte[Target.getBufferSize()/5];
                    int nbs_byte_lus;
                    Target.start();
                    while (true) {
                        nbs_byte_lus = Target.read(data,0,data.length); 
                        out.write(data, 0, nbs_byte_lus);
                    }
                }
            };
            System.out.println("Starting record");
            thread_target.start();
            Thread.sleep(5000);
            Target.stop();
            Target.close();
            System.out.println("End record");

            System.out.println("Starting play back");
            thread_source.start();
            Thread.sleep(5000);
            Source.stop();
            Source.close();
            System.out.println("End play back");

        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}   
