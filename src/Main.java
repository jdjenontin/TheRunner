import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Run baby, run");
        Group root = new Group();
        Pane pane = new Pane(root);

        GameScene theScene = new GameScene(pane, 600,400);
        theScene.setFill(Color.rgb(0,153,255));
        primaryStage.setScene(theScene);

        /**
         * The animations
         */

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(theScene.getGameState()== GameScene.GameState.PLAYING) {
                    theScene.update(l);
                }

            }
        };

        timer.start();


        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
