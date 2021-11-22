import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/*
Valentin VIAL 2G1 TD3 TP6.
Class defining static object on the scene such as backgrounds / cactus ... 
*/
public class staticThing {

    double x; //Position
    double y;

    ImageView imageView; //image sprite

    int pref_height; //dimensions
    int pref_width;

    /** Construct a static thing 
    * @param ImagePath : relative path to image (.jpg/.png/... ) 
    * @param x,y : position on the scene 
    * @param pref_height,pref_height : dimensions 
    * @param Preserve_Proportion : boolean, true meaning resizing keeping the ratio height/width constant   
    */
    staticThing(String ImagePath, double x, double y, int pref_height, int pref_width, boolean Preserve_Proportion){
        this.x = x;
        this.y = y;
        this.pref_height= pref_height;
        this.pref_width = pref_width;

        Image image = new Image(ImagePath);
        this.imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        
        if(Preserve_Proportion==true){
            this.imageView.setPreserveRatio(Preserve_Proportion);
            this.imageView.setFitHeight(pref_height);
        }
        else{
            this.imageView.setFitHeight(pref_height);
            this.imageView.setFitWidth(pref_width);
        }
    }

    /**
     * 
     * @return a rectangle framing this object
     */
    public Rectangle2D Rectangle2DgetMyHitBox(){
        x = imageView.getX();
        y = imageView.getY();
        return new Rectangle2D(x, y, pref_width, pref_height);
    }
    /**
     * 
     * @param Hitbox : a rectangle 
     * @return true if this object's hitbox intersects the hitbox given. 
     */
    public boolean Rectangle2DisCollide(Rectangle2D Hitbox){
        return Rectangle2DgetMyHitBox().intersects(Hitbox);
    }

    //Getter and setter :
    public ImageView getImageView() {
        return imageView;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
