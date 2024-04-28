package cz.cvut.fel.pjv.dudkolau;


import cz.cvut.fel.pjv.dudkolau.Model.GameObject;
import cz.cvut.fel.pjv.dudkolau.Model.Level;
import cz.cvut.fel.pjv.dudkolau.Model.Player;
import javafx.scene.image.Image;

public class Graphics {
    public Image[] playerImages = {
            new Image("animRIGHT-1.png"),
            new Image("animRIGHT-2.png"),
            new Image("animRIGHT-3.png"),
            new Image("animRIGHT-4.png"),
            new Image("animRIGHT-5.png"),
            new Image("animRIGHT-6.png"),
            new Image("animRIGHT-7.png"),
            new Image("animRIGHT-8.png")
    };

    public int imageIndex = 0;
    public int renderLevel(Level level) { return 0; }

    public int renderObject(GameObject gameObject) { return 0;}

    public int renderPlayer(Player player) { return 0;}

    public void animate() {
        imageIndex = (imageIndex + 1) % playerImages.length;
    }

}
