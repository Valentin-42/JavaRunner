import javafx.geometry.Rectangle2D;
/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class Bird extends AnimatedThing{
    Bird(String FilePath, int pos_x, int pos_y){
        /*
        parametres fixes
        single frame w = 750
        single frame h = 535
        offset btw = 5
        max index = 1; (nbs de frame dans le spritesheet)
        pref w = 100
        pref h = 50
        */ 
        
        super(FilePath, pos_x, pos_y, 0, 1, 0, 5, 0, 80, 80, true);
    }

    @Override
    public void ArrangeViewport(int index, int statut) {
        switch (statut) {
            case 0: //Line 1 sprite sheet parameters (Run for the hero sprite sheet)
                single_frame_height = 750;
                single_frame_width = 735;
                break;
            default:
                System.out.println("Erreur statut Bird"); 
                statut = 0;
                break;
        }

        image_sprite.setViewport(new Rectangle2D((single_frame_width+2*offset_between_frame)*index, (single_frame_height+2*offset_between_frame_statut)*statut, single_frame_width, single_frame_height));
    }

    @Override
    public void update(long time) {
        counter +=1;
        if(counter == between_frame_duration){
            counter = 0;
            index +=1;
            if(index > max_index){
                index = 0;
            }
            ArrangeViewport(index, statut);
        }
    }
}
