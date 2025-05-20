package cz.cvut.fel.pjv.dudkolau.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Button is an interactbale object, which the
 * player can step on to activate it.
 * Pressing a button can either unlock all doors
 * in level, or do nothing if boolean fake is true.
 *
 * @SuppressWarnings("unused") for
 * getters and setter marked as
 * "unused", because they are
 * needed for json de/serialization.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
public class GameButton implements GameObject {

    private int xCoord;
    private int yCoord;
    private String imageName;
    private int width;
    private int height;
    private boolean pressed;
    private boolean fake;
    @JsonIgnore
    private HitBox hitBox = new HitBox();

    @Override
    public String getImageName() {
        return this.imageName;
    }

    @Override
    public void setImageName(String imageName) {
           this.imageName = imageName;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }

    @Override
    public int getxCoord() {
        return this.xCoord;
    }

    @Override
    public int getyCoord() {
        return this.yCoord;
    }

    @Override
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    @Override
    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isFake() {
        return fake;
    }

    @SuppressWarnings("unused")
    public void setFake(boolean fake) { this.fake = fake; }
}
