package cz.cvut.fel.pjv.dudkolau.Model;

public interface GameObject {
    public int getxCoord();
    public int getyCoord();
    public String getImageName();
    public void setImageName(String imageName);

    public void setxCoord(int x);
    public void setyCoord(int y);

    public HitBox getHitBox();
    public void setHitBox(int xOffset, int yOffset);

    public int getWidth();
    public int getHeight();

    public void setWidth(int width);
    public void setHeight(int height);


}
