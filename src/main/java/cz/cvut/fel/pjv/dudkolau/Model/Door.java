package cz.cvut.fel.pjv.dudkolau.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Door extends InteractableObject implements GameObject {
    private int level1;
    private int level2;
    private int xCoord;
    private int yCoord;
    private String imageName;
    private int width;
    private int height;
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

    public int getOutLevel(int enterLevel) {
        if (enterLevel == level1) {
            return level2;
        } else if (enterLevel==level2) {
            return level1;
        } else {
            return -1; //Doors are in the wrong place!!
        }
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

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
}
