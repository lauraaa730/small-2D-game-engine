package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;

public class Player implements Entity {
    private int maxHealth;
    private int currHealth;
    private int xCoord;

    private int yCoord;
    private int height;
    private int width;
    @JsonIgnore
    private boolean interacting; //this does not need to go into json
    @JsonIgnore
    private boolean fighting;
    private  Directions lastDirection = Directions.NONE;

    private boolean invincible = false;
    private int damage = 1;

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

    @JsonIgnore
    private HitBox hitBox = new HitBox();
    //for JSON

    private HitBox attackHitBox = new HitBox();
    public Player() {
    }



    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }

    private int piecesCollected[];

    private Directions currDirection = Directions.NONE;

    @Override
    public void move(Directions d, int w, int h, int tileDimension){;

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


    public int interact() { return 0;}

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    public Directions getCurrDirection() {
        return currDirection;
    }

    @Override
    public void setCurrDirection(Directions d) {
        currDirection = d;
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

    public HitBox getAttackHitBox() {
        return attackHitBox;
    }

    public void setAttackHitBox() {
        this.attackHitBox.setRectangle(xCoord-attackFaceOffset, yCoord-attackSideOffset, this.width/2, this.height*3/2,0,0);
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
}
