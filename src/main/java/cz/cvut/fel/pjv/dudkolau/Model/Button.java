package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Button extends InteractableObject implements GameObject {

    private int xCoord;
    private int yCoord;
    private String imageName;
    private int width;
    private int height;
    @JsonIgnore
    private HitBox hitBox = new HitBox();

    @Override
    public String getImageName() {
        return super.getImageName();
    }

    @Override
    public void setImageName(String imageName) {
        super.setImageName(imageName);
    }

    @Override
    public HitBox getHitBox() {
        return super.getHitBox();
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        super.setHitBox(xOffset, yOffset);
    }

    @Override
    public int getxCoord() {
        return super.getxCoord();
    }

    @Override
    public int getyCoord() {
        return super.getyCoord();
    }

    @Override
    public void setxCoord(int x) {
        super.setxCoord(x);
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    public void setyCoord(int y) {
        super.setyCoord(y);
    }

    @Override
    public void interactAction() {
        super.interactAction();
    }
}
