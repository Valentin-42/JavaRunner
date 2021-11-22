import java.util.ArrayList;
import javax.sound.sampled.AudioSystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/*
Valentin VIAL 2G1 TD3 TP6.
Interface scene settting up peripherals. 
*/
public class GameSettings extends GUI_Scene {
    Button[] boutons;
    ArrayList<String> selected_periph = new ArrayList<String>();
    Label LabelInput_Periph;
    Label LabelOutput_Periph;
    Label Periph_Complete;

    GameSettings(Parent oldRoot,int heightSetByUser,int widthSetByUser,boolean allowPGAccess, Pane pane){
        super(oldRoot, heightSetByUser, widthSetByUser,allowPGAccess,pane);

        LabelInput_Periph = CreateLabel("Select your recording peripheral", "Arial", 15, 150, 10);
        LabelOutput_Periph = CreateLabel("Select your output peripheral", "Arial", 15, 150, 10);
        Periph_Complete = CreateLabel("Peripherals setup complete", "Arial", 15, 150, 10);

        LabelInput_Periph.setVisible(true);
        LabelOutput_Periph.setVisible(false);
        Periph_Complete.setVisible(false);
    }

    @Override
    public void GUISetup() {
        BackgroundSetup("MenuBackground.png");
        Button Save_Back = CreateButton("Save & GO Back", "-fx-font: 20 arial; -fx-base: #b6e7c9;", 400, 200);
        Save_Back.setVisible(false);
        AudioIO.printAudioMixers();
        boutons = new Button[AudioSystem.getMixerInfo().length];
        for(int i=0; i<AudioSystem.getMixerInfo().length; i++){
            String Bouton_Name= String.valueOf(AudioSystem.getMixerInfo()[i].getName());
            boutons[i] = CreateButton(AudioSystem.getMixerInfo()[i].getName(),"-fx-font: 13 arial; -fx-base: #b6e7c9;", 30, 40+i*40);
            boutons[i].setOnAction(e->{
            switch (selected_periph.size()) {
                case 0:
                    LabelInput_Periph.setVisible(false);
                    LabelOutput_Periph.setVisible(true);
                    Periph_Complete.setVisible(false);
                    Save_Back.setVisible(false);
                    selected_periph.add(Bouton_Name);
                    break;
                case 1:
                    LabelInput_Periph.setVisible(false);
                    LabelOutput_Periph.setVisible(false);
                    Periph_Complete.setVisible(true);
                    Save_Back.setVisible(true);
                    selected_periph.add(Bouton_Name);
                    break;
                case 2:
                    LabelInput_Periph.setVisible(true);
                    LabelOutput_Periph.setVisible(false);
                    Periph_Complete.setVisible(false);
                    Save_Back.setVisible(false);
                    selected_periph.clear();
                    break;
                default:
                    break;
            }
                System.out.println(selected_periph);
            });
        }
       
        Save_Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                AudioVoiceSetup.setAudio_Input_Device(selected_periph.get(0));
                AudioVoiceSetup.setAudio_Output_Device(selected_periph.get(1));
                App.getPrimaryStage().setTitle("Profile");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameProfile(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
            }
        });
    }
}
