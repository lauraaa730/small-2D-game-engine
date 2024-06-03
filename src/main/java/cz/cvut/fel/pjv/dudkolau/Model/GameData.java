package cz.cvut.fel.pjv.dudkolau.Model;

import java.util.ArrayList;
import java.util.List;

/*
* This will save current game data
* to a json file.
* For example: current position,
* puzzles completed etc..
*/
public class GameData {
    private int totalLevelNum;
    private List<Level> levels = new ArrayList<>();

    public GameData() {
    }


    public int getTotalLevelNum() {
        return totalLevelNum;
    }

    public void setTotalLevelNum(int totalLevelNum) {
        this.totalLevelNum = totalLevelNum;
    }
}
