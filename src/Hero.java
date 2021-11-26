import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Hero extends AnimatedThing{

    private double invincibility;
    private int numberOfLives;
    private int vx=5;
    private int vy=0;
    private double g =0.4;
    private double m = 20.0;
    private double fy=0, ay;

    Hero(double x, double y, String fileName){
        super(x,y,fileName);
        this.imageView.setViewport(new Rectangle2D(getViewportx(this.index)[0], getViewportx(this.index)[1], getWidth(this.index), 100));
        this.hitBox = new Rectangle2D(this.x, this.y, getWidth(index), 100);
        this.numberOfLives=3;
    }

    public void update(long time, Foe foe1, Foe foe2, Foe foe3) {
       long btwUpdate = time - lastUpdate;
        if (btwUpdate > this.animationDuration) {
            //Change the hero index
            this.index++;
            this.index %= 6;
            this.x+=vx;

            //Jumping
            if (this.attitude == Attitude.JUMPING_UP) {
                this.index = 6;
                this.y *= 0.85;
                if (this.y < 100)
                    this.attitude = Attitude.JUMPING_DOWN;
            }

            //Gravity
           if (this.y < 250 && this.attitude != Attitude.JUMPING_UP) {
                this.index = 7;
                this.y /= 0.9;
            } else if (this.y > 250 && this.attitude != Attitude.JUMPING_UP) {
                this.y = 250;
            }


            this.getImageView().setViewport(new Rectangle2D(getViewportx(this.index)[0], getViewportx(this.index)[1], getWidth(this.index), 100));

            this.hitBox = new Rectangle2D(this.imageView.getX(), this.imageView.getY()-5, 70, 75);


            System.out.println("numberOfLives: "+this.numberOfLives);

            lastUpdate = time;
        }
    }


    public void jump(){
        if(this.y>=250){
            this.attitude=Attitude.JUMPING_UP;
            System.out.println("Jump");
            }

    }

    public void accelerate(){
        this.vx++;
    }


    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVx() {
        return vx;
    }

    /**
     * Check if a collision with a foe occurred
     * @param foe is the foe hitBox
     */
    public void checkCollision(Rectangle2D foe){

            if (this.hitBox.intersects(foe)) {
                this.invincibility = 2500000000D;
                this.numberOfLives--;
                System.out.println("Check");
                System.out.println("Invincibility: "+this.invincibility);
                System.out.println("Number of life: "+this.numberOfLives);
            } else {
                this.invincibility = -1;
                System.out.println("No collision");
            }

    }

    /**
     * Detect if the hero is invincible or not!
     * @return 1 if hero is invincible and 0 if not
     */
    public boolean isInvincible(){
        return this.invincibility>0;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }
}
