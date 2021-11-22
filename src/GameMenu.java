import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class GameMenu extends GUI_Scene {
    Pane branch;

    GameMenu(Parent oldRoot,int heightSetByUser,int widthSetByUser,boolean allowPGAccess, Pane pane){
        super(oldRoot, heightSetByUser, widthSetByUser,allowPGAccess,pane);
    }

    @Override
    public void GUISetup() {
        BackgroundSetup("MenuBackground.png");
        Button buttonPlay = CreateButton("PLAY", "-fx-font: 22 arial; -fx-base: #b6e7c9;",230,50);
        Button buttonProfile = CreateButton("My profile", "-fx-font: 22 arial; -fx-base: #b6e7c9;",150,120);
        Button buttonLeaderBoard = CreateButton("LeaderBoard", "-fx-font: 22 arial; -fx-base: #b6e7c9;",200,210);
        Button buttonRules = CreateButton("Controls & Rules", "-fx-font: 22 arial; -fx-base: #b6e7c9;",290,120);
        
        buttonPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                if(GameProfile.IsProfileCompleted()){
                    App.getPrimaryStage().setTitle("Run !");
                    Group root = new Group();
                    Pane New_pane = new Pane(root);
                    Scene scene = new GameScene(New_pane, 600, 300, true, New_pane);
                    App.getPrimaryStage().setScene(scene);
                }
                else{
                    App.getPrimaryStage().setTitle("Please complete your profile !");
                    Group root = new Group();
                    Pane New_pane = new Pane(root);
                    Scene scene = new GameProfile(New_pane, 600, 300, true, New_pane);
                    App.getPrimaryStage().setScene(scene);
                }
                
            }
        });

        buttonProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                App.getPrimaryStage().setTitle("My profile");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameProfile(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
            }
        });

        buttonLeaderBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                App.getPrimaryStage().setTitle("My profile");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameLeaderboard(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
            }
        });

        buttonRules.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                App.getPrimaryStage().setTitle("Rules & Controls");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameRules(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
            }
        });

    }
}
