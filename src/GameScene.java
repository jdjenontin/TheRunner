import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;


/**
 * Class: GameScene
 * Role: Manage the game scene */


public class GameScene extends Scene {

    public enum GameState {START, PLAYING, PAUSE, GAMEOVER};

    private GameState gameState;

    private Camera camera;

    private Pane pane;

    //Background
    private staticThing left;
    private staticThing right;

    private staticThing vies;
    private staticThing game0;
    private  final int bgL=800;
    private final int bgH=400;
    private int rep =1;

    //Animated things
    private Hero hero;
    private double invincibility=-1;//--

    //Foes
    private Foe foe1, foe2, foe3;
    private ArrayList<Foe> foes;

    //Score
    private Text score;
    private int valScore;

    //Start text
    private  Text start ;

    GameScene(Pane pane, int width, int height){
        super(pane, width, height);

        this.pane = pane;
        this.camera = new Camera(100,0);

        //Left BG
        this.left = new staticThing(-this.camera.getX(),-this.camera.getY(), "img/desert.png");
        this.left.getImageView().setViewport(new Rectangle2D(0,0,bgL, bgH));

        //Right BG
        this.right = new staticThing(-this.camera.getX()+bgL,-this.camera.getY(), "img/desert.png");
        this.right.getImageView().setViewport(new Rectangle2D(0,0,bgL, bgH));

        //Vies
        this.vies = new staticThing(0,0,"img/vies.png");

        //Hero
        this.hero = new Hero(0, 250, "img/heros.png");

        //Foes


        foe1= new Foe(600, 268, "img/BO1.gif");
        foe1.getImageView().setViewport(new Rectangle2D(0, 0, 58, 89));
        foe1.setHitBox(new Rectangle2D(foe1.getX(), foe1.getY(), 58,89));

        foe2= new Foe(1200, 268, "img/BO1.gif");
        foe2.getImageView().setViewport(new Rectangle2D(0, 0, 58, 89));
        foe2.setHitBox(new Rectangle2D(foe2.getX(), foe2.getY(), 58,89));

        foe3= new Foe(1800, 268, "img/BO1.gif");
        foe3.getImageView().setViewport(new Rectangle2D(0, 0, 58, 89));
        foe3.setHitBox(new Rectangle2D(foe3.getX(), foe3.getY(), 58,89));

        //Game Over
        game0 = new staticThing(0,100, "img/gameover.gif");
        game0.getImageView().setViewport(new Rectangle2D(0,0,600,180));

        //Score
        this.score=new Text("Score :"+valScore);
        score.setY(50);
        score.setX(0);
        score.setFont(new Font(20));

        //Start
        start = new Text("Press S to START");
        start.setY(380);
        start.setX(200);
        start.setFont(new Font(30));

        //Adding nodes
        pane.getChildren().addAll(left.getImageView(), right.getImageView(), vies.getImageView(), hero.getImageView(), foe1.getImageView(),
                foe2.getImageView(), foe3.getImageView(), score, start);


        //Game control
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case SPACE -> {
                        hero.jump();
                        break;
                    }

                    case D -> {
                        hero.accelerate();
                        break;
                    }

                    case S ->{
                        gameState=GameState.PLAYING;
                        start.setOpacity(0);
                        break;
                    }

                    case P ->{
                        gameState=GameState.PAUSE;
                        break;
                    }
                    case R->{
                        // A gérer
                        break;
                    }


                }
            }
        });
    }

    void update(long time){



        foe1.update(time);
        foe2.update(time);
        foe3.update(time);

        hero.update(time, foe1, foe2, foe3);
       /* hero.update(time, foe2);
        hero.update(time, foe3);*/

        /// Collision cheking
        if(this.invincibility<0){
            hero.getImageView().setOpacity(1);
            if (hero.hitBox.intersects(foe1.getHitBox()) || hero.hitBox.intersects(foe2.getHitBox()) || hero.hitBox.intersects(foe3.getHitBox()) ) {
                this.invincibility = 250D;
                hero.setNumberOfLives(hero.getNumberOfLives()-1);
                System.out.println("Check");
                System.out.println("Invincibility: "+this.invincibility);
                System.out.println("Number of life GS: "+hero.getNumberOfLives());
            } else {
                this.invincibility = -1;
                System.out.println("No collision");
            }
        }
        else{
            hero.getImageView().setOpacity(0.5);
            this.invincibility--;
        }


        camera.update(time, hero);
        // Background Management
        if(camera.getX()>bgL*rep){
            rep++;
            valScore++;
            score.setText("Score: "+valScore);
        }
        // Xs management
        if(rep % 2 == 1){
            left.getImageView().setX(bgL*(rep-1) -camera.getX());
            right.getImageView().setX(bgL*rep-camera.getX());
        }
        else{
            left.getImageView().setX(bgL*rep-camera.getX());
            right.getImageView().setX(bgL*(rep-1) -camera.getX());
        }

        //Vies update
        if(hero.getNumberOfLives()>0){
            vies.getImageView().setViewport(new Rectangle2D(0,0,hero.getNumberOfLives()*20,19 ));
        }


        // Ys management
        left.getImageView().setY(-camera.getY());
        right.getImageView().setY(-camera.getY());

        // Hero ImageView
        hero.getImageView().setX(hero.getX()-camera.getX()+100);
        hero.getImageView().setY(hero.getY()-camera.getY());

        //Foes ImageView
        foe1.getImageView().setY(foe1.getY()-camera.getY());
        foe2.getImageView().setY(foe1.getY()-camera.getY());
        foe3.getImageView().setY(foe1.getY()-camera.getY());

        // Foes regeneration
        if(foe1.getX()<0){
            foe1.setX(foe3.getX()+300*Math.random()+300);
        }

        if(foe2.getX()<0){
            foe2.setX(foe1.getX()+300*Math.random()+300);
        }

        if(foe3.getX()<0){
            foe3.setX(foe2.getX()+300*Math.random()+250);
        }

        //Game Over
        if(hero.getNumberOfLives()<0){
            this.gameState = GameState.GAMEOVER;
            pane.getChildren().add(game0.getImageView());
        }

    }

    public Hero getHero() {
        return hero;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
