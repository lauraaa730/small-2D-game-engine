package cz.cvut.fel.pjv.dudkolau.Model;


public interface Entity {
    int getMaxHealth();

    int getCurrHealth();

    void setMaxHealth(int maxHealth);

    void setCurrHealth(int currHealth);

    int getxCoord();

    int getyCoord();

    Directions getCurrDirection();

    void setCurrDirection(Directions d);

    void move(Directions d, int w, int h, int tileDimension);

    void setxCoord(int x);

    void setyCoord(int y);

    HitBox getHitBox();

    void setHitBox(int xOffset, int yOffset);

    int getWidth();

    int getHeight();

    void setWidth(int width);

    void setHeight(int height);
}
