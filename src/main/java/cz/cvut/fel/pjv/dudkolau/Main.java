package cz.cvut.fel.pjv.dudkolau;

import cz.cvut.fel.pjv.dudkolau.Model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;

public class Main extends Application {
    private Graphics graphics = new Graphics();
    //private Image playerImage =  new Image("playerImage.png");

    private Image pausedImage = new Image("paused.png");
    private Game game = new Game();

    private Image[] currPlayerImages = graphics.animSTAND_RIGHT;
    private  Directions lastDirection = Directions.NONE;

    public int animationCounter = 0;

    public boolean mainMenuButtonsAdded = false;

    private boolean showHitBoxes = false;
    private Image backgroundImage = new Image("background.png");
    @Override
    public void start(Stage stage) throws Exception {


        game.setTileDimension(tileDimension);
        Canvas canvas = new Canvas(game.getWidth(), game.getHeight());
        AnchorPane pane = new AnchorPane(canvas);
        //using anchorpane instead of stackpane allows moving buttons more freely
        Scene scene = new Scene(pane, game.getWidth(), game.getHeight());


        Button continueButton = new Button("Continue");
        continueButton.setLayoutX(600);
        continueButton.setLayoutY(200);
        continueButton.setFocusTraversable(false);

        Button newGameButton = new Button("Start New Game");
        newGameButton.setGraphic(new ImageView(new Image("bushHorizontal.png")));
        newGameButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        newGameButton.setLayoutX(100);
        newGameButton.setLayoutY(200);
        newGameButton.setFocusTraversable(false);

        game.setPaused(true);

        stage.setTitle("Beyond the Forest");
        stage.setResizable(true);
        /*    File musicPath = new File("music.wav");
        //maybe add whether program can find this file
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        game.setPaused(!game.getIsPaused());
                    }
                    if (keyEvent.getCode() == KeyCode.LEFT) {
                        game.getPlayer().setCurrDirection(Directions.LEFT);
                    } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                        game.getPlayer().setCurrDirection(Directions.RIGHT);
                    } else if (keyEvent.getCode() == KeyCode.DOWN) {
                        game.getPlayer().setCurrDirection(Directions.DOWN);
                    } else if (keyEvent.getCode() == KeyCode.UP) {
                        game.getPlayer().setCurrDirection(Directions.UP);
                    }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT ||
                        keyEvent.getCode() == KeyCode.RIGHT ||keyEvent.getCode() == KeyCode.UP ||
                        keyEvent.getCode() == KeyCode.DOWN) {
                    game.getPlayer().setCurrDirection(Directions.NONE);
                }
            }
        });

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                game.setPaused(!game.getIsPaused());
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastCall;
            @Override
            public void handle(long l) {
                if ((l - lastCall) >= 25_000_000) {
                    if (!game.getIsPaused()) {
                        game.update();
                        setCurrPlayerImages();
                        if (mainMenuButtonsAdded) {
                            pane.getChildren().removeAll(newGameButton, continueButton);
                            mainMenuButtonsAdded = false;
                            System.out.println("Deleted button");
                        }
                        render(canvas);
                        if (animationCounter%3==0) {
                            animate();
                        }
                        animationCounter++;
                    } else {
                        if (!mainMenuButtonsAdded) {
                            pane.getChildren().addAll(newGameButton, continueButton);
                            mainMenuButtonsAdded = true;
                            System.out.println("Added button");
                        }
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
        if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.RIGHT) {
            currPlayerImages = graphics.animSTAND_RIGHT;
            lastDirection = Directions.NONE;

        } else if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.LEFT) {
            currPlayerImages = graphics.animSTAND_LEFT;
            lastDirection = Directions.NONE;

        }  else if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.UP) {
            currPlayerImages = graphics.animSTAND_UP;
            lastDirection = Directions.NONE;

        }  else if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.DOWN) {
            currPlayerImages = graphics.animSTAND_DOWN;
            lastDirection = Directions.NONE;

        } else if (game.getPlayer().getCurrDirection()==Directions.RIGHT) {
            currPlayerImages = graphics.animRIGHT;
            lastDirection = Directions.RIGHT;

        } else if (game.getPlayer().getCurrDirection()==Directions.UP) {
            currPlayerImages = graphics.animSTAND_UP;
            lastDirection = Directions.UP;

        } else if (game.getPlayer().getCurrDirection()==Directions.DOWN) {
            currPlayerImages = graphics.animSTAND_DOWN;
            lastDirection = Directions.DOWN;

        } else if (game.getPlayer().getCurrDirection()==Directions.LEFT) {
            currPlayerImages = graphics.animLEFT;
            lastDirection = Directions.LEFT;
        }
    }


    private void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);
        boolean drewPlayer = false;
        int playerY = game.getPlayer().getHitBox().getyCoord();
        int objectY;
        int objectX;
        //concept: player shows behind or infront of bushes
        for (int i = 0; i < game.getGameObjects().size(); i++) {

            objectY = game.getGameObjects().get(i).getyCoord();
            objectX = game.getGameObjects().get(i).getxCoord();
            if (playerY-tileDimension>objectY && playerY< objectY+game.getGameObjects().get(i).getHeight()/tileDimension) {
                //System.out.println("Hrac pred kerem, kre cislo: " + i);
                gc.drawImage(new Image(game.getGameObjects().get(i).getImageName()),objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());

                gc.drawImage(currPlayerImages[graphics.imageIndex], game.getPlayer().getxCoord() * game.getTileDimension(),
                        game.getPlayer().getyCoord() * game.getTileDimension());
                drewPlayer = true;

            } else {
                if (!drewPlayer) {
                    gc.drawImage(currPlayerImages[graphics.imageIndex], game.getPlayer().getxCoord() * game.getTileDimension(),
                            game.getPlayer().getyCoord() * game.getTileDimension());
                    drewPlayer=true;
                }
                //for (int i = 0; i<game.getGameObjects().length; i++) {
                gc.drawImage(new Image(game.getGameObjects().get(i).getImageName()), objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());
            }
            if (!drewPlayer) {
                gc.drawImage(currPlayerImages[graphics.imageIndex], game.getPlayer().getxCoord() * game.getTileDimension(),
                        game.getPlayer().getyCoord() * game.getTileDimension());
            }
        }

        for (Enemy enemy : game.getCurrLevel().getEnemies()) {
            gc.drawImage(new Image(enemy.getImageName()), enemy.getxCoord()*tileDimension, enemy.getyCoord()*tileDimension);
        }



        if (showHitBoxes) {
            drawRectangle(gc, game.getPlayer().getHitBox().getxCoord(), game.getPlayer().getHitBox().getyCoord(), game.getPlayer().getHitBox().getWidth(), game.getPlayer().getHitBox().getHeight());
            //drawRectangle(gc, game.getGameObjects().getFirst().getHitBox().getRectangle());
            for (GameObject gameObject : game.getGameObjects()) {
                drawRectangle(gc, gameObject.getHitBox().getxCoord(), gameObject.getHitBox().getyCoord(), gameObject.getHitBox().getWidth(), gameObject.getHitBox().getHeight());
            }
            for (Enemy enemy : game.getCurrLevel().getEnemies()) {
                drawRectangle(gc, enemy.getHitBox().getxCoord(), enemy.getHitBox().getyCoord(), enemy.getHitBox().getWidth(), enemy.getHitBox().getHeight());
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