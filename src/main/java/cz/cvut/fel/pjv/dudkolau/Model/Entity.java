package cz.cvut.fel.pjv.dudkolau.Model;

public interface Entity {
    public int getHealth();
    public int getYCoord();
    public int getXCoord();
    public void move(Directions d);
    public void attack(Directions d);
    public boolean isNextToOject(Directions d);
}
