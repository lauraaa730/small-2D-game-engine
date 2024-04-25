package cz.cvut.fel.pjv.dudkolau.Model;

public class Player implements Entity {
    private int health;
    private int xCoord;
    private int yCoord;
    private int piecesCollected[];

    private Directions currDirection = Directions.NONE;

    @Override
    public void move(Directions d, int w, int h){
        currDirection = d;

        if (currDirection==Directions.LEFT && xCoord*20>0) {
            xCoord--;
        } else if (currDirection==Directions.RIGHT && xCoord*20<w-100) {
            xCoord++;
        } else if (currDirection==Directions.UP && yCoord*20>0) {
            yCoord--;
        } else if (currDirection==Directions.DOWN && yCoord*20<h-100) {
            yCoord++;
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
