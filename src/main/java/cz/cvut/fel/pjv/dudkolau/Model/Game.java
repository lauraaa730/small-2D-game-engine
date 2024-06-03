package cz.cvut.fel.pjv.dudkolau.Model;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.willCollideWithObject;

public class Game {
    private Player player;
    private Level currLevel;

    private int Width;
    private int Height;
    private int tileDimension;
    private boolean isPaused;

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

    private boolean didLevelChange = false;

    public Game(Player player, int h, int w) {
        this.player = player;
        player.setxCoord(0);
        player.setyCoord(0);
        player.setWidth(playerWidth);
        player.setHeight(playerHeight);
        player.setHitBox(playerXOffset, playerYOffset);
        Height = h;
        Width = w;
        this.currLevel = loadLevelFromJson();
        if (currLevel==null) {
            System.out.println("Level1 is null!");
            System.exit(1);
        }


        /*//CODE BELOW FOR TESTING - generating level NOT from JSON, it works

        BackgroundObject bush = new BackgroundObject();
        bush.setxCoord(50);
        bush.setyCoord(50);
        bush.setWidth(228);
        bush.setHeight(151);
        bush.setHitBox(bushXOffset, bushYOffset);
        currLevel.objectsInLevel.add(bush);
        currLevel.setEnemiesNum(0);
        currLevel.setLevelType(1);
        currLevel.setEnemies(new ArrayList<>());
        currLevel.setBackgroundObjects(new ArrayList<>());
        currLevel.setInteractableObjects(new ArrayList<>());
        currLevel.setBackgroundObjectsNum(0);
        currLevel.setInteractableObjectsNum(0);*/

        GameData gameData = new GameData();
        gameData.setTotalLevelNum(4);

    }

    public void update() {
        if (!willCollideWithObject(player, this.currLevel.objectsInLevel.getFirst() , player.getCurrDirection())) {
            player.move(player.getCurrDirection(), Width, Height, tileDimension);
        }
        //DOESNT WORK BUT STILL MIGHT TRY TO FIX IT - KEEP
        /*//if collision happens, jump back**********
        if (checkCollisionWithObject(player,this.currLevel.objectsInLevel.getFirst() , player.getCurrDirection())) {
            if (player.getCurrDirection() == Directions.LEFT) {
                player.move(Directions.RIGHT, Width, Height, tileDimension);
            } else if (player.getCurrDirection() == Directions.RIGHT) {
                player.move(Directions.LEFT, Width, Height, tileDimension);
            } else if (player.getCurrDirection() == Directions.UP) {
                player.move(Directions.DOWN, Width, Height, tileDimension);
            } else if (player.getCurrDirection() == Directions.DOWN) {
                player.move(Directions.UP, Width, Height, tileDimension);
            }
        }//******************************************/

    }

    private boolean isThereObstacle(Directions d, int x, int y) {
        return false;
    }

    public Level getCurrLevel() {
        return currLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public List<GameObject> getGameObjects() {
        return currLevel.objectsInLevel;
    }

    private Level loadLevelFromJson(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Level levelToRead = objectMapper.readValue(
                    new File("C:/Users/laura/Documents/PJV/semestralka/dudkolau/saves/level1.json"), Level.class
            );
            System.out.println(levelToRead);
            return levelToRead;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

            System.out.println("Something went wrong with json");
        }
        return null;
    }
}
