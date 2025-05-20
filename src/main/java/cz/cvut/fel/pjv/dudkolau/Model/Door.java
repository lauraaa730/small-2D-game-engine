package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Door is an interactbale object, which holds the
 * information needed for level transfer.
 * Interaction allows player to enter different level.
 *
 * @SuppressWarnings("unused") for
 * getters and setter marked as
 * "unused", because they are
 * needed for json de/serialization.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
public class Door implements GameObject {
    private int level1;
    private int level2;
    private int xCoord;
    private int yCoord;
    private String imageName;
    private String lockedImageName;
    private int width;
    private int height;
    private boolean locked;
    private Directions dir;

    @JsonIgnore
    private HitBox hitBox = new HitBox();

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }

    public int getLevel1() {
        return level1;
    }

    @SuppressWarnings("unused")
    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel2() {
        return level2;
    }

    @SuppressWarnings("unused")
    public void setLevel2(int level2) { this.level2 = level2; }

    @Override
    public int getxCoord() {
        return xCoord;
    }

    @Override
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    @Override
    public int getyCoord() {
        return yCoord;
    }

    @Override
    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    @Override
    public String getImageName() {
        return imageName;
    }

    @Override
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getLockedImageName() {
        return lockedImageName;
    }

    @SuppressWarnings("unused")
    public void setLockedImageName(String lockedImageName) {
        this.lockedImageName = lockedImageName;
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Directions getDir() {
        return dir;
    }

    @SuppressWarnings("unused")
    public void setDir(Directions dir) {
        this.dir = dir;
    }
}
