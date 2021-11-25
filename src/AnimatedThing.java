import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Mold for the hero and his oponent
 */
public abstract class AnimatedThing {
    protected double x;
    protected double y;

    protected ImageView imageView;

    protected Attitude attitude;

    protected int index;

    protected double animationDuration;
    protected long lastUpdate;

    protected Rectangle2D hitBox;

    AnimatedThing(double x, double y, String fileName){
        this.x=x;
        this.y=y;
        this.index=1;
        this.imageView = new ImageView(fileName);
        this.imageView.setX(x);
        this.imageView.setY(y);
        this.animationDuration = 1e8/2;


    }



    /***
     * Return the coord position of the heros on the spriteSheet
     * @param index is the position of the hero
     */
    public int[] getViewportx(int index) {
        int coord [] = new int[2];
        switch (index) {
            case 1:
                coord[0] = 95;
                coord[1] = 2;
                break;
            case 2:
                coord[0] = 170;
                coord[1] = 2;
                break;
            case 3:
                coord[0] = 270;
                coord[1] = 2;
                break;
            case 4:
                coord[0] = 345;
                coord[1] = 2;
                break;
            case 5:
                coord[0] = 425;
                coord[1] = 2;
                break;
            case 6:
                coord[0] = 20;
                coord[1] = 160;
                break;
            case 7:
                coord[0] = 95;
                coord[1] = 160;
                break;
            default:
                coord[0] = 20;
                coord[1] = 2;
                break;
        }
        return coord;
    }

    /**
     * Return the width of the hero
     *
     * @param index the index of the hero on the spritesheet
     */
    public int getWidth(int index) {
        int width = 75;
        if (index == 2 || index == 5 || index ==7)
            width = 80;
        return width;
    }

    /// Getters


    public ImageView getImageView() {
        return imageView;
    }
   //Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Rectangle2D getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle2D hitBox) {
        this.hitBox = hitBox;
    }

    //Setters


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }





}
