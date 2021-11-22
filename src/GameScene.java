import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/*
Valentin VIAL 2G1 TD3 TP6.
Scene de jeu. 
*/
public class GameScene extends Scene {

    Camera camera = new Camera(0,65); // Une camera centrée sur le joueur.
    static boolean stop = false; // True : Game active / False : Game over
    static Pane branch; // Branch 
    static int score = 0; //Player score
    static int counter = 0; // Counter for specific late loops and updates
    static boolean allow_Collision = true; // True : Player can collide with other objects / False : invicibility
    static Hero hero; //Player's avatar 
    static Bird vulture; // A bird ennemi
    static numberOfLives Hero_Life; // Player's life
    static ArrayList<ImageView> Backgrounds; // List of backgrounds on the scene
    static ArrayList<Foe> Ennemis = new ArrayList<Foe>(); // List of static ennemies
    static double backgrounds_speed = 3; // Player speed.
    static double vulture_speed=4; // Bird ennemi speed.

    static AudioGestion gestion_audio; //Object managing audio stuff.
    static int action; // integer matching Player's action : jumping or sliding 
    static Label label; // text container to display score on screen
    static int invincibility_timer; // Counting time during invicibility
    static int invincibility_time=80; // Max time for invicibility

    AnimationTimer animationTimer = new AnimationTimer(){
        public void handle(long time) {
            if(stop == false){
                camera.update(time, hero); // Aligne la camera sur le hero
                GameScene.update(time); //Update la position des décors & obstacles
                hero.update(time); // Update l'animation du personnage
                vulture.update(time);// Update l'animation du vautour
            }
            else{
                this.stop(); // Stop le timer car fin du jeu
            }
        }        
    };           

