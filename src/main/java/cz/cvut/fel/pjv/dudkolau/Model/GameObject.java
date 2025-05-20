package cz.cvut.fel.pjv.dudkolau.Model;


/**
 * Interface for all objects in game,
 * that ahve hitboxes, but they do not move,
 * have health etc. Some can be interacted with,
 * some can't. (Bushes, potions, doors...)
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
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
