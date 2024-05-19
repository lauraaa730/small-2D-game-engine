package cz.cvut.fel.pjv.dudkolau;

import cz.cvut.fel.pjv.dudkolau.Model.Directions;
import cz.cvut.fel.pjv.dudkolau.Model.Game;
import cz.cvut.fel.pjv.dudkolau.Model.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public void start(Stage stage) throws Exception {

        game.setTileDimension(constants.tileDimension);
        Canvas canvas = new Canvas(game.getWidth(), game.getHeight());
        Pane pane = new StackPane(canvas);
        Scene scene = new Scene(pane, game.getWidth(), game.getHeight());

        stage.setTitle("Beyond the Forest");
        stage.setResizable(true);

        player.setXCoord(5);
        player.setYCoord(5);

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
                if ((l - lastCall) >= 50_000_000) {
                    game.update();
                    setCurrPlayerImages();
                    render(canvas);
                    animate();
                    lastCall = l;
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
    }
    private void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);
        gc.drawImage(currPlayerImages[graphics.imageIndex], player.getXCoord() * game.getTileDimension(),
                player.getYCoord() * game.getTileDimension());
        //for (int i = 0; i<game.getGameObjects().length; i++) {
        gc.drawImage(bushImage, game.getGameObjects().get(0).getXCoord()*game.getTileDimension(), game.getGameObjects().get(0).getYCoord()*game.getTileDimension());
    }

    public void animate() {
        graphics.imageIndex = (graphics.imageIndex + 1) % currPlayerImages.length;
    }

    public static void main(String[] args) { launch(args); }
}