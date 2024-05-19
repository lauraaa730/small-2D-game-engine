package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;

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
        Height = h;
        Width = w;
        this.currLevel = new Level();
        //next couple lines only for testing
        BackgroundObject bush = new BackgroundObject();
        bush.setXCoord(50);
        bush.setYCoord(50);
        currLevel.objectsInLevel.add(bush);
        currLevel.loadLevelFromJson(1);
    }

    public void update() {
        if (!isThereObstacle(player.getCurrDirection(), player.getXCoord(), player.getYCoord())) {
            player.move(player.getCurrDirection(), Width, Height, tileDimension);
        }

    }

    private boolean isThereObstacle(Directions d, int x, int y) {
        //this works really weird
        //TODO fix it
        boolean ret = false;
        for (GameObject gameObject : currLevel.objectsInLevel ) {
            if (d == Directions.LEFT) {
                if (gameObject.getXCoord()== x-1 && gameObject.getYCoord() == y) {
                    ret = true;
                    break;
                }
            } else if (d == Directions.RIGHT) {
                if (gameObject.getXCoord()== x+1 && gameObject.getYCoord() == y) {
                    ret = true;
                    break;
                }
            } else if (d == Directions.UP) {
                if (gameObject.getXCoord()== y-1 && gameObject.getXCoord() == x) {
                    ret = true;
                    break;
                }
            } else if (d == Directions.DOWN) {
                if (gameObject.getXCoord()== y+1 && gameObject.getXCoord() == x) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
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