    //Update animation & Scene objects
    public static void update(long time){
        invincibility_timer += 1;
        counter += 1;
        if(counter == 10){
            counter =0;
            score+=1;
            label.setText("Score : "+String.valueOf(score));
            if(score%100 == 0){
                backgrounds_speed +=1;
            }
        }
        if(invincibility_timer == invincibility_time){
            allow_Collision = true;
        }
       
        for (ImageView Background : Backgrounds) {
           Background.setX(Background.getX()-backgrounds_speed);
            if(Background.getX() <= -800){
                Background.setX((800+Background.getX())+800);
            }
        }

        for (Foe ennemis  : Ennemis) {
            ImageView sprite = ennemis.getImageView();
            sprite.setX(sprite.getX()-backgrounds_speed);
            if(sprite.getX() <= -50){
                sprite.setX(800 + (Math.random() * (1000 - 800))); // Actualisation de la position des obstacles
            }
            if((ennemis.Rectangle2DisCollide(hero.Rectangle2DgetMyHitBox())) && (allow_Collision == true)){                
                Hero_Life.Damage(1);
                allow_Collision = false;
                invincibility_timer = 0;
            }
        }

        vulture.getImage_sprite().setX(vulture.getImage_sprite().getX() - (backgrounds_speed+vulture_speed));
        if( vulture.getImage_sprite().getX() <= -50){
            vulture.getImage_sprite().setX(3000 + (Math.random() * (1000))); // Actualisation de la position des obstacles
        }
        if((vulture.Rectangle2DisCollide(hero.Rectangle2DgetMyHitBox())) && (allow_Collision == true)){
            Hero_Life.Damage(1);
            allow_Collision = false;
            invincibility_timer = 0;
        }

        if(Hero_Life.Current_Life == 0){
            //Fin du jeu ...
            // Sauvegarde du score.
            System.out.println("Fin du jeu pour "+String.valueOf(GameProfile.getName_field().getCharacters()));
            File outFile = new File("SaveFile.txt");
            FileWriter writer;
            try {
                writer = new FileWriter(outFile,true);
                writer.write(GameProfile.getName_field().getCharacters()+" : "+String.valueOf(score)+"\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("Erreur : fichier de sauvegarde introuvable");
                e.printStackTrace();
            }
            //Switching scene
            Stop_Game();
            Hero_Life.Damage(1);
        }

    }

    //Stop the game and display the game-over UI.
    private static void Stop_Game() {
        stop = true; // Stop the timer.
        //New UI 
        Button button = new Button("Save & Try again");
        button.setLayoutX(170);
        button.setLayoutY(50);
        button.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        button.setOnAction(new EventHandler<ActionEvent>() { //Onclick event.
            @Override
            public void handle(ActionEvent event) { 
                //Creating the GameScene.
                App.getPrimaryStage().setTitle("Menu");
                Group root = new Group();
                Pane New_pane = new Pane(root);
                Scene scene = new GameMenu(New_pane, 600, 300, true, New_pane);
                App.getPrimaryStage().setScene(scene);
            }
        });

        staticThing Background = new staticThing("/ressources/images/MenuBackground.png", 0, 0, 400,800,false);
       
        label = new Label("Votre score : "+score);
        label.setFont(new Font("Arial", 32));
        label.setTextFill(Color.BLACK);
        label.setLayoutX(170);
        label.setLayoutY(150);
        
        //Detach all 'game-objects'.
        Ennemis.clear();
        //Attach new 'game-over-objects'.
        branch.getChildren().clear();
        branch.getChildren().addAll(Background.getImageView(),button,label);       
    }

    //Construct the game scene
    GameScene(Parent oldRoot,int heightSetByUser,int widthSetByUser,boolean allowPGAccess, Pane pane) {
        super(oldRoot, heightSetByUser, widthSetByUser, allowPGAccess);
        branch = pane;
        branch.getChildren().clear(); //game over UI detached.
        stop=false; // game active
        score = 0; // new score

        //Setup all game-objects
        Backgrounds = this.BackgroundSetup(); 
        Hero_Life = this.LifeSetup();
        this.ennemisSetup();
        hero = this.HeroSetup();
        gestion_audio = new AudioGestion(AudioVoiceSetup.getAudio_Input_Device(),AudioVoiceSetup.getAudio_Output_Device(),hero);    
        animationTimer.start(); 
        displayScore();

       //Event creation 
        this.setOnKeyPressed( (event)->{
            if((event.getCode() == KeyCode.UP)){
                hero.jump();
            }
            if((event.getCode() == KeyCode.DOWN)){
                hero.slide();
            }
            if((event.getCode() == KeyCode.SPACE)){
                gestion_audio.ListingInput(); // New thread
            }
            
        });

    }

    private void displayScore() {
        label = new Label("Score : "+String.valueOf(score));
        label.setFont(new Font("Arial", 18));
        label.setTextFill(Color.RED);
        label.setLayoutX(450);
        label.setLayoutY(10);
        branch.getChildren().add(label);
    }

    private void ennemisSetup() {
        Ennemis.add(new Foe("/ressources/images/cactus.png", 1200, 180, 100));
        for (Foe ennemi : Ennemis) {
            ImageView e =  ennemi.getImageView();
            branch.getChildren().addAll(e);
        }
        vulture = new Bird("/ressources/images/spritesheet_bird.png", 10000, 160);//160
        branch.getChildren().add(vulture.getImage_sprite());
    }

    private ArrayList<ImageView> BackgroundSetup() {   

        staticThing Background = new staticThing("/ressources/images/desert.png", 0, 0, 400,800,false);
        staticThing Background2 = new staticThing("/ressources/images/desert.png", 800, 0, 400,800,false);

        ImageView Background_sprite = Background.getImageView();
        ImageView Background2_sprite = Background2.getImageView();

        Background_sprite.setX(Background.getX()-this.camera.getX());
        Background_sprite.setY(Background.getY()-this.camera.getY());

        Background2_sprite.setX(Background2.getX()-this.camera.getX());
        Background2_sprite.setY(Background2.getY()-this.camera.getY());
       
        ArrayList<ImageView> Backgrounds = new ArrayList<ImageView>();
        Backgrounds.add(Background_sprite);
        Backgrounds.add(Background2_sprite);

        branch.getChildren().addAll(Background_sprite,Background2_sprite);        

        return Backgrounds;
    }

    private numberOfLives LifeSetup() {
        numberOfLives Life = new numberOfLives("/ressources/images/coeur.png",3, 10, 20);
        for (staticThing Coeur : Life.getCoeurs()) {
            ImageView Coeur_sprite = Coeur.getImageView();
            branch.getChildren().addAll(Coeur_sprite);
        }
        return Life;
    }   

    private Hero HeroSetup(){
        Hero hero = new Hero("/ressources/images/spritesheet_hero.png", 270, 180); //y=180
        ImageView hero_sprite = hero.getImage_sprite();
        branch.getChildren().addAll(hero_sprite); 
        return hero;
    }

}