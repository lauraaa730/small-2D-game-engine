package cz.cvut.fel.pjv.dudkolau.Model;

/*
* This will save current game data
* to a json file.
* For example: current position,
* puzzles completed etc..
*/
public class GameData {
    private int totalLevelNum;

    public GameData() {
    }


    public int getTotalLevelNum() {
        return totalLevelNum;
    }

    public void setTotalLevelNum(int totalLevelNum) {
        this.totalLevelNum = totalLevelNum;
    }
}
