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
    private Constants constants = new Constants();
    private Graphics graphics = new Graphics();
    //private Image playerImage =  new Image("playerImage.png");

    private Image backgroundImage = new Image("background.png");
    private Image bushImage = new Image ("bush.png");
    private Image pausedImage = new Image("paused.png");
    private Player player = new Player();
    private Game game = new Game(player, constants.height,constants.width);

    private Image[] currPlayerImages = graphics.animSTAND_RIGHT;
    private  Directions lastDirection = Directions.NONE;

    public int animationCounter = 0;

    private boolean showHitBoxes = false;
    private boolean isPaused = false;
    @Override
    public void start(Stage stage) throws Exception {

        game.setTileDimension(constants.tileDimension);
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
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage(pausedImage, 300,200);
                    }

                    lastCall = l;
                    if (animationCounter>= 2147483640) { //so the counter doesnt overflow
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
            /*if (lastDirection!=Directions.RIGHT) {
                graphics.imageIndex = 0;
            }*/
            lastDirection = Directions.RIGHT;
        } else if (player.getCurrDirection()==Directions.UP) {
            currPlayerImages = graphics.animSTAND_UP;
            /*if (lastDirection!=Directions.UP) {
                graphics.imageIndex = 0;
            }*/
            lastDirection = Directions.UP;
        } else if (player.getCurrDirection()==Directions.DOWN) {
            currPlayerImages = graphics.animSTAND_DOWN;
            /*if (lastDirection!=Directions.DOWN) {
                graphics.imageIndex = 0;
            }*/
            lastDirection = Directions.DOWN;
        } else if (player.getCurrDirection()==Directions.LEFT) {
            currPlayerImages = graphics.animLEFT;
            lastDirection = Directions.LEFT;
        }
    }


    private void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);

        //concept: player shows behind or infront of bushes
        if (player.getyCoord()>game.getGameObjects().get(0).getYCoord()+10) {
            gc.drawImage(bushImage, game.getGameObjects().get(0).getXCoord()*game.getTileDimension(),
                    game.getGameObjects().get(0).getYCoord()*game.getTileDimension());
            gc.drawImage(currPlayerImages[graphics.imageIndex], player.getxCoord() * game.getTileDimension(),
                    player.getyCoord() * game.getTileDimension());
        } else {
            gc.drawImage(currPlayerImages[graphics.imageIndex], player.getxCoord() * game.getTileDimension(),
                    player.getyCoord() * game.getTileDimension());
            //for (int i = 0; i<game.getGameObjects().length; i++) {
            gc.drawImage(bushImage, game.getGameObjects().get(0).getXCoord()*game.getTileDimension(),
                    game.getGameObjects().get(0).getYCoord()*game.getTileDimension());
        }


        if (showHitBoxes) {
            drawRectangle(gc, player.getHitBox().getRectangle());
            //drawRectangle(gc, game.getGameObjects().getFirst().getHitBox().getRectangle());
            for (GameObject gameObject : game.getGameObjects()) {
                drawRectangle(gc, gameObject.getHitBox().getRectangle());
            }
        }
    }

    public void animate() {
        graphics.imageIndex = (graphics.imageIndex + 1) % currPlayerImages.length;
    }

    private void drawRectangle(GraphicsContext gc,Rectangle rect){
        gc.setStroke(Color.RED);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2);
        gc.strokeRect(rect.getX(),
                rect.getY(),
                rect.getWidth(),
                rect.getHeight());
    }

    public static void main(String[] args) { launch(args); }
}