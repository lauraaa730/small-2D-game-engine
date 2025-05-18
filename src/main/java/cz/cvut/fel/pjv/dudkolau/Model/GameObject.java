package cz.cvut.fel.pjv.dudkolau.Model;

public interface GameObject {
    int getxCoord();
    int getyCoord();
    String getImageName();

    @SuppressWarnings("unused")
    void setImageName(String imageName);

    void setxCoord(int x);
    void setyCoord(int y);

    HitBox getHitBox();
    void setHitBox(int xOffset, int yOffset);

    int getWidth();
    int getHeight();

    void setWidth(int width);
    void setHeight(int height);

}
