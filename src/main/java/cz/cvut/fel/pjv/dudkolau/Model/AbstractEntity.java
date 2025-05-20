package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Parent entity for player and enemy,
 * has basic methods for movement, collisions,
 * getters and setters.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
public abstract class AbstractEntity implements Entity {

    public int maxHealth;
    public int currHealth;
    public int xCoord;
    public int yCoord;
    public int height;
    public int width;
    public String imageName;
    @JsonIgnore
    public HitBox hitBox = new HitBox();
    public Directions currDirection;

    /**
     * Moves the entity in the specified direction if the move stays within the given boundaries.
     *
     * <p>This method updates both the logical coordinates ({@code xCoord}, {@code yCoord}) and
     * the position of the entity's hitbox. It checks boundary conditions based on the provided
     * world width and height, along with tile dimensions.</p>
     *
     * @param d the direction to move (LEFT, RIGHT, UP, or DOWN)
     * @param w the total width of the world in pixels
     * @param h the total height of the world in pixels
     * @param tileDimension the dimension (both width and height) of one tile in pixels
     */
    @Override
    public void move(Directions d, int w, int h, int tileDimension){
        if (d==Directions.LEFT && this.hitBox.getxCoord()*tileDimension>0) {
            xCoord--;
            this.hitBox.changexCoord(-1);
        } else if (d==Directions.RIGHT && this.hitBox.getxCoord()*tileDimension<w- hitBox.getWidth()) {
            xCoord++;
            this.hitBox.changexCoord(1);
        } else if (d==Directions.UP && this.hitBox.getyCoord()*tileDimension>0) {
            yCoord--;
            this.hitBox.changeYCoord(-1);
        } else if (d==Directions.DOWN && this.hitBox.getyCoord()*tileDimension<h- hitBox.getHeight()) {
            yCoord++;
            this.hitBox.changeYCoord(1);
        }
    }

    @SuppressWarnings("unused")
    public String getImageName() {
        return imageName;
    }

    @SuppressWarnings("unused")
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    /**
     * Sets the position and size of the entity's hitbox with the specified offsets.
     *
     * <p>The hitbox is updated based on the entity's current coordinates and dimensions,
     * along with additional x and y offsets. The bigger the offsets are, the smaller the hitbox is</p>
     *
     * @param xOffset the horizontal offset to apply to the hitbox
     * @param yOffset the vertical offset to apply to the hitbox
     */
    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public int getCurrHealth() {
        return currHealth;
    }

    @Override
    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getxCoord() { return xCoord; }

    @Override
    public int getyCoord() { return yCoord; }

    @Override
    public Directions getCurrDirection() {
        return currDirection;
    }

    @Override
    public void setCurrDirection(Directions d) {
        currDirection = d;
    }

    @Override
    public void setxCoord(int x) {
        xCoord=x;
    }

    @Override
    public void setyCoord(int y) {
        yCoord=y;
    }
}