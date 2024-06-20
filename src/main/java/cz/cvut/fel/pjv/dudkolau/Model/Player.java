package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;

public class Player implements Entity {
    private int health;
    private int xCoord;

    private int yCoord;
    private int height;
    private int width;

    //for JSON
    public Player() {
    }

    private HitBox hitBox = new HitBox();

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord*tileDimension, yCoord*tileDimension, this.width, this.height, xOffset, yOffset);
    }

    private int piecesCollected[];

    private Directions currDirection = Directions.NONE;

    @Override
    public void move(Directions d, int w, int h, int tileDimension){;

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

    @Override
    public void attack(Directions d){}

    public int interact() { return 0;}

    @Override
    public int getHealth(){ return health; }


    public Directions getCurrDirection() {
        return currDirection;
    }

    @Override
    public void setCurrDirection(Directions d) {
        currDirection = d;
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

}
