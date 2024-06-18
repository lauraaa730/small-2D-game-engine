package cz.cvut.fel.pjv.dudkolau.Model;

public interface GameObject {
    public int getxCoord();
    public int getyCoord(); //

    public void setxCoord(int x);
    public void setyCoord(int y);

    public HitBox getHitBox();
    public void setHitBox(int xOffset, int yOffset);

    int getWidth();
    int getHeight();

}
