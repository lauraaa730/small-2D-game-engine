package cz.cvut.fel.pjv.dudkolau.Model;

import static cz.cvut.fel.pjv.dudkolau.Constants.tileDimension;

public class Enemy extends AbstractEntity{

    private boolean hasCollision;
    private int selfMovementPosition = 0;

    public Enemy() {
        //for JSON
    }

    public void updateSelfMovementPosition() {
        if (this.currDirection == Directions.LEFT) {
            this.selfMovementPosition--;
        } else if (this.currDirection==Directions.RIGHT) {
            this.selfMovementPosition++;
        }
    }

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

