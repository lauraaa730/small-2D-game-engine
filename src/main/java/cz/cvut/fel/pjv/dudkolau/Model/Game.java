package cz.cvut.fel.pjv.dudkolau.Model;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.checkCollisionWithEntity;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.checkCollisionWithObject;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fel.pjv.dudkolau.Model.Views;


public class Game {
    private Player player;
    private Level currLevel;

    private int tileDimension;
    private boolean isPaused;
    private int levelsNum = 0;
    private int enemieMoveCounter = 0;
    private boolean mainMenuOn;


    private List<Level> levels = new ArrayList<>();


    public boolean getIsPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void setTileDimension(int tileDimension) {
        this.tileDimension = tileDimension;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileDimension() {
        return tileDimension;
    }

    public List<Level> getLevels() {
        return levels;
    }


    public Game() {
        mainMenuOn = true;
        startGame(mainMenuOn);

        GameData gameData = new GameData();
        gameData.setTotalLevelNum(4);

    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCurrLevel(Level currLevel) {
        this.currLevel = currLevel;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getLevelsNum() {
        return levelsNum;
    }

    public void setLevelsNum(int levelsNum) {
        this.levelsNum = levelsNum;
    }

    public int getEnemieMoveCounter() {
        return enemieMoveCounter;
    }

    public void setEnemieMoveCounter(int enemieMoveCounter) {
        this.enemieMoveCounter = enemieMoveCounter;
    }

    public boolean isMainMenuOn() {
        return mainMenuOn;
    }

    public void setMainMenuOn(boolean mainMenuOn) {
        this.mainMenuOn = mainMenuOn;
    }

    public void startGame(boolean newGame) {
        startNewGame();
        //loadSavedGame();
    }

    public void update() {
        GameObject currentObject;
        //move the player***************************************************
        player.move(player.getCurrDirection(), width, height, tileDimension);
        for (int i = 0; i < currLevel.getBackgroundObjectsNum() ; i++) {
            currentObject = this.currLevel.getBackgroundObjects().get(i);
            if (checkCollisionWithObject(player,currentObject)) {
                player.jumpBack(true,width, height);
            }
        }

        //Check and use doors **********************************************************
        Door currDoor;
        for (int i = 0; i < currLevel.getDoorsNum() ; i++) {
            currDoor = this.currLevel.getDoors().get(i);
            if (checkCollisionWithObject(player,currDoor)) {
                player.jumpBack(true,width, height);
                System.out.println("collision");
            }
            // Check if player is interacting with an object
            if ( player.isInteracting() && isPlayerFacingObject(currDoor)) {
                // wtf proc mi tady nejde dat current object do te podminky??? - protoze gameobject nema tu metodu
                if (currDoor.getLevel1() == currLevel.getLevelType()) {
                    currLevel = levels.get(currDoor.getLevel2()-1);
                    for (int j = 0; j < this.currLevel.getBackgroundObjectsNum(); j++) {
                        this.currLevel.getBackgroundObjects().get(j).setHitBox(bushXOffset, bushYOffset);
                    }
                    for (int j = 0; j < this.currLevel.getDoorsNum(); j++) {
                        this.currLevel.getDoors().get(j).setHitBox(0, 0);
                    }
                    for (int j = 0; j < this.currLevel.getEnemiesNum(); j++) {
                        this.currLevel.getEnemies().get(j).setHitBox(0, 0);
                    }
                    System.out.println(width/tileDimension);
                    System.out.println(currDoor.getWidth());
                    player.setxCoord(width/tileDimension - currDoor.getxCoord());
                    if (player.getxCoord() >=width/tileDimension-player.getWidth()/tileDimension) {
                        player.setxCoord((width-player.getWidth())/tileDimension);
                    }
                    player.setHitBox(playerXOffset,playerYOffset);
                    return;
                }
            }
        }

        //Enemies movement and collision *********************************
        Enemy e;
        for (int i = 0; i < currLevel.getEnemiesNum() ; i++) {
            e=currLevel.getEnemies().get(i);

            if (e.isHasCollision() && checkCollisionWithEntity(player,e)) {
                player.jumpBack(true,width, height);
            }
            if (enemieMoveCounter !=2) { //slowing down enemies
                if (e.getSelfMovementPosition()>=enemyMovementLength) {
                    //System.out.println("Turning left");
                    e.setCurrDirection(Directions.LEFT);
                } else if (e.getSelfMovementPosition()<=0) {
                    //System.out.println("Turning right");
                    e.setCurrDirection(Directions.RIGHT);
                }
                e.updateSelfMovementPosition();
                //System.out.println("SM Position: "+ e.getSelfMovementPosition() +", Hitbox xCoord: "+ e.getHitBox().getxCoord());
                e.move(e.getCurrDirection(), width, height, tileDimension);
                //TODO aby se vcas odrazel od sten?
                if (e.isHasCollision() && checkCollisionWithEntity(player,e)) {
                    e.jumpBack(width, height);
                    if  (e.getCurrDirection()==Directions.LEFT) {
                        e.setSelfMovementPosition(0);
                        e.setCurrDirection(Directions.RIGHT);
                    } else if (e.getCurrDirection() == Directions.RIGHT) {
                        e.setSelfMovementPosition(enemyMovementLength);
                        e.setCurrDirection(Directions.LEFT);
                    }
                }
                //TODO enemy by se mel pohybovat i vertikalne, pridat do jsonu movement jakej atd
            }
            enemieMoveCounter = (enemieMoveCounter+1)%3; // for speeding up or slowing down enemy movement

        }//*****************************************************************

    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public void startNewGame() {
        //TODO prvotni menu, az pak se rozrazuje jestli se pokracuje nebo new game, if else..

        this.player = new Player();
        player.setMaxHealth(8); //TODO pridat do json souboru
        player.setCurrHealth(4);
        player.setInteracting(false);
        player.setxCoord(0);
        player.setyCoord(0);
        player.setWidth(playerWidth);
        player.setHeight(playerHeight);
        player.setHitBox(playerXOffset, playerYOffset);
        loadAllLevels();
        currLevel = levels.getFirst();
        if (currLevel==null) {
            System.out.println("Level1 is null!");
            System.exit(1);
        }
        for (int i = 0; i < this.currLevel.getBackgroundObjectsNum(); i++) {
            this.currLevel.getBackgroundObjects().get(i).setHitBox(bushXOffset, bushYOffset);
        }
        for (int i = 0; i < this.currLevel.getDoorsNum(); i++) {
            this.currLevel.getDoors().get(i).setHitBox(0, 0);
        }
        for (int i = 0; i < this.currLevel.getEnemiesNum(); i++) {
            this.currLevel.getEnemies().get(i).setHitBox(0, 0);
        }
        System.out.println(currLevel.getDoors().getFirst().getWidth());
        System.out.println(currLevel.getDoors().getFirst().getxCoord());
    }


    public Level getCurrLevel() {
        return currLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.addAll(this.currLevel.getBackgroundObjects());
        gameObjects.addAll(this.currLevel.getInteractableObjects());
        gameObjects.addAll(this.currLevel.getDoors());
        return gameObjects;
    }

    private boolean isPlayerFacingObject(GameObject object) {
        player.move(player.getLastDirection(), width, height, tileDimension);
        if (checkCollisionWithObject(player,object)) {
            player.jumpBack(false,width, height);
            return true;
        }
        player.jumpBack(false,width, height);

        return false;
    }

    // Saving and loading help functions ***********************
    public void loadSavedGame() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            GameData gameData = objectMapper.readerWithView(Views.SaveGameView.class).readValue(
                    new File("C:/Users/laura/Documents/PJV/semestralka/dudkolau/saves/current-game-state.json" ), GameData.class
            );

            levels = gameData.getLevels();
            System.out.println(gameData.getCurrPlayerLevel());
            for (int i = 0; i <levels.size(); i++) {
                if (levels.get(i).getLevelType()==gameData.getCurrPlayerLevel()) {
                    currLevel = levels.get(i);
                    break;
                }
            }

            this.player = new Player();
            player.setInteracting(false);
            player.setMaxHealth(gameData.getMaxPlayerHealth());
            player.setCurrHealth(gameData.getCurrPlayerHealth());
            player.setxCoord(gameData.getCurrPlayerX());
            player.setyCoord(gameData.getCurrPlayerY());
            System.out.println(player.getxCoord());
            player.setWidth(playerWidth);
            player.setHeight(playerHeight);
            player.setHitBox(playerXOffset, playerYOffset);
            System.out.println("Loaded game succesfully!");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Something went wrong with json");
        }

        for (int i = 0; i < this.currLevel.getBackgroundObjectsNum(); i++) {
            this.currLevel.getBackgroundObjects().get(i).setHitBox(bushXOffset, bushYOffset);
        }
        for (int i = 0; i < this.currLevel.getEnemiesNum(); i++) {
            System.out.println(currLevel.getEnemies().get(i).getCurrDirection());
            this.currLevel.getEnemies().get(i).setHitBox(0, 0);
        }
        for (int i = 0; i < this.currLevel.getDoorsNum(); i++) {
            this.currLevel.getDoors().get(i).setHitBox(bushXOffset, bushYOffset);
        }

    }

    private Level loadLevelFromJson(String levelName){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("C:/Users/laura/Documents/PJV/semestralka/dudkolau/saves/" + levelName);

            Level levelToRead = objectMapper.readerWithView(Views.SaveGameView.class).readValue(jsonFile, Level.class);
            System.out.println("Loaded level");
            return levelToRead;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Something went wrong with json");
        }
        return null;
    }

    private void loadAllLevels() {
        Level l;
        for (int i = 0; i < maxLevelNum; i++) {
            l = loadLevelFromJson("level"+(i+1)+".json");
            if (l!=null) {
                levels.add(l);
                levelsNum++;
            } else {
                System.out.println("Level number " +(i+1) + " is empty!");
            }

        }
    }

    public void saveGame() {
        GameData gameData = new GameData();
        gameData.setCurrPlayerHealth(this.player.getCurrHealth());
        gameData.setMaxPlayerHealth(this.player.getMaxHealth());
        gameData.setCurrPlayerLevel(currLevel.getLevelType());
        gameData.setCurrPlayerX(this.player.getxCoord());
        gameData.setCurrPlayerY(this.player.getyCoord());
        gameData.setTotalLevelNum(levelsNum);

        gameData.setLevels(levels);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.addMixIn(java.awt.Rectangle.class, RectangleMixin.class);

            objectMapper.writerWithView(Views.SaveGameView.class).writeValue(
                    new File("C:/Users/laura/Documents/PJV/semestralka/dudkolau/saves/current-game-state.json" ), gameData
            );
            System.out.println("game saved succesfully");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Something went wrong with json");
        }


    }

}
