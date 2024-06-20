package cz.cvut.fel.pjv.dudkolau.Model;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.checkCollisionWithEntity;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.checkCollisionWithObject;

public class Game {
    private Player player;
    private Level currLevel;

    private int Width;
    private int Height;
    private int tileDimension;
    private boolean isPaused;
    private int levelsNum = 0;
    private int enemieMoveCounter = 0;

    private boolean playerCollidesWithEnemy = false; //add to game data

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

    public void setWidth(int width) {
        Width = width;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }

    public int getTileDimension() {
        return tileDimension;
    }

    public List<Level> getLevels() {
        return levels;
    }


    public Game(Player player, int h, int w) {
        this.player = player;
        player.setxCoord(0);
        player.setyCoord(0);
        player.setWidth(playerWidth);
        player.setHeight(playerHeight);
        player.setHitBox(playerXOffset, playerYOffset);
        Height = h;
        Width = w;
        loadAllLevels();
        currLevel = levels.getFirst();
        if (currLevel==null) {
            System.out.println("Level1 is null!");
            System.exit(1);
        }
        for (int i = 0; i < this.currLevel.getBackgroundObjectsNum(); i++) {
            this.currLevel.getBackgroundObjects().get(i).setHitBox(bushXOffset, bushYOffset);
        }
        for (int i = 0; i < this.currLevel.getEnemiesNum(); i++) {
            System.out.println(currLevel.getEnemies().get(i).getCurrDirection());
            this.currLevel.getEnemies().get(i).setHitBox(0, 0);
        }

        GameData gameData = new GameData();
        gameData.setTotalLevelNum(4);

    }

    public void update() {
        //move the player***************************************************
        player.move(player.getCurrDirection(), Width, Height, tileDimension);
        for (int i = 0; i < currLevel.getBackgroundObjectsNum() ; i++) {
            if (checkCollisionWithObject(player,this.currLevel.getBackgroundObjects().get(i))) {
                player.jumpBack(Width, Height);
            }
        }

        //Enemies movement and collision *********************************
        Enemy e;
        for (int i = 0; i < currLevel.getEnemiesNum() ; i++) {
            e=currLevel.getEnemies().get(i);
            if (playerCollidesWithEnemy) {

            }
            if (playerCollidesWithEnemy && checkCollisionWithEntity(player,e)) {
                player.jumpBack(Width, Height);
            }
            if (enemieMoveCounter == 0 || enemieMoveCounter == 1) {
                if (e.getSelfMovementPosition()>=enemyMovementLength) {
                    System.out.println("Turning left");
                    e.setCurrDirection(Directions.LEFT);
                } else if (e.getSelfMovementPosition()<=0) {
                    System.out.println("Turning right");
                    e.setCurrDirection(Directions.RIGHT);
                }
                e.updateSelfMovementPosition();
                //System.out.println("SM Position: "+ e.getSelfMovementPosition() +", Hitbox xCoord: "+ e.getHitBox().getxCoord());
                e.move(e.getCurrDirection(), Width, Height, tileDimension);
                if (playerCollidesWithEnemy && checkCollisionWithEntity(player,e)) {
                    e.jumpBack(Width, Height);
                }
            }
            enemieMoveCounter = (enemieMoveCounter+1)%3;

        }//*****************************************************************

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
        return gameObjects;
    }

    private Level loadLevelFromJson(String levelName){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Level levelToRead = objectMapper.readValue(
                    new File("C:/Users/laura/Documents/PJV/semestralka/dudkolau/saves/" + levelName), Level.class
            );
            System.out.println(levelToRead);
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

}
