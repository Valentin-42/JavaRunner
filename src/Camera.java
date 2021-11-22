/*
Valentin VIAL 2G1 TD3 TP6.
*/
public class Camera {
    double x;
    double y;

    double x0;

    int timer_test = 0;
    int x_final = 150;
    int i =0;
    double k =1.3;
    double m =1;
    double f =1.2;

    double vx = 0;
    double axP = 0;
    double x_p;
    double ax = 0;
    double delta;

    Camera(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        System.out.println(this.x+" , "+this.y);
        return "";
    }

    public void update(long t, Hero hero){
        
        x_p = this.x;
        this.x = this.x + 0.016*vx;
        axP = ax;
        delta = ((hero.getImage_sprite().getX()-x_p)-(2*x_final-hero.getPos_x0()));
        ax = -vx*(f/m) + (k/m)*(delta);

        vx = vx + 0.016*axP;

        hero.getImage_sprite().setX(hero.getPos_x0()-this.x);
        }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
