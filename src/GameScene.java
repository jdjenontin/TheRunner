import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Class: GameScene
 * Role: Manage the game scene */


public class GameScene extends Scene {

    public enum GameState {START, PLAYING, PAUSE, GAMEOVER, RESTART};

    private GameState gameState;

    private Camera camera;

    private Pane pane;

    //Background
    private staticThing left;
    private staticThing right;
    private staticThing bg0;

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
    private  ImageView start ;

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

        //BG0
        this.bg0 = new staticThing(-700,0, "img/desert.png");

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
        start = new ImageView("Img/TheRunner.png");
        start.setY(31);
        start.setX(0);
        start.setViewport(new Rectangle2D(0,0,600,338));


        //Adding nodes
        pane.getChildren().addAll(bg0.getImageView(),left.getImageView(), right.getImageView(), vies.getImageView(), hero.getImageView(), foe1.getImageView(),
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
                        pane.getChildren().remove(start);
                        break;
                    }

                    case P ->{
                        gameState=GameState.PAUSE;
                        break;
                    }
                    case R->{
                        //gameState=GameState.RESTART;
                        //hero = new Hero(0, 250, "img/heros.png");
                        if(gameState==GameState.GAMEOVER) {
                            pane.getChildren().remove(game0.getImageView());
                            /*game0 = new staticThing(0,100, "img/gameover.gif");
                            game0.getImageView().setViewport(new Rectangle2D(0,0,600,180));*/
                            hero.setNumberOfLives(3);
                            hero.setX(0);
                            hero.setY(250);
                            foe1.setX(600);
                            foe2.setX(foe1.getX()+300*Math.random()+300);
                            foe3.setX(foe2.getX()+300*Math.random()+300);
                            valScore=0;
                            rep=1;
                            score.setText("Score :"+valScore);
                            pane.getChildren().addAll(start, vies.getImageView());
                            gameState = GameState.START;
                        }
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
            hero.setVx(hero.getVx()+1);
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
        bg0.getImageView().setX(bgL*(rep-2) -camera.getX());

        //Vies update
        if(hero.getNumberOfLives()>0){
            vies.getImageView().setViewport(new Rectangle2D(0,0,hero.getNumberOfLives()*20,19 ));
        } else if(hero.getNumberOfLives()==0){
            pane.getChildren().remove(vies.getImageView());
        }


        // Ys management
        if(camera.getY()<0) {
            System.out.println("CAM: "+camera.getY());
            left.getImageView().setY(-camera.getY());
            right.getImageView().setY(-camera.getY());
            bg0.getImageView().setY(-camera.getY());
        }else{
            System.out.println("CAM: "+camera.getY());
            left.getImageView().setY(0);
            right.getImageView().setY(0);
            bg0.getImageView().setY(0);
        }



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
