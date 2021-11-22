import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
/*
Valentin VIAL 2G1 TD3 TP6.
*/
public abstract class AnimatedThing {

    int pos_x;
    double pos_y;
    double pos_x0;

    int masse = 1;
    int jump_acceleration = 350; 
    double vy=0;
    double ay = 0;
    ImageView image_sprite;
    int pref_height;
    int pref_width;

    int statut;
    int index;
    int max_index=9;

    int between_frame_duration = 5;
    int offset_between_frame;
    int offset_between_frame_statut;
    int single_frame_width;
    int single_frame_height;

    int counter;

    AnimatedThing(String FilePath, int pos_x, int pos_y,int index, int max_index,int statut,  int offset_between_frame,int offset_between_frame_statut,int pref_width, int pref_height, boolean preserve_ratio){
        //initialisation of positions
        this.pos_x = pos_x;
        this.pos_x0 = pos_x;
        this.pos_y = pos_y;
        //initialisation of animation frame sequence
        this.index = index;
        this.statut = statut;
        this.max_index = max_index;
        
        this.offset_between_frame = offset_between_frame;
        this.offset_between_frame_statut = offset_between_frame_statut;

        //Initialisation sprite on scene.
        this.pref_height = pref_height;
        this.pref_width = pref_width;
        Image image = new Image(FilePath);
        this.image_sprite = new ImageView(image);
        
        this.image_sprite.setX(pos_x);
        this.image_sprite.setY(pos_y);
        if(preserve_ratio==true){
            this.image_sprite.setPreserveRatio(preserve_ratio);
            this.image_sprite.setFitHeight(pref_height);
        }
        else{
            this.image_sprite.setFitHeight(pref_height);
            this.image_sprite.setFitWidth(pref_width);
        }
        ArrangeViewport(index, statut);
    }

    public void ArrangeViewport(int index, int statut){ 
        //Move the viewport to the corresponding sprite image animation//
           switch (statut) {
               case 0: //Line 1 sprite sheet parameters (Run for the hero sprite sheet)
                   single_frame_height = 507;
                   single_frame_width = 415;
                    image_sprite.setViewport(new Rectangle2D((single_frame_width+2*offset_between_frame)*index, (single_frame_height+2*offset_between_frame_statut)*statut, single_frame_width, single_frame_height));
                   break;
                case 1://Line 2 sprite sheet parameters (Jump for the hero sprite sheet)
                    single_frame_height = 536;
                    single_frame_width = 407;
                    image_sprite.setViewport(new Rectangle2D((single_frame_width+2*offset_between_frame)*index, (507+offset_between_frame_statut)*statut, single_frame_width, single_frame_height));

                    break;
                case 2: //Line 3 sprite sheet parameters (Slide for the hero sprite sheet)
                    single_frame_height = 389;
                    single_frame_width = 394;
                    image_sprite.setViewport(new Rectangle2D((single_frame_width)*0, (536+2*offset_between_frame_statut)*statut, single_frame_width, single_frame_height));
                    break;
               default:
                   System.out.println("Erreur statut"); 
                   statut = 0;
                   break;
           }
        }
    
    public void update(long time){
        counter +=1;
        if(counter == between_frame_duration){
            counter = 0;
            index +=1;
            if(index > max_index){
                index = 0;
            }
            ArrangeViewport(index, statut);
        }

        //adding gravity
        if(pos_y < 180){
            ay = 9.8;
            vy = vy + 0.16*ay;
            pos_y =  pos_y + 0.16*vy;
            getImage_sprite().setY(pos_y);
        }
        else{
            if(statut == 1){
                statut = 0;
            }
            if(statut == 2 & index == 9){
                statut = 0;
            }
        }
        
    }

    public void jump() {
        if(pos_y >= 180){
            ay = 0;
            vy = 0;
            ay = 9.8-jump_acceleration;
            vy = vy + 0.16*ay;
            pos_y =  pos_y + 0.16*vy;
            getImage_sprite().setY(pos_y);
            statut = 1;
        }
    }

    public void slide() {
        statut = 2;
        index =0;
    }

    public Rectangle2D Rectangle2DgetMyHitBox(){
        int tolerance = 30;
        if(statut == 2){
            return new Rectangle2D(this.getImage_sprite().getX(), this.getImage_sprite().getY()+this.getImage_sprite().getY()/2, this.pref_width-tolerance, (this.pref_height-tolerance)/2);
        }
        else{
            return new Rectangle2D(this.getImage_sprite().getX(), this.getImage_sprite().getY(), this.pref_width-tolerance, this.pref_height-tolerance);
        }
    }
    
    public boolean Rectangle2DisCollide(Rectangle2D myHitbox){
        return this.Rectangle2DgetMyHitBox().intersects(myHitbox);
    }

    public double getPos_x0() {
        return pos_x0;
    }

    public ImageView getImage_sprite() {
        return image_sprite;
    }
}