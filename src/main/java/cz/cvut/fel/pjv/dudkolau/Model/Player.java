package cz.cvut.fel.pjv.dudkolau.Model;

public class Player implements Entity {
    private int health;
    private int xCoord;
    private int yCoord;
    private int piecesCollected[];

    @Override
    public void move(Directions d){}

    @Override
    public void attack(Directions d){}

    public int interact() { return 0;}

    public boolean isNextToOject(Directions d){return false;}

    @Override
    public int getHealth(){ return health; }

    @Override
    public int getXCoord() { return xCoord; }

    @Override
    public int getYCoord() { return yCoord; }
}
