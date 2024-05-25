package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int levelType;
    private int backgroundObjectsNum;
    private int interactableObjectsNum;
    private int enemiesNum;
    public Level() {
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public int getBackgroundObjectsNum() {
        return backgroundObjectsNum;
    }

    public void setBackgroundObjectsNum(int backgroundObjectsNum) {
        this.backgroundObjectsNum = backgroundObjectsNum;
    }

    public int getInteractableObjectsNum() {
        return interactableObjectsNum;
    }

    public void setInteractableObjectsNum(int interactableObjectsNum) {
        this.interactableObjectsNum = interactableObjectsNum;
    }

    public int getEnemiesNum() {
        return enemiesNum;
    }

    public void setEnemiesNum(int enemiesNum) {
        this.enemiesNum = enemiesNum;
    }

    public List<InteractableObject> getInteractableObjects() {
        return interactableObjects;
    }

    public void setInteractableObjects(List<InteractableObject> interactableObjects) {
        this.interactableObjects = interactableObjects;
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

    public List<GameObject> getObjectsInLevel() {
        return objectsInLevel;
    }

    public void setObjectsInLevel(List<GameObject> objectsInLevel) {
        this.objectsInLevel = objectsInLevel;
    }

    public List<InteractableObject> interactableObjects = new ArrayList<>();
    public List<BackgroundObject> backgroundObjects = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public List<GameObject> objectsInLevel = new ArrayList<>();

}
