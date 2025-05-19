package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;

public class Player extends AbstractEntity {
    private int xOffset;
    private int yOffset;
    @JsonIgnore
    private boolean interacting;
    @JsonIgnore
    private boolean fighting;
    private  Directions lastDirection = Directions.NONE;
    private boolean invincible = false;
    private int damage = 1;
    @JsonIgnore
    private HitBox attackHitBox = new HitBox();

    public Player() {
        //declaration for JSON
    }

    public void setAttackHitBox() {
        this.attackHitBox.setRectangle(xCoord-attackFaceOffset, yCoord-attackSideOffset,
                this.width/2, this.height*3/2,0,0);
    }

    public void jumpBack(boolean fromCurrDir,int w, int h) {
        //if we want to check from curr direction, fromCurrDir will be 1,
        //if from last dir, it will be 0
        if (fromCurrDir) {
            if (currDirection == Directions.LEFT) {
                move(Directions.RIGHT, w, h, tileDimension);
            } else if (currDirection == Directions.RIGHT) {
                move(Directions.LEFT, w, h, tileDimension);
            } else if (currDirection == Directions.UP) {
                move(Directions.DOWN, w, h, tileDimension);
            } else if (currDirection == Directions.DOWN) {
                move(Directions.UP, w, h, tileDimension);
            }
        } else {
            if (lastDirection == Directions.LEFT) {
                move(Directions.RIGHT, w, h, tileDimension);
            } else if (lastDirection == Directions.RIGHT) {
                move(Directions.LEFT, w, h, tileDimension);
            } else if (lastDirection == Directions.UP) {
                move(Directions.DOWN, w, h, tileDimension);
            } else if (lastDirection == Directions.DOWN) {
                move(Directions.UP, w, h, tileDimension);
            }
        }
    }

    public void updateAttackHitbox() {
        if (currDirection == Directions.LEFT ||
                (currDirection == Directions.NONE && lastDirection==Directions.LEFT)) {
            this.attackHitBox.setxCoord(xCoord-attackFaceOffset);
            this.attackHitBox.setyCoord(yCoord-attackSideOffset);
            this.attackHitBox.setHeight(height+attackSideOffset*2);
            this.attackHitBox.setWidth(width/2);
        } else if (currDirection == Directions.RIGHT ||
                (currDirection == Directions.NONE && lastDirection==Directions.RIGHT)) {
            this.attackHitBox.setxCoord(xCoord+width/tileDimension-attackFaceOffset);
            this.attackHitBox.setyCoord(yCoord-attackSideOffset);
            this.attackHitBox.setHeight(height+attackSideOffset*3);
            this.attackHitBox.setWidth(width/2);
        } else if (currDirection == Directions.UP ||
                (currDirection == Directions.NONE && lastDirection==Directions.UP)) {
            this.attackHitBox.setxCoord(xCoord-attackSideOffset);
            this.attackHitBox.setyCoord(yCoord-attackFaceOffset);
            this.attackHitBox.setHeight(height/2);
            this.attackHitBox.setWidth(width+3*attackSideOffset);
        } else if (currDirection ==  Directions.DOWN ||
                (currDirection == Directions.NONE && lastDirection==Directions.DOWN)) {
            this.attackHitBox.setxCoord(xCoord-attackSideOffset);
            this.attackHitBox.setyCoord(yCoord+height/tileDimension-attackFaceOffset);
            this.attackHitBox.setHeight(height/2);
            this.attackHitBox.setWidth(width+2*attackSideOffset);
        }
    }

    // Getters and setters --------------------------------------------

    public HitBox getAttackHitBox() {
        return attackHitBox;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isFighting() {
        return fighting;
    }

    public void setFighting(boolean fighting) {
        this.fighting = fighting;
    }

    public Directions getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Directions lastDirection) {
        this.lastDirection = lastDirection;
    }

    public boolean isInteracting() {
        return interacting;
    }

    public void setInteracting(boolean interacting) {
        this.interacting = interacting;
    }
}
