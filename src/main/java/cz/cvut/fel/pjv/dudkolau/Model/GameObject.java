package cz.cvut.fel.pjv.dudkolau.Model;

public interface GameObject {
    public int getXCoord();
    public int getYCoord(); //

    public void setXCoord(int x);
    public void setYCoord(int y);

    public HitBox getHitBox();
    public void setHitBox();
}
