package cz.cvut.fel.pjv.dudkolau.Model;

public interface Entity {
    public int getHealth();
    public int getXCoord();
    public int getYCoord();
    public Directions getCurrDirection();
    public void setCurrDirection(Directions d);
    public void move(Directions d, int w, int h, int tileDimension);
    public void attack(Directions d);

    public void setXCoord(int x);
    public void setYCoord(int y);

}
