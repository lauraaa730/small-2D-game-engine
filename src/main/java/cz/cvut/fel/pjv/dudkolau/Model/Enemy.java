package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

/**
 * Class for non-playable entity.
 * Moves itself back and forth.
 * Can have collision.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
public class Enemy extends AbstractEntity{

    private boolean hasCollision;
    private int selfMovementPosition = 0;

    public Enemy() {
        //for JSON
    }

    /**
     * Updates the internal position tracker for self-movement based on the current direction.
     * <p>If moving left, the position is decremented. If moving right, it is incremented.
     * Other directions are ignored.</p>
     */
    public void updateSelfMovementPosition() {
        if (this.currDirection == Directions.LEFT) {
            this.selfMovementPosition--;
        } else if (this.currDirection==Directions.RIGHT) {
            this.selfMovementPosition++;
        }
    }

    /**
     * Moves the entity one step in the opposite direction of its current movement.
     *
     * <p>This simulates a "jump back" or recoil behavior by calling the {@code move} method
     * with the opposite direction of {@code currDirection}.</p>
     *
     * @param w the width of the game map in pixels
     * @param h the height of the game map in pixels
     */
    public void jumpBack(int w, int h) {
        if (currDirection == Directions.LEFT) {
            move(Directions.RIGHT, w, h, tileDimension);
        } else if (currDirection == Directions.RIGHT) {
            move(Directions.LEFT, w, h, tileDimension);
        } else if (currDirection == Directions.UP) {
            move(Directions.DOWN, w, h, tileDimension);
        } else if (currDirection == Directions.DOWN) {
            move(Directions.UP, w, h, tileDimension);
        }
    }

    public boolean isHasCollision() {
        return hasCollision;
    }

    @SuppressWarnings("unused")
    public void setHasCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }

    public int getSelfMovementPosition() {
        return selfMovementPosition;
    }

    public void setSelfMovementPosition(int selfMovementPosition) {
        this.selfMovementPosition = selfMovementPosition;
    }
}

