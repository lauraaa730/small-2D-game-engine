package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int levelType;
    private int backgroundObjectsNum;
    private int interactableObjectsNum;
    private int enemiesNum;
    public List<InteractableObject> interactableObjects = new ArrayList<>();
    public List<BackgroundObject> backgroundObjects = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public List<GameObject> objectsInLevel = new ArrayList<>();

    public boolean loadLevelFromJson(int levelType){return false;}
}
