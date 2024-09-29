package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.pjv.dudkolau.Model.Views;


/*
* This will save current game data
* to a json file.
* For example: current position,
* puzzles completed etc..
*/
public class GameData {


    private int totalLevelNum;
    @JsonView(Views.SaveGameView.class)
    private int currPlayerX;
    @JsonView(Views.SaveGameView.class)
    private int currPlayerY;
    private int currPlayerLevel;


    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    //@JsonView(Views.SaveGameView.class)
    private List<Level> levels;

    public int getCurrPlayerX() {
        return currPlayerX;
    }

    public void setCurrPlayerX(int currPlayerX) {
        this.currPlayerX = currPlayerX;
    }

    public int getCurrPlayerY() {
        return currPlayerY;
    }

    public void setCurrPlayerY(int currPlayerY) {
        this.currPlayerY = currPlayerY;
    }

    public int getCurrPlayerLevel() {
        return currPlayerLevel;
    }

    public void setCurrPlayerLevel(int currPlayerLevel) {
        this.currPlayerLevel = currPlayerLevel;
    }

    public GameData() {
    }




    public int getTotalLevelNum() {
        return totalLevelNum;
    }

    public void setTotalLevelNum(int totalLevelNum) {
        this.totalLevelNum = totalLevelNum;
    }
}
