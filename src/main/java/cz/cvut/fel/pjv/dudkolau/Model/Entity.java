package cz.cvut.fel.pjv.dudkolau.Model;


public interface Entity {
    public int getMaxHealth();
    public int getCurrHealth();
    public void setMaxHealth(int maxHealth);
    public void setCurrHealth(int currHealth);
    public int getxCoord();
    public int getyCoord();
    public Directions getCurrDirection();
    public void setCurrDirection(Directions d);
    public void move(Directions d, int w, int h, int tileDimension);
    public void attack(Directions d);

    public void setxCoord(int x);
    public void setyCoord(int y);

    public HitBox getHitBox();
    public void setHitBox(int xOffset, int yOffset);

}
