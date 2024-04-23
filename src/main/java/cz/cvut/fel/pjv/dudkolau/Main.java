package cz.cvut.fel.pjv.dudkolau;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(400, 300);
        Pane pane = new StackPane(canvas);
        Scene scene = new Scene(pane, 400, 300);

        stage.setTitle("Beyond the Forest");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) { launch(args);
    }
}