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

    /**
     * Sets the position and size of the object's hitbox with the specified offsets.
     *
     * <p>The bigger the offsets are, the smaller the hitbox is</p>
     *
     * @param xOffset the horizontal offset to apply to the hitbox
     * @param yOffset the vertical offset to apply to the hitbox
     */
    void setHitBox(int xOffset, int yOffset);

    int getWidth();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);
}
