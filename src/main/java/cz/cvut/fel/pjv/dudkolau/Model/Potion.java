package cz.cvut.fel.pjv.dudkolau.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Potion implements GameObject {

    private int xCoord;
    private int yCoord;
    private String imageName;
    private int width;
    private int height;
    private Effect effect;
    private int healthAdd;

    private int damageModifier;

    @JsonIgnore
    private HitBox hitBox = new HitBox();

    /*How many game cycles will this effect last*/
    private int effectDuration;


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

    public int getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    public int getHealthAdd() {
        return healthAdd;
    }

    public void setHealthAdd(int healthAdd) {
        this.healthAdd = healthAdd;
    }

    public String getImageName() {
        return this.imageName;
    }


    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public int getxCoord() {
        return xCoord;
    }

    @Override
    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    @Override
    public int getyCoord() {
        return yCoord;
    }

    @Override
    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
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
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public HitBox getHitBox() {
        return hitBox;
    }

    public void setHitBox(int xOffset, int yOffset) {
        this.hitBox.setRectangle(xCoord, yCoord, this.width, this.height, xOffset, yOffset);
    }
}
