package cz.cvut.fel.pjv.dudkolau.Model;

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
        currLevel.loadLevelFromJson(1);
    }

    public void update() {
        player.move(player.getCurrDirection(), Width, Height);
    }

    private boolean isThereObstacle(Directions d) {
        for (GameObject gameObject : currLevel.backgroundObjects ) {

        }
        return false;
    }
    public Level getCurrLevel() {
        return currLevel;
    }

    public Player getPlayer() {
        return player;
    }
}
