package cz.cvut.fel.pjv.dudkolau.Model;

public class Enemy extends InteractableObject implements Entity{
    private int health;
    private int xCoord;
    private int yCoord;

    @Override
    public void move(Directions d){}

    @Override
    public void attack(Directions d){}

    @Override
    public int getHealth(){ return health; }

    @Override
    public int getXCoord() { return xCoord; }

    @Override
    public int getYCoord() { return yCoord; }

    public boolean isNextToOject(Directions d){return false;}
}

