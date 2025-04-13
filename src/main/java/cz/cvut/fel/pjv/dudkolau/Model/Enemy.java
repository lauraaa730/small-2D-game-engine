package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

public class Enemy implements Entity{
    private int health;
    private int xCoord;
    private int yCoord;
    private int height;
    private int width;
    private String imageName;

    @JsonIgnore
    private HitBox hitBox = new HitBox();
    private int selfMovementPosition = 0;

    private Directions currDirection = Directions.RIGHT;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    //for JSON
    public Enemy() {
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getSelfMovementPosition() {
        return selfMovementPosition;
    }

    public void setSelfMovementPosition(int selfMovementPosition) {
        this.selfMovementPosition = selfMovementPosition;
    }
    public void updateSelfMovementPosition() {
        if (this.currDirection == Directions.LEFT) {
            this.selfMovementPosition--;
        } else if (this.currDirection==Directions.RIGHT) {
            this.selfMovementPosition++;
        }
    }


    @Override
    public void move(Directions d, int w, int h, int tileDimension){
        //NOTE Enemies DO NOT collide with game objects, but can collidewith player
        //if the feature is turned on
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

    public void jumpBack(int w, int h) {
        if (currDirection == Directions.LEFT) {
            move(Directions.RIGHT, w, h, tileDimension);
        } else if (currDirection == Directions.RIGHT) {
            move(Directions.LEFT, w, h, tileDimension);
        } else if (currDirection == Directions.UP) {
            move(Directions.DOWN, w, h, tileDimension);
        } else if (currDirection == Directions.DOWN) {
            move(Directions.UP, w, h, tileDimension);
        }
    }

    @Override
    public void attack(Directions d){}

    @Override
    public int getHealth(){ return health; }

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

