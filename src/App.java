import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/*
Valentin VIAL 2G1 TD3 TP6.
Programme principal : a executer pour lancer le jeu.
*/
public class App extends Application{

    private static Stage primaryStage; 

    @Override
    public void start(Stage stage){      
        
        primaryStage = stage;

        //Creation du menu de jeu
        stage.setTitle("Java game");
        Group root = new Group();
        Pane pane = new Pane(root);
        Scene MenuScene = new GameMenu(pane, 600, 300, true, pane);        

        stage.setScene(MenuScene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}