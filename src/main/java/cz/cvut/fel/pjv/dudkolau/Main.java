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

public class Main extends Application {
    private Constants constants = new Constants();
    private Graphics graphics = new Graphics();
    //private Image playerImage =  new Image("playerImage.png");

    private Image backgroundImage = new Image("background.png");
    private Image bushImage = new Image ("bush.png");
    private Player player = new Player();
    private Game game = new Game(player, constants.height,constants.width);

    private Image[] currPlayerImages = graphics.animSTAND_RIGHT;
    private  Directions lastDirection = Directions.NONE;

    public int animationCounter = 0;

    private boolean showHitBoxes = true;
    @Override
    public void start(Stage stage) throws Exception {

        game.setTileDimension(constants.tileDimension);
        Canvas canvas = new Canvas(game.getWidth(), game.getHeight());
        Pane pane = new StackPane(canvas);
        Scene scene = new Scene(pane, game.getWidth(), game.getHeight());

        stage.setTitle("Beyond the Forest");
        stage.setResizable(true);

        player.setXCoord(0);
        player.setYCoord(0);
        player.setWidth(constants.playerWidth);
        player.setHeight(constants.playerHeight);

        if (showHitBoxes) {
            //https://stackoverflow.com/questions/40729967/drawing-shapes-on-javafx-canvas
            player.setHitBox();

        }

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
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
                    currPlayerImages = graphics.animSTAND_RIGHT;
                } else {
                    currPlayerImages = graphics.animRIGHT;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastCall;
            @Override
            public void handle(long l) {
                if ((l - lastCall) >= 30_000_000) {
                    game.update();
                    setCurrPlayerImages();
                    render(canvas);
                    if (animationCounter%3==0) {
                        animate();
                    }
                    lastCall = l;
                    animationCounter++;
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
        if (player.getCurrDirection()==Directions.NONE) {
            currPlayerImages = graphics.animSTAND_RIGHT;
            if (lastDirection!=Directions.NONE) {
                graphics.imageIndex = 0;
            }
            lastDirection = Directions.NONE;

        } else if (player.getCurrDirection()==Directions.RIGHT) {
            currPlayerImages = graphics.animRIGHT;
            if (lastDirection!=Directions.RIGHT) {
                graphics.imageIndex = 0;
            }
            lastDirection = Directions.RIGHT;
        }
        //add these for all directions - rn causing problem when going left - index out of bound
    }
    private void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);
        gc.drawImage(currPlayerImages[graphics.imageIndex], player.getXCoord() * game.getTileDimension(),
                player.getYCoord() * game.getTileDimension());
        //for (int i = 0; i<game.getGameObjects().length; i++) {
        gc.drawImage(bushImage, game.getGameObjects().get(0).getXCoord()*game.getTileDimension(), game.getGameObjects().get(0).getYCoord()*game.getTileDimension());

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