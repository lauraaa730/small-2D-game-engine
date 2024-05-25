package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.willCollideWithObject;

public class Game {
    private Player player;
    private Level currLevel;

    private int Width;
    private int Height;
    private int tileDimension;


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
        player.setXCoord(0);
        player.setYCoord(0);
        player.setWidth(playerWidth);
        player.setHeight(playerHeight);
        player.setHitBox(playerXOffset, playerYOffset);
        Height = h;
        Width = w;
        this.currLevel = new Level();
        //next couple lines only for testing
        BackgroundObject bush = new BackgroundObject();
        bush.setXCoord(50);
        bush.setYCoord(50);
        bush.setWidth(228);
        bush.setHeight(151);
        bush.setHitBox(bushXOffset, bushYOffset);
        currLevel.objectsInLevel.add(bush);
        currLevel.loadLevelFromJson(1);
    }

    public void update() {
        if (!willCollideWithObject(player, this.currLevel.objectsInLevel.getFirst() , player.getCurrDirection())) {
            player.move(player.getCurrDirection(), Width, Height, tileDimension);
        }

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
}
