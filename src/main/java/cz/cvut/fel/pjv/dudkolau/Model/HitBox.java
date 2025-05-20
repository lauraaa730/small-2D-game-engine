package cz.cvut.fel.pjv.dudkolau.Model;
import java.awt.*;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

/**
 * Represents a hitbox used to detect collisions between game entities and objects.
 * The hitbox is based on a rectangle that can be positioned and resized.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
public class HitBox {
    private Rectangle  rectangle;
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;

    public int getxCoord() {
        return xCoord;
    }

    /**
     * Sets the X-coordinate of the object and updates the hitbox rectangle's position accordingly.
     *
     * @param xCoord the new Y-coordinate in tile units
     */
    public void setxCoord(int xCoord) {

        this.xCoord = xCoord;
        this.rectangle.setLocation(xCoord*tileDimension, yCoord*tileDimension);
        this.rectangle.setBounds(this.rectangle);
    }

    public int getyCoord() {
        return yCoord;
    }

    /**
     * Sets the Y-coordinate of the object and updates the hitbox rectangle's position accordingly.
     *
     * @param yCoord the new Y-coordinate in tile units
     */
    public void setyCoord(int yCoord) {

        this.yCoord = yCoord;
        this.rectangle.setLocation(xCoord*tileDimension, yCoord*tileDimension);
        this.rectangle.setBounds(this.rectangle);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * Initializes the hitbox rectangle with adjusted coordinates and dimensions based on offsets.
     *
     * @param x         the X-coordinate in tile units
     * @param y         the Y-coordinate in tile units
     * @param width     the width of the hitbox in pixels
     * @param height    the height of the hitbox in pixels
     * @param xOffset   the horizontal offset in pixels to shrink the hitbox
     * @param yOffset   the vertical offset in pixels to shrink the hitbox
     */
    public void setRectangle(int x, int y, int width, int height, int xOffset, int yOffset) {
        this.rectangle = new Rectangle();
        this.xCoord = x+(xOffset/tileDimension);
        this.yCoord = y+(yOffset/tileDimension);
        this.width = width-2*xOffset;
        this.height = height-2*yOffset;
        this.rectangle.setLocation(x*tileDimension,y*tileDimension);
        this.rectangle.setSize(this.width,this.height);
        this.rectangle.setBounds(xCoord*tileDimension,yCoord*tileDimension, this.width, this.height);
    }

    /**
     * Changes the X-coordinate by a specified amount and updates the rectangle's position.
     *
     * @param x the amount to change the X-coordinate by (in tile units)
     */
    public void changexCoord(int x) {
        this.xCoord+= x;
        this.rectangle.setLocation(xCoord*tileDimension, yCoord*tileDimension);
        this.rectangle.setBounds(this.rectangle);
    }

    /**
     * Changes the Y-coordinate by a specified amount and updates the rectangle's position.
     *
     * @param y the amount to change the Y-coordinate by (in tile units)
     */
    public void changeYCoord(int y) {
        this.yCoord+=y;
        this.rectangle.setLocation( xCoord*tileDimension, yCoord*tileDimension);
        this.rectangle.setBounds(this.rectangle);
    }

    /**
     * Checks whether a given entity is colliding with a game object.
     *
     * @param entity the entity whose hitbox is being checked
     * @param object the game object to check collision against
     * @return true if the hitboxes intersect, false otherwise
     */
    public static boolean checkCollisionWithObject(Entity entity, GameObject object) {
        return entity.getHitBox().getRectangle().intersects(object.getHitBox().getRectangle());
    }

    /**
     * Checks whether two entities are colliding based on their hitboxes.
     *
     * @param entity1 the first entity
     * @param entity2 the second entity
     * @return true if the hitboxes intersect, false otherwise
     */
    public static boolean checkCollisionWithEntity(Entity entity1, Entity entity2) {
        return entity1.getHitBox().getRectangle().intersects(entity2.getHitBox().getRectangle());
    }
}
