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

    private int tileDimension;
    private boolean isPaused;
    private int levelsNum = 0;
    private int enemieMoveCounter = 0;
    private boolean mainMenuOn;
    private int invincibilityTimer = 0; //counter that provides invincibility for player after getting hit
    private int interactingTimer = 0;

    private int inviPotionCountDown = 0;
    private int damagePotionCountDown = 0;

    private int attackTimer = 0;

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

        updateTimers();
        player.updateAttackHitbox();

        GameObject currentObject;
        //move the player***************************************************
        player.move(player.getCurrDirection(), width, height, tileDimension);
        for (int i = 0; i < currLevel.getBackgroundObjectsNum() ; i++) {
            currentObject = this.currLevel.getBackgroundObjects().get(i);
            if (checkCollisionWithObject(player,currentObject)) {
                player.jumpBack(true,width, height);
            }
        }

        Button currentButton;
        for (int i = 0; i < currLevel.getButtonsNum() ; i++) {
            currentButton = this.currLevel.getButtons().get(i);
            if (checkCollisionWithObject(player,currentButton)) {
                currentButton.setPressed(true);
                if (!currentButton.isFake()) {
                    for (int j = 0; j<currLevel.getDoorsNum(); j++) {
                        currLevel.getDoors().get(i).setLocked(false);
                    }
                }

            }
        }

        //Check and use doors **********************************************************
        Door currDoor;
        //TODO also up and down doors
        for (int i = 0; i < currLevel.getDoorsNum() ; i++) {
            currDoor = this.currLevel.getDoors().get(i);
            if (checkCollisionWithObject(player,currDoor)) {
                player.jumpBack(true,width, height);
            }
            // Check if player is interacting with an object
            if ( !currDoor.isLocked() && interactingTimer==0 && player.isInteracting() && isPlayerFacingObject(currDoor)) {
                interactingTimer = 1;
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
                    for (int j = 0; j < this.currLevel.getPotionsNum(); j++) {
                        this.currLevel.getPotions().get(j).setHitBox(0, 0);
                    }
                    for (int j = 0; j < this.currLevel.getButtonsNum(); j++) {
                        this.currLevel.getButtons().get(j).setHitBox(0, 0);
                    }
                    System.out.println(width/tileDimension);
                    System.out.println(currDoor.getWidth());
                    player.setxCoord(width/tileDimension - currDoor.getxCoord());
                    if (player.getxCoord() >=width/tileDimension-player.getWidth()/tileDimension) {
                        player.setxCoord((width-player.getWidth())/tileDimension-5); //HOW DOES THIS WORK???
                    }
                    player.setHitBox(playerXOffset,playerYOffset);
                    return;
                }
            }
        }

        managePotions();

        updateEnemies();
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
        player.setAttackHitBox();
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
            this.currLevel.getEnemies().get(i).setCurrHealth(this.currLevel.getEnemies().get(i).getMaxHealth());
        }
        for (int i = 0; i < this.currLevel.getPotionsNum(); i++) {
            this.currLevel.getPotions().get(i).setHitBox(0, 0);
        }
        for (int i = 0; i < this.currLevel.getButtonsNum(); i++) {
            this.currLevel.getButtons().get(i).setHitBox(0, 0);
        }
    }


    public Level getCurrLevel() {
        return currLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDamagePotionCountDown() {
        return damagePotionCountDown;
    }

    public List<GameObject> getGameObjects() {
        //TODO rename to getbackgroundobjects
        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.addAll(this.currLevel.getBackgroundObjects());
        gameObjects.addAll(this.currLevel.getDoors());
        gameObjects.addAll(this.currLevel.getPotions());
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

    private void updateTimers() {
        if (invincibilityTimer > 0) {
            invincibilityTimer++;
            if (invincibilityTimer>= invincibilityCooldown) {
                invincibilityTimer=0;
            }
        }

        if (interactingTimer > 0) {
            interactingTimer++;
            if (interactingTimer>= interactingCooldown) {
                interactingTimer=0;
            }
        }

        if (attackTimer > 0) {
            attackTimer++;
            if (attackTimer>= attackCooldown) {
                attackTimer=0;
            }
        }


        if (inviPotionCountDown > 0) {
            player.setInvincible(true);
            inviPotionCountDown--;
        } else {
            player.setInvincible(false);
        }

        if (damagePotionCountDown > 0) {
            damagePotionCountDown--;
        } else {
            player.setDamage(playerDefaultDamage);

        }
    }

    public void managePotions(){
        Potion currPotion;
        //System.out.println("Current health: "+ player.getCurrHealth());
        for (int i = 0; i < currLevel.getPotionsNum() ; i++) {
            currPotion = this.currLevel.getPotions().get(i);
            if (checkCollisionWithObject(player, currPotion)) {
                player.jumpBack(true, width, height);
            }
            // Check if player is interacting with an object
            if (interactingTimer == 0 && player.isInteracting() && isPlayerFacingObject(currPotion)) {
                interactingTimer = 1;
                if (currPotion.getEffect()== Effect.HEALTH) {
                    if (player.getMaxHealth() >= player.getCurrHealth() + currPotion.getHealthAdd()) {
                        System.out.println("HEALING!");
                        player.setCurrHealth(player.getCurrHealth() + currPotion.getHealthAdd());
                    }
                } else if (currPotion.getEffect() == Effect.INVINCIBILITY) {
                    if (inviPotionCountDown < currPotion.getEffectDuration()) {
                        inviPotionCountDown = currPotion.getEffectDuration();
                    }
                } else if (currPotion.getEffect() == Effect.DAMAGE) {
                    damagePotionCountDown = currPotion.getEffectDuration();
                    player.setDamage(player.getDamage()+currPotion.getDamageModifier());
                }

                /* Remove the potion from the level*/
                currLevel.getPotions().remove(i);
                //TODO zkontrolovat jestli se to nesmaze jen lokalne, is it really pointer? --ale asi dobry
                currLevel.setPotionsNum(currLevel.getPotions().size());

                //TODO remove potion after consumption


            }
        }
    }

    private void updateEnemies() {
        Enemy e;
        for (int i = 0; i < currLevel.getEnemiesNum() ; i++) {
            e=currLevel.getEnemies().get(i);

            if ( player.isFighting() && attackTimer <= 0 && player.getAttackHitBox().getRectangle().intersects(e.getHitBox().getRectangle())) {
                e.setCurrHealth(e.getCurrHealth()-player.getDamage());
                System.out.printf("Enemy health : %d\n", e.getCurrHealth());
                attackTimer = 1;
                if (e.getCurrHealth()<=0) {
                    currLevel.getEnemies().remove(i);
                    currLevel.setEnemiesNum(getCurrLevel().getEnemies().size());
                    continue;
                }
            }

            if (checkCollisionWithEntity(player,e)) {
                //System.out.println(player.isInvincible());
                //These two invincibility timers are CORRECTLY divided into two, two seperate things (for rendering icons)
                if (invincibilityTimer == 0 && !player.isInvincible()){
                    invincibilityTimer = 1; //start the counter

                    player.setCurrHealth(player.getCurrHealth()-1); //popr max(0, get curr health)
                    System.out.println("OUCH!");
                    if (player.getCurrHealth() <=0) {
                        System.out.println("GAME OVER");
                        System.exit(0);
                    }
                }
                if (e.isHasCollision()) {
                    player.jumpBack(true,width, height);
                }

            }
            if (enemieMoveCounter !=2) { //slowing down enemies
                if ( player.isFighting() && attackTimer <= 0 && player.getAttackHitBox().getRectangle().intersects(e.getHitBox().getRectangle())) {
                    e.setCurrHealth(e.getCurrHealth()-player.getDamage());
                    System.out.printf("Enemy health : %d\n", e.getCurrHealth());
                    attackTimer = 1;
                    if (e.getCurrHealth()<=0) {
                        currLevel.getEnemies().remove(i);
                        currLevel.setEnemiesNum(getCurrLevel().getEnemies().size());
                        continue;
                    }
                }
                if (e.getSelfMovementPosition()>=enemyMovementLength) {
                    e.setCurrDirection(Directions.LEFT);
                } else if (e.getSelfMovementPosition()<=0) {
                    e.setCurrDirection(Directions.RIGHT);
                }
                e.updateSelfMovementPosition();
                e.move(e.getCurrDirection(), width, height, tileDimension);
                //TODO aby se vcas odrazel od sten? + maybe ruzny damage od enemy
                if (checkCollisionWithEntity(player,e)) {
                    if (invincibilityTimer == 0 && !player.isInvincible()) {
                        invincibilityTimer = 1; //start the counter
                        player.setCurrHealth(player.getCurrHealth()-1); //popr max(0, get curr health)
                        System.out.println("OUCH 2!");
                        if (player.getCurrHealth() <=0) {
                            System.out.println("GAME OVER");
                            System.exit(0);
                        }
                    }
                    if(e.isHasCollision()) {
                        e.jumpBack(width, height);
                        if  (e.getCurrDirection()==Directions.LEFT) {
                            e.setSelfMovementPosition(0);
                            e.setCurrDirection(Directions.RIGHT);
                        } else if (e.getCurrDirection() == Directions.RIGHT) {
                            e.setSelfMovementPosition(enemyMovementLength);
                            e.setCurrDirection(Directions.LEFT);
                        }
                    }

                }
                //TODO enemy by se mel pohybovat i vertikalne, pridat do jsonu movement jakej atd
            }
            enemieMoveCounter = (enemieMoveCounter+1)%3; // for speeding up or slowing down enemy movement

        }//*****************************************************************
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

        //TODO tuto cast kodu dat do jedne funkce
        for (int i = 0; i < this.currLevel.getBackgroundObjectsNum(); i++) {
            this.currLevel.getBackgroundObjects().get(i).setHitBox(bushXOffset, bushYOffset);
        }
        for (int i = 0; i < this.currLevel.getPotionsNum(); i++) {
            this.currLevel.getPotions().get(i).setHitBox(0, 0);
        }
        for (int i = 0; i < this.currLevel.getEnemiesNum(); i++) {
            System.out.println(currLevel.getEnemies().get(i).getCurrDirection());
            this.currLevel.getEnemies().get(i).setHitBox(0, 0);
        }
        for (int i = 0; i < this.currLevel.getDoorsNum(); i++) {
            this.currLevel.getDoors().get(i).setHitBox(bushXOffset, bushYOffset);
        }
        for (int i = 0; i < this.currLevel.getButtonsNum(); i++) {
            this.currLevel.getButtons().get(i).setHitBox(0, 0);
        }// END of TODO

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
