/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class Foe extends staticThing {
    Foe(String FilePath, int x, int y,int pref_height){
        /*
        Parametres :
        single_frame_width = 40;
        single_frame_height = 50;
        pref_width = 0 if preserve ratio = true;
        */ 
        super(FilePath, x,y,pref_height,30,true);

    }
}
