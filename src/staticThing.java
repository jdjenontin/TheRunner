import javafx.scene.image.ImageView;

public class staticThing {
    private double x;
    private double y;
    private ImageView imageView;

    staticThing(double x, double y, String fileName){
        this.x=x;
        this.y=y;
        this.imageView= new ImageView(fileName);
        this.imageView.setX(x);
        this.imageView.setY(y);
    }

    public ImageView getImageView() {
        return imageView;
    }


}
