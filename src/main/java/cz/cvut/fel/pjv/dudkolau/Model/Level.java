package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

public class Level {

    private int levelType;


    private String backgroundName;



    //@JsonView(Views.SaveGameView.class)
    private int backgroundObjectsNum;
    private int interactableObjectsNum;
    //@JsonView(Views.SaveGameView.class)
    private int enemiesNum;
    private int doorsNum;
    private List<Door> doors = new ArrayList<>();
    private List<InteractableObject> interactableObjects = new ArrayList<>();
    private List<BackgroundObject> backgroundObjects = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();

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
    public String getBackgroundName() {
        return backgroundName;
    }

    public void setBackgroundName(String backgroundName) {
        this.backgroundName = backgroundName;
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
}
