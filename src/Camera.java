/**
 * Game Camera
 */
public class Camera {
    private int x;
    private int y;
    private double ax, ay, k, m, f,vx, vy;
   // private long lastUpdate;
   // private double animationDuration;


    /**
     * Camera constructor
     * @param x : Upper left x
     * @param y : Upper left y
     */
    Camera(int x, int y){
        this.x=x;
        this.y=y;
        this.k=10;
        this.m=10;
        this.f=12;
        this.vx=0;
        this.vy=0;


        //this.animationDuration =1e4;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return this.x +" "+ this.y;
    }

    public void update(long time, Hero hero) {
       /*ax=(k/m)*(hero.getX()-x)-(f/m)*vx;
        vx+=ax;
        x+=vx;*/
        this.x=(int) hero.x-100;
        this.y = (int) hero.y-250;
        /*ay=(k/m)*(hero.getY()-250-y)-(f/m)*vy;
        vy+=ay;
        y+=vy;*/


    }


    }

//}
