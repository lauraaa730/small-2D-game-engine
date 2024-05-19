package cz.cvut.fel.pjv.dudkolau.Model;

/*
 * This will represent all objects in the game,
 * with which you cannot interact,
 * meaning that they are just background objects.
 * Bushes, grass, trees..
 */
public class BackgroundObject implements GameObject{
    private int xCoord;
    private int yCoord;

    @Override
    public int getXCoord() {
        return xCoord;
    }

    @Override
    public int getYCoord() {
        return yCoord;
    }

    @Override
    public void setXCoord(int x) {
        this.xCoord = x;
    }

    @Override
    public void setYCoord(int y) {
        this.yCoord = y;
    }
}
