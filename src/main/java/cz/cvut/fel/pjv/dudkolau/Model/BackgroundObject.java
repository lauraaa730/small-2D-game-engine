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
    private String imageName;
    private int width;
    private int height;
    private HitBox hitBox = new HitBox();

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }



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
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord*tileDimension, yCoord*tileDimension, this.width, this.height, xOffset, yOffset);
    }


}
