package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all objects and entities within that level, but not player.
 *
 * @SuppressWarnings("unused") for
 * getters and setter marked as
 * "unused", because they are
 * needed for json de/serialization.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
@SuppressWarnings("unused")
public class Level {
    private int levelType;
    private String backgroundName;
    private int backgroundObjectsNum;
    private int enemiesNum;
    private int doorsNum;
    private int potionsNum;
    private int gameButtonsNum;
    private boolean doorsWereUnlocked;
    private List<Door> doors = new ArrayList<>();
    private List<Potion> potions = new ArrayList<>();
    private  List<GameButton> gameButtons = new ArrayList<>();
    private List<BackgroundObject> backgroundObjects = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();

    public Level() {
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) { this.levelType = levelType; }

    public int getPotionsNum() {
        return potionsNum;
    }

    public void setPotionsNum(int potionsNum) {
        this.potionsNum = potionsNum;
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public void setPotions(List<Potion> potions) {
        this.potions = potions;
    }

    public int getBackgroundObjectsNum() {
        return backgroundObjectsNum;
    }

    public String getBackgroundName() {
        return backgroundName;
    }

    public void setBackgroundName(String backgroundName) {
        this.backgroundName = backgroundName;
    }

    public void setBackgroundObjectsNum(int backgroundObjectsNum) {
        this.backgroundObjectsNum = backgroundObjectsNum;
    }

    public int getEnemiesNum() {
        return enemiesNum;
    }

    public void setEnemiesNum(int enemiesNum) {
        this.enemiesNum = enemiesNum;
    }

    public List<BackgroundObject> getBackgroundObjects() {
        return backgroundObjects;
    }

    public void setBackgroundObjects(List<BackgroundObject> backgroundObjects) {
        this.backgroundObjects = backgroundObjects;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int getDoorsNum() {
        return doorsNum;
    }

    public void setDoorsNum(int doorsNum) {
        this.doorsNum = doorsNum;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public int getGameButtonsNum() {
        return gameButtonsNum;
    }

    public void setGameButtonsNum(int gameButtonsNum) {
        this.gameButtonsNum = gameButtonsNum;
    }

    public List<GameButton> getGameButtons() {
        return gameButtons;
    }

    public void setGameButtons(List<GameButton> gameButtons) {
        this.gameButtons = gameButtons;
    }

    public boolean isDoorsWereUnlocked() { return doorsWereUnlocked; }

    public void setDoorsWereUnlocked(boolean doorsWereUnlocked) { this.doorsWereUnlocked = doorsWereUnlocked; }
}
