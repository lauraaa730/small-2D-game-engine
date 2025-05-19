package cz.cvut.fel.pjv.dudkolau.Model;
import java.util.List;

/*
* This will save current game data
* to a json file.
* For example: current position,
* puzzles completed etc..
*/
public class GameData {

    private int totalLevelNum;
    private int currPlayerX;
    private int currPlayerY;
    private int currPlayerLevel;
    private int currPlayerHealth;
    private int maxPlayerHealth;
    private int inviPotionTimer;
    private int damagePotionTimer;
    private List<Level> levels;

    public GameData() {
    }

    //Getters and setters --------------------------------------------

    public int getTotalLevelNum() {
        return totalLevelNum;
    }

    public int getInviPotionTimer() {
        return inviPotionTimer;
    }

    public void setInviPotionTimer(int inviPotionTimer) {
        this.inviPotionTimer = inviPotionTimer;
    }

    public int getDamagePotionTimer() {
        return damagePotionTimer;
    }

    public void setDamagePotionTimer(int damagePotionTimer) {
        this.damagePotionTimer = damagePotionTimer;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public int getCurrPlayerX() {
        return currPlayerX;
    }

    public void setCurrPlayerX(int currPlayerX) {
        this.currPlayerX = currPlayerX;
    }

    public int getCurrPlayerY() {
        return currPlayerY;
    }

    public void setCurrPlayerY(int currPlayerY) {
        this.currPlayerY = currPlayerY;
    }

    public int getCurrPlayerLevel() {
        return currPlayerLevel;
    }

    public void setCurrPlayerLevel(int currPlayerLevel) {
        this.currPlayerLevel = currPlayerLevel;
    }

    public void setTotalLevelNum(int totalLevelNum) {
        this.totalLevelNum = totalLevelNum;
    }

    public int getCurrPlayerHealth() {
        return currPlayerHealth;
    }

    public void setCurrPlayerHealth(int currPlayerHealth) {
        this.currPlayerHealth = currPlayerHealth;
    }

    public int getMaxPlayerHealth() {
        return maxPlayerHealth;
    }

    public void setMaxPlayerHealth(int maxPlayerHealth) {
        this.maxPlayerHealth = maxPlayerHealth;
    }
}
