package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

public class Enemy extends InteractableObject implements Entity{
    private int health;
    private int xCoord;
    private int yCoord;
    private int height;
    private int width;
    private HitBox hitBox = new HitBox();
    private int selfMovementPosition = 0;
    private Directions currDirection = Directions.LEFT;

    //for JSON
    public Enemy() {
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord*tileDimension, yCoord*tileDimension, this.width, this.height, xOffset, yOffset);
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
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
    public int getWidth() {
        return width;
    }

    @Override
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
        if (currDirection == Directions.LEFT) {
            selfMovementPosition--;
        } else if (currDirection==Directions.RIGHT) {
            selfMovementPosition++;
        }
    }


    @Override
    public void move(Directions d, int w, int h, int tileDimension){
        if (d==Directions.LEFT && this.hitBox.getxCoord()*tileDimension>0) {
            xCoord--;
            this.hitBox.changexCoord(-1);
        } else if (d==Directions.RIGHT && this.hitBox.getxCoord()*tileDimension<w-100) {
            xCoord++;
            this.hitBox.changexCoord(1);
        } else if (d==Directions.UP && this.hitBox.getyCoord()*tileDimension>0) {
            yCoord--;
            this.hitBox.changeYCoord(-1);
        } else if (d==Directions.DOWN && this.hitBox.getyCoord()*tileDimension<h-100) {
            yCoord++;
            this.hitBox.changeYCoord(1);
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
        return null;
    }

    @Override
    public void setCurrDirection(Directions d) {
    }

    @Override
    public void setxCoord(int x) {

    }

    @Override
    public void setyCoord(int y) {

    }
}

