package cz.cvut.fel.pjv.dudkolau.Model;

/**
 * Represents a movable and damageable entity in the game world.
 *
 * Implementing classes should provide logic for updating position,
 * handling health, and responding to directional movement.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
public interface Entity {
    int getMaxHealth();
    int getCurrHealth();
    void setMaxHealth(int maxHealth);
    void setCurrHealth(int currHealth);
    int getxCoord();
    int getyCoord();
    Directions getCurrDirection();
    void setCurrDirection(Directions d);

    /**
     * Moves the entity in a given direction while considering map boundaries and tile size.
     *
     * @param d             the direction to move in
     * @param w             the width of the game map in pixels
     * @param h             the height of the game map in pixels
     * @param tileDimension the size of a tile in pixels
     */
    void move(Directions d, int w, int h, int tileDimension);
    void setxCoord(int x);
    void setyCoord(int y);
    HitBox getHitBox();

    /**
     * Sets the position and size of the entity's hitbox with the specified offsets.
     *
     * <p>The hitbox is updated based on the entity's current coordinates and dimensions,
     * along with additional x and y offsets. The bigger the offsets are, the smaller the hitbox is</p>
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
