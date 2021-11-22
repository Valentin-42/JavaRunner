import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class GameLeaderboard extends GUI_Scene{



    GameLeaderboard(Parent oldRoot,int heightSetByUser,int widthSetByUser,boolean allowPGAccess, Pane pane){
        super(oldRoot,heightSetByUser,widthSetByUser,allowPGAccess,pane);
        readFile_DisplayLeaderBoard();
    }

    private void readFile_DisplayLeaderBoard() {
        try {
            int offset = 0;
            File myObj = new File("SaveFile.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                CreateLabel(data, "Arial",20, 200, 20+offset);
                offset+=30;   
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur ouverture fichier en lecture !");
            e.printStackTrace();
        }
    }

    @Override
    public void GUISetup(){
        BackgroundSetup("MenuBackground.png");
        Button BackButton = CreateButton("Go Back",  "-fx-font: 22 arial; -fx-base: #b6e7c9;", 460, 250);
        BackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                App.getPrimaryStage().setTitle("Menu");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameMenu(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
                
            }
        });
    }    
}
