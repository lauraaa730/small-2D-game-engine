package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractEntity implements Entity {

    public int maxHealth;
    public int currHealth;
    public int xCoord;
    public int yCoord;
    public int height;
    public int width;
    public String imageName;
    @JsonIgnore
    public HitBox hitBox = new HitBox();
    public Directions currDirection;

    @Override
    public void move(Directions d, int w, int h, int tileDimension){
        if (d==Directions.LEFT && this.hitBox.getxCoord()*tileDimension>0) {
            xCoord--;
            this.hitBox.changexCoord(-1);
        } else if (d==Directions.RIGHT && this.hitBox.getxCoord()*tileDimension<w- hitBox.getWidth()) {
            xCoord++;
            this.hitBox.changexCoord(1);
        } else if (d==Directions.UP && this.hitBox.getyCoord()*tileDimension>0) {
            yCoord--;
            this.hitBox.changeYCoord(-1);
        } else if (d==Directions.DOWN && this.hitBox.getyCoord()*tileDimension<h- hitBox.getHeight()) {
            yCoord++;
            this.hitBox.changeYCoord(1);
        }
    }

    @SuppressWarnings("unused")
    public String getImageName() {
        return imageName;
    }

    @SuppressWarnings("unused")
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public int getCurrHealth() {
        return currHealth;
    }

    @Override
    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
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
    public int getxCoord() { return xCoord; }

    @Override
    public int getyCoord() { return yCoord; }

    @Override
    public Directions getCurrDirection() {
        return currDirection;
    }

    @Override
    public void setCurrDirection(Directions d) {
        currDirection = d;
    }

    @Override
    public void setxCoord(int x) {
        xCoord=x;
    }

    @Override
    public void setyCoord(int y) {
        yCoord=y;
    }
}