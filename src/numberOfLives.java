import java.util.ArrayList;
/*
Valentin VIAL 2G1 TD3 TP6.
Object managing Hero's life at each moment. 
*/
public class numberOfLives extends staticThing{
    int numberOfLives;
    ArrayList<staticThing> Coeurs = new ArrayList<staticThing>();
    int Current_Life;
    numberOfLives(String ImagePath,int TotalLife, int x, int y){
        /*
        pref_width = 0 if preserve ratio = true;
         */
        super(ImagePath,x,y,30,0,true);
        Coeurs.add(this);
        this.Current_Life = TotalLife;
        for (int index = 1; index < TotalLife; index++) {
            Coeurs.add(new staticThing(ImagePath, x+index*30, y, 30, 0, true));
        }
    }

    public ArrayList<staticThing> getCoeurs() {
        return Coeurs;
    }

    public void Damage(int damage){
        if(Current_Life > 0){
            for (int i = 0; i < damage; i++) {
                Coeurs.get(Current_Life-1-i).getImageView().setVisible(false);
                Current_Life = Current_Life - 1;
            }
        }
        else{
            Current_Life = Current_Life - 1;
        }        
    }

}
