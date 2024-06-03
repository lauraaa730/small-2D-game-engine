package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

public class InteractableObject implements GameObject {
    private int xCoord;
    private int yCoord;
    private HitBox hitBox = new HitBox();

    private int width;
    private int height;

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord*tileDimension, yCoord*tileDimension, this.width, this.height, xOffset, yOffset);
    }

    @Override
    public int getxCoord() {
        return xCoord;
    }

    @Override
    public int getyCoord() {
        return yCoord;
    }

    @Override
    public void setxCoord(int x) {
        this.xCoord = x;
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
    public void setyCoord(int y) {
        this.yCoord = y;
    }

    public void interactAction(){}
}
