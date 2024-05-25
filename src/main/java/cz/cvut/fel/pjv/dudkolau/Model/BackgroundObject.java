package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

/*
 * This will represent all objects in the game,
 * with which you cannot interact,
 * meaning that they are just background objects.
 * Bushes, grass, trees..
 */
public class BackgroundObject implements GameObject{
    private int xCoord;
    private int yCoord;
    private HitBox hitBox = new HitBox();

    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public int getXCoord() {
        return xCoord;
    }

    @Override
    public int getYCoord() {
        return yCoord;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord*tileDimension, yCoord*tileDimension, this.width, this.height, xOffset, yOffset);
    }

    @Override
    public void setXCoord(int x) {
        this.xCoord = x;
    }

    @Override
    public void setYCoord(int y) {
        this.yCoord = y;
    }
}
