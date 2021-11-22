import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.sound.sampled.LineUnavailableException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class GameProfile extends GUI_Scene {

    static TextField Name_field;
    static boolean IsOuCompleted = false;
    static boolean IsICompleted = false;


    GameProfile(Parent oldRoot,int heightSetByUser,int widthSetByUser,boolean allowPGAccess, Pane pane){
        super(oldRoot, heightSetByUser, widthSetByUser,allowPGAccess,pane);
        Name_field = CreateTextInput("Votre nom ...", 10, 220, 30);
        CreateLabel("Please wait for playback before recording again", "Arial", 13, 170, 80);
    }

    static public TextField getName_field() {
        return Name_field;
    }

    static public boolean IsProfileCompleted() {
        if(Name_field != null & IsOuCompleted == true & IsICompleted ==true){
            return true;
        }
        else{
            return false;
        }
    }
    
    @Override
    public void GUISetup() {
        BackgroundSetup("MenuBackground.png");
        Button Etalonner_OU = CreateButton("Enregistrez le son : 'OU' ", "-fx-font: 22 arial; -fx-base: #b6e7c9;", 180, 120);
        Button Etalonner_I = CreateButton("Enregistrez le son : 'A' ", "-fx-font: 22 arial; -fx-base: #b6e7c9;", 180, 180);
        Button Save = CreateButton("Save", "-fx-font: 22 arial; -fx-base: #b6e7c9;", 250, 250);
        Button Settings = CreateButton("Settings", "-fx-font: 20 arial; -fx-base: #b6e7c9;", 0, 0);
        Save.setVisible(false);
        Etalonner_OU.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
               //Etalonner son grave
               try {
                AudioVoiceSetup audioVoiceSetup = new AudioVoiceSetup("Internal Microphone (AMD Audio ", "Haut-parleurs (Realtek(R) Audio)", 0);
                new Thread(audioVoiceSetup).start();
                IsOuCompleted = true;
                if(IsProfileCompleted() == true){
                    Save.setVisible(true);
                }
            } catch (LineUnavailableException e) {
                // TODO Auto-generated catch block
                System.out.println("Erreur audioVoiceSetup");
                e.printStackTrace();
            } // Son grave -> 0
            }
        });
        Etalonner_I.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
               //Etalonner son grave
               try {
                AudioVoiceSetup audioVoiceSetup = new AudioVoiceSetup("Internal Microphone (AMD Audio Device)", "Haut-parleurs (Realtek(R) Audio)", 1);
                Thread audio = new Thread(audioVoiceSetup);
                audio.start();
                IsICompleted =true;
                if(IsProfileCompleted() == true){
                    Save.setVisible(true);
                }                
            } catch (LineUnavailableException e) {
                // TODO Auto-generated catch block
                System.out.println("Erreur audioVoiceSetup");
                e.printStackTrace();
            } // Son grave -> 0
            }
        });
        Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                System.out.println("New player -> "+Name_field.getCharacters());
                App.getPrimaryStage().setTitle("Menu");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameMenu(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
            }
        });
        Settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                App.getPrimaryStage().setTitle("Settings");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameSettings(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
            }
        });
    }
}
