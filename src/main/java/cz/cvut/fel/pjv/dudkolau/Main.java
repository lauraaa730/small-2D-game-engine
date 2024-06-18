package cz.cvut.fel.pjv.dudkolau;

import cz.cvut.fel.pjv.dudkolau.Model.Directions;
import cz.cvut.fel.pjv.dudkolau.Model.Game;
import cz.cvut.fel.pjv.dudkolau.Model.GameObject;
import cz.cvut.fel.pjv.dudkolau.Model.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;

public class Main extends Application {
    private Graphics graphics = new Graphics();
    //private Image playerImage =  new Image("playerImage.png");

    private Image bushImage = new Image ("bush.png");
    private Image pausedImage = new Image("paused.png");
    private Player player = new Player();
    private Game game = new Game(player, height, width);

    private Image[] currPlayerImages = graphics.animSTAND_RIGHT;
    private  Directions lastDirection = Directions.NONE;

    public int animationCounter = 0;

    private boolean showHitBoxes = false;
    private boolean isPaused = false;
    private Image backgroundImage = new Image("background.png");
    @Override
    public void start(Stage stage) throws Exception {


        game.setTileDimension(tileDimension);
        Canvas canvas = new Canvas(game.getWidth(), game.getHeight());
        Pane pane = new StackPane(canvas);
        Scene scene = new Scene(pane, game.getWidth(), game.getHeight());

        stage.setTitle("Beyond the Forest");
        stage.setResizable(true);



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        isPaused = !isPaused;
                    }
                    if (keyEvent.getCode() == KeyCode.LEFT) {
                        player.setCurrDirection(Directions.LEFT);
                    } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                        player.setCurrDirection(Directions.RIGHT);
                    } else if (keyEvent.getCode() == KeyCode.DOWN) {
                        player.setCurrDirection(Directions.DOWN);
                    } else if (keyEvent.getCode() == KeyCode.UP) {
                        player.setCurrDirection(Directions.UP);
                    }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT ||
                        keyEvent.getCode() == KeyCode.RIGHT ||keyEvent.getCode() == KeyCode.UP ||
                        keyEvent.getCode() == KeyCode.DOWN) {
                    player.setCurrDirection(Directions.NONE);
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastCall;
            @Override
            public void handle(long l) {
                if ((l - lastCall) >= 25_000_000) {
                    if (!isPaused) {
                        game.update();
                        setCurrPlayerImages();
                        render(canvas);
                        if (animationCounter%3==0) {
                            animate();
                        }
                        animationCounter++;
                    } else {
                        render(canvas);
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage(graphics.backgroundPaused,0,0);
                        gc.drawImage(pausedImage, 155,110);
                    }

                    lastCall = l;
                    if (animationCounter>= 2147483600) { //so the counter doesnt overflow
                        animationCounter=0;
                    }
                }
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();


    }

    private void setCurrPlayerImages() {
        if (player.getCurrDirection()==Directions.NONE && lastDirection == Directions.RIGHT) {
            currPlayerImages = graphics.animSTAND_RIGHT;
            lastDirection = Directions.NONE;

        } else if (player.getCurrDirection()==Directions.NONE && lastDirection == Directions.LEFT) {
            currPlayerImages = graphics.animSTAND_LEFT;
            lastDirection = Directions.NONE;

        }  else if (player.getCurrDirection()==Directions.NONE && lastDirection == Directions.UP) {
            currPlayerImages = graphics.animSTAND_UP;
            lastDirection = Directions.NONE;

        }  else if (player.getCurrDirection()==Directions.NONE && lastDirection == Directions.DOWN) {
            currPlayerImages = graphics.animSTAND_DOWN;
            lastDirection = Directions.NONE;

        } else if (player.getCurrDirection()==Directions.RIGHT) {
            currPlayerImages = graphics.animRIGHT;
            lastDirection = Directions.RIGHT;

        } else if (player.getCurrDirection()==Directions.UP) {
            currPlayerImages = graphics.animSTAND_UP;
            lastDirection = Directions.UP;

        } else if (player.getCurrDirection()==Directions.DOWN) {
            currPlayerImages = graphics.animSTAND_DOWN;
            lastDirection = Directions.DOWN;

        } else if (player.getCurrDirection()==Directions.LEFT) {
            currPlayerImages = graphics.animLEFT;
            lastDirection = Directions.LEFT;
        }
    }


    private void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);
        boolean drewPlayer = false;
        int playerY = player.getHitBox().getyCoord();
        int objectY;
        int objectX;
        //concept: player shows behind or infront of bushes
        for (int i = 0; i < game.getGameObjects().size(); i++) {
            objectY = game.getGameObjects().get(i).getyCoord();
            objectX = game.getGameObjects().get(i).getxCoord();
            if (playerY-tileDimension>objectY && playerY< objectY+game.getGameObjects().get(i).getHeight()/tileDimension) {
                //System.out.println("Hrac pred kerem, kre cislo: " + i);
                gc.drawImage(bushImage,objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());

                gc.drawImage(currPlayerImages[graphics.imageIndex], player.getxCoord() * game.getTileDimension(),
                        player.getyCoord() * game.getTileDimension());
                drewPlayer = true;

            } else {
                if (!drewPlayer) {
                    gc.drawImage(currPlayerImages[graphics.imageIndex], player.getxCoord() * game.getTileDimension(),
                            player.getyCoord() * game.getTileDimension());
                    drewPlayer=true;
                }
                //for (int i = 0; i<game.getGameObjects().length; i++) {
                gc.drawImage(bushImage, objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());
            }
            if (!drewPlayer) {
                gc.drawImage(currPlayerImages[graphics.imageIndex], player.getxCoord() * game.getTileDimension(),
                        player.getyCoord() * game.getTileDimension());
            }
        }



        if (showHitBoxes) {
            drawRectangle(gc, player.getHitBox().getxCoord(), player.getHitBox().getyCoord(), player.getHitBox().getWidth(), player.getHitBox().getHeight());
            //drawRectangle(gc, game.getGameObjects().getFirst().getHitBox().getRectangle());
            for (GameObject gameObject : game.getGameObjects()) {
                drawRectangle(gc, gameObject.getHitBox().getxCoord(), gameObject.getHitBox().getyCoord(), gameObject.getHitBox().getWidth(), gameObject.getHitBox().getHeight());
            }
        }
    }

    public void animate() {
        graphics.imageIndex = (graphics.imageIndex + 1) % currPlayerImages.length;
    }

    private void drawRectangle(GraphicsContext gc, int x, int y, int w, int h){
        gc.setStroke(Color.RED);
        Rectangle rect = new Rectangle();
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2);
        gc.strokeRect(x*tileDimension, y*tileDimension, w, h);
    }

    public static void main(String[] args) { launch(args); }
}