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
public class GameRules extends GUI_Scene{

    GameRules(Parent oldRoot,int heightSetByUser,int widthSetByUser,boolean allowPGAccess, Pane pane){
        super(oldRoot, heightSetByUser, widthSetByUser,allowPGAccess,pane);
    }
    
    @Override
    public void GUISetup() {
        BackgroundSetup("Rules_Screen_1.png");
        Button button_Next = CreateButton("Next", "-fx-font: 20 arial; -fx-base: #b6e7c9;",520,50);
        button_Next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { //Creating the GameScene.
                BackgroundSetup("Rules_Screen_2.png");
                Button button_Menu = CreateButton("Menu", "-fx-font: 20 arial; -fx-base: #b6e7c9;",520,50);
                button_Menu.setOnAction(new EventHandler<ActionEvent>() {
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
        });
    }
}
