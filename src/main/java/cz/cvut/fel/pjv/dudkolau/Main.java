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

public class Main extends Application {
    private Image playerImage =  new Image("playerImage.jpg");
    private Image backgroundImage = new Image("background.jpg");
    private Player player = new Player();
    private Game game = new Game(player, 600,800);
    @Override
    public void start(Stage stage) throws Exception {
        game.setTileDimension(20);
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
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastCall;
            @Override
            public void handle(long l) {
                if ((l - lastCall) >= 50_000_000) {
                    game.update();
                    render(canvas);
                    lastCall = l;
                }
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();


    }

    private void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);
        gc.drawImage(playerImage, player.getXCoord() * game.getTileDimension(),
                player.getYCoord() * game.getTileDimension());
    }

    public static void main(String[] args) { launch(args); }
}