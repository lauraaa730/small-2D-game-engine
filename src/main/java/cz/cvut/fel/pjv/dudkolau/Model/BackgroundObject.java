package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String imageName;
    private int width;
    private int height;
    @JsonIgnore
    private HitBox hitBox = new HitBox();

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

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }


}
