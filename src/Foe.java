import javafx.geometry.Rectangle2D;

public class Foe extends AnimatedThing{

    Foe(double x, double y, String fileName){
        super(x,y,fileName);
    }

    public void update(long time){
        long btwUpdate=time-lastUpdate; // Define the time between 2 updates
        if(btwUpdate>this.animationDuration) {
            this.x -= 15; //To move the foe
            // Change the position of the position of the animatesThing
            this.imageView.setX(x);
            this.hitBox = new Rectangle2D(this.imageView.getX()+5, this.imageView.getY(), 48, 89);
            lastUpdate=time;
        }
    }
}
