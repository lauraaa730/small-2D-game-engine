package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Potion extends InteractableObject implements GameObject {

    private int xCoord;
    private int yCoord;
    private String imageName;
    private int width;
    private int height;
    private Effect effect;
    private int healthAdd;

    private int damageModifier;

    /*How many game cycles will this effect last - applies only to invincibility*/
    private int invincibilityDuration;

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public int getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(int damageModifier) {
        this.damageModifier = damageModifier;
    }

    public int getInvincibilityDuration() {
        return invincibilityDuration;
    }

    public void setInvincibilityDuration(int invincibilityDuration) {
        this.invincibilityDuration = invincibilityDuration;
    }

    public int getHealthAdd() {
        return healthAdd;
    }

    public void setHealthAdd(int healthAdd) {
        this.healthAdd = healthAdd;
    }

    @JsonIgnore
    private HitBox hitBox = new HitBox();

    public String getImageName() {
        return this.imageName;
    }


    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public HitBox getHitBox() {
        return super.getHitBox();
    }

    //TODO BACHA !!!!! prepsat super. settery a gettery, blblo imagename u potionu!!
    @Override
    public void setHitBox(int xOffset, int yOffset) {
        super.setHitBox(xOffset, yOffset);
    }

    @Override
    public int getxCoord() {
        return super.getxCoord();
    }

    @Override
    public int getyCoord() {
        return super.getyCoord();
    }

    @Override
    public void setxCoord(int x) {
        super.setxCoord(x);
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    @Override
    public void setyCoord(int y) {
        super.setyCoord(y);
    }

}
