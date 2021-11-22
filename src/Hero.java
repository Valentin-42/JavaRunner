/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class Hero extends AnimatedThing{
   
    int time=0;

    Hero(String FilePath, int x, int y){
        /*
        Parametres communs : 
        offset_between_frame = 5;
        offset_between_frame_statut = 5;

        Petits défauts sur la dernière ligne -> Séparer en plusieurs cas.
        */ 
        super(FilePath, x,y, 0,9,0, 5,5,75,102,false); //Parametres a trouver
    }
}