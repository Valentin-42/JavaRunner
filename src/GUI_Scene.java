import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/*
Valentin VIAL 2G1 TD3 TP6.
Basic properties for a Menu scene.
*/
public class GUI_Scene extends Scene{
    static Pane branch;
    
    GUI_Scene(Parent oldRoot,int heightSetByUser,int widthSetByUser,boolean allowPGAccess, Pane pane){
        super(oldRoot, heightSetByUser, widthSetByUser);
        branch = pane;
        GUISetup();
    }


    public void GUISetup() {
        CreateButton("My button", "-fx-font: 22 arial; -fx-base: #b6e7c9;", 0, 0);
    }

//Basic UX functions :

    public void BackgroundSetup(String FileName) {
        staticThing Background = new staticThing("/ressources/images/"+FileName, 0, 0, 400,800,false);
        ImageView Background_sprite = Background.getImageView();
        Background_sprite.setX(Background.getX());

        branch.getChildren().addAll(Background_sprite);        
    }    

    public Button CreateButton(String Name, String Style, int posX, int posY) {
        Button buttonPlay = new Button(Name);
        buttonPlay.setLayoutX(posX);
        buttonPlay.setLayoutY(posY);
        buttonPlay.setStyle(Style);

        branch.getChildren().add(buttonPlay);
        return buttonPlay;
    }

    public Label CreateLabel(String Name, String Police, int Taille, int posX, int posY) {

        Label label = new Label(Name);
        label.setFont(new Font(Police, Taille));
        label.setTextFill(Color.BLACK);
        label.setLayoutX(posX);
        label.setLayoutY(posY);

        branch.getChildren().add(label);
        return label;
    }

    public TextField CreateTextInput(String Name, int Width, int posX, int posY) {
        TextField textField = new TextField(Name);
        textField.setMinWidth(Width);
        textField.setLayoutX(posX);
        textField.setLayoutY(posY);
        branch.getChildren().add(textField);
        return textField;
    }
}
