package cz.cvut.fel.pjv.dudkolau.Model;

public class Enemy extends InteractableObject implements Entity{
    private int health;
    private int xCoord;
    private int yCoord;

    private int selfMovementPosition = 0; //0 in the middle, and then to -or + enemymovementlength
    private Directions currDirection = Directions.LEFT;

    public void moveItself() {
    }

    @Override
    public void move(Directions d, int w, int h){
    }


    @Override
    public void attack(Directions d){}

    @Override
    public int getHealth(){ return health; }

    @Override
    public int getXCoord() { return xCoord; }

    @Override
    public int getYCoord() { return yCoord; }

    @Override
    public Directions getCurrDirection() {
        return null;
    }

    @Override
    public void setCurrDirection(Directions d) {
    }

    @Override
    public void setXCoord(int x) {

    }

    @Override
    public void setYCoord(int y) {

    }
}

