package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

public class Player implements Entity {
    private int health;
    private int xCoord;
    private int yCoord;
    private int height;
    private int width;
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

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public void setHitBox() {
        //int hitBoxCoords[] =  {xCoord*tileDimension-width/2, xCoord*tileDimension+width/2, yCoord*tileDimension-height/2, yCoord*tileDimension+height/2};
        //System.out.printf("hitBoxCoords: %d %d %d %d\n", hitBoxCoords[0], hitBoxCoords[1], hitBoxCoords[2], hitBoxCoords[3]);
        this.hitBox.setRectangle(xCoord*tileDimension, yCoord*tileDimension, this.width, this.height);
    }

    private int piecesCollected[];

    private Directions currDirection = Directions.NONE;

    @Override
    public void move(Directions d, int w, int h, int tileDimension){
        currDirection = d;

        if (currDirection==Directions.LEFT && xCoord*tileDimension>0) {
            xCoord--;
            this.hitBox.changexCoord(xCoord);
        } else if (currDirection==Directions.RIGHT && xCoord*tileDimension<w-100) {
            xCoord++;
            this.hitBox.changexCoord(xCoord);
        } else if (currDirection==Directions.UP && yCoord*tileDimension>0) {
            yCoord--;
            this.hitBox.changeYCoord(yCoord);
        } else if (currDirection==Directions.DOWN && yCoord*tileDimension<h-100) {
            yCoord++;
            this.hitBox.changeYCoord(yCoord);
        }
    }

    @Override
    public void attack(Directions d){}

    public int interact() { return 0;}

    @Override
    public int getHealth(){ return health; }

    @Override
    public int getXCoord() { return xCoord; }

    @Override
    public int getYCoord() { return yCoord; }

    public Directions getCurrDirection() {
        return currDirection;
    }

    @Override
    public void setCurrDirection(Directions d) {
        currDirection = d;
    }

    @Override
    public void setXCoord(int x) {
        xCoord = x;
    }

    @Override
    public void setYCoord(int y) {
        yCoord = y;
    }
}
