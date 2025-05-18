package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.dudkolau.Model.Views;


/*
* This will save current game data
* to a json file.
* For example: current position,
* puzzles completed etc..
*/
public class GameData {


    private int totalLevelNum;
    @JsonView(Views.gameView.class)
    private int currPlayerX;
    @JsonView(Views.gameView.class)
    private int currPlayerY;
    private int currPlayerLevel;
    private int currPlayerHealth;
    private int maxPlayerHealth;

    private int inviPotionTimer;
    private int damagePotionTimer;

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

    //@JsonView(Views.SaveGameView.class)
    private List<Level> levels;

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

    public GameData() {
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
