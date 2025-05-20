package cz.cvut.fel.pjv.dudkolau.Model;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.checkCollisionWithEntity;
import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.checkCollisionWithObject;
import static cz.cvut.fel.pjv.dudkolau.Model.Directions.*;

/**
 * Stores and manages all game logic and model.
 * Manages levels, objects in levels,player, loading and saving.
 * Can work independently of GUI.
 *
 * Created for B0B36PJV
 * @author  dudkolau@fel.cvut.cz
 */
public class Game {
    private final static System.Logger logger = System.getLogger(Game.class.getName());
    private Player player;
    private Level currLevel;
    private int maxLevelNum;
    private int tileDimension;
    private boolean isPaused;
    private boolean running;
    private boolean gameOverMenu;
    private int levelsNum = 0;
    private int enemieMoveCounter = 0;
    private boolean mainMenuOn;

    /*counter that provides invincibility for player after getting hit*/
    private int invincibilityTimer = 0;
    private int interactingTimer = 0;
    private int attackTimer = 0;

    private int inviPotionCountDown = 0;
    private int damagePotionCountDown = 0;

    private List<Level> levels = new ArrayList<>();

    public Game() {
        this.mainMenuOn = true;
        this.running = false;
        this.gameOverMenu = false;
    }

    public void startGame(boolean newGame) {
        this.running = true;
        this.mainMenuOn = false;
        this.gameOverMenu = false;
        this.isPaused = false;
        if (newGame) {
            startNewGame();
        } else {
            loadSavedGame();
        }
    }

    public void startNewGame() {
        logger.log(System.Logger.Level.INFO, "started new game");
        this.player = new Player();
        resetGame();

        this.levels = new ArrayList<>();
        loadAllLevels();
        currLevel = levels.getFirst();
        if (currLevel==null) {
            logger.log(System.Logger.Level.INFO,"Level1 is null!");
            System.exit(1);
        }

        setObjects();
    }

    public void update() {
        updateTimers();

        //Move player
        player.move(player.getCurrDirection(), width, height, tileDimension);
        player.updateAttackHitbox();

        if (enterNewLevel()) { return; }

        //Check collisions and interaction with level objects
        checkBackgroundObjects();

        checkGameButtons();

        managePotions();

        updateEnemies();
    }

    public boolean isPlayerFacingObject(GameObject object) {
        //Fake movement to check possible collision, then jumpback
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

    /**
     * Check all doors, manage collision, interaction and level change
     *
     * <p>This method checks for collisions between the player and doors. If a collision occurs,
     * the player is moved back. It then verifies whether the player is interacting with an unlocked door
     * and is facing it. If the conditions are met, the method changes the current level
     * based on the door's configuration and adjusts the player's coordinates accordingly.
     *
     * @return true if the level change happened, false otherwise.
     */
    private boolean enterNewLevel() {
        Door currDoor;
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
                    logger.log(System.Logger.Level.INFO, "Player is entering level " +currDoor.getLevel2());
                    setObjects();


                    //Manage player coordination in new level
                    if (currDoor.getDir()==LEFT || currDoor.getDir()==RIGHT) {
                        player.setxCoord(width/tileDimension - currDoor.getxCoord());
                        if (player.getxCoord() >=width/tileDimension-player.getWidth()/tileDimension) {
                            player.setxCoord((width-player.getWidth())/tileDimension-5);
                        }
                    } else if (currDoor.getDir() == UP || currDoor.getDir()==DOWN) {
                        player.setyCoord(height/tileDimension - currDoor.getyCoord());
                        if (player.getyCoord() >=height/tileDimension-player.getHeight()/tileDimension) {
                            player.setyCoord((height-player.getHeight())/tileDimension-5);
                        }
                    }
                    player.setHitBox(player.getxOffset(), player.getyOffset());
                    return true;
                }
            }
        }
        return false;
    }

    private void managePotions(){
        Potion currPotion;
        for (int i = 0; i < currLevel.getPotionsNum() ; i++) {
            currPotion = this.currLevel.getPotions().get(i);
            if (checkCollisionWithObject(player, currPotion)) {
                player.jumpBack(true, width, height);
            }

            //Check if player is interacting with potion
            if (interactingTimer == 0 && player.isInteracting() && isPlayerFacingObject(currPotion)) {
                //Start countdown
                interactingTimer = 1;

                if (currPotion.getEffect()== Effect.HEALTH) {
                    if (player.getMaxHealth() >= player.getCurrHealth() + currPotion.getHealthAdd()) {
                        logger.log(System.Logger.Level.INFO, "Player was healed for " + currPotion.getHealthAdd() + " hearts.");
                        player.setCurrHealth(player.getCurrHealth() + currPotion.getHealthAdd());
                    }
                } else if (currPotion.getEffect() == Effect.INVINCIBILITY) {
                    if (inviPotionCountDown < currPotion.getEffectDuration()) {
                        inviPotionCountDown = currPotion.getEffectDuration();
                    }
                    logger.log(System.Logger.Level.INFO, "Player is now invincible");
                    player.setInvincible(true);
                } else if (currPotion.getEffect() == Effect.DAMAGE) {
                     if (damagePotionCountDown < currPotion.getEffectDuration()) {
                         damagePotionCountDown = currPotion.getEffectDuration();
                     }
                    player.setDamage(player.getDamage()+currPotion.getDamageModifier());
                    logger.log(System.Logger.Level.INFO, "Player's damage  was increased by: "+currPotion.getDamageModifier());
                }

                /* Remove the potion from the level*/
                currLevel.getPotions().remove(i);
                logger.log(System.Logger.Level.INFO, "Potion was consumed by player.");
                currLevel.setPotionsNum(currLevel.getPotions().size());
            }
        }
    }

    private void checkBackgroundObjects() {
        GameObject currentObject;
        for (int i = 0; i < currLevel.getBackgroundObjectsNum() ; i++) {
            currentObject = this.currLevel.getBackgroundObjects().get(i);
            if (checkCollisionWithObject(player,currentObject)) {
                player.jumpBack(true,width, height);
            }
        }
    }

    private void checkGameButtons() {
        GameButton currentGameButton;
        for (int i = 0; i < currLevel.getGameButtonsNum() ; i++) {
            currentGameButton = this.currLevel.getGameButtons().get(i);
            if (checkCollisionWithObject(player, currentGameButton)) {
                currentGameButton.setPressed(true);
                if (!currentGameButton.isFake() && !currLevel.isDoorsWereUnlocked()) {
                    for (int j = 0; j<currLevel.getDoorsNum(); j++) {
                        currLevel.getDoors().get(j).setLocked(false);
                    }
                    currLevel.setDoorsWereUnlocked(true);
                    logger.log(System.Logger.Level.INFO, "All doors in this level now unlocked!");
                }
            }
        }
    }

    private void updateEnemies() {
        Enemy e;
        for (int i = 0; i < currLevel.getEnemiesNum() ; i++) {
            e=currLevel.getEnemies().get(i);
            attack(e,i);
            if (checkCollisionWithEntity(player,e)) {
                checkEnemyAttack();
                if (e.isHasCollision()) {
                    player.jumpBack(true,width, height);
                }
            }

            //Movement
            if (enemieMoveCounter != enemyMovementModifier) { //slowing down enemies
                attack(e,i);

                //Cycle movement update
                if (e.getSelfMovementPosition()>=enemyMovementLength) {
                    e.setCurrDirection(LEFT);
                } else if (e.getSelfMovementPosition()<=0) {
                    e.setCurrDirection(RIGHT);
                }
                e.updateSelfMovementPosition();
                e.move(e.getCurrDirection(), width, height, tileDimension);

                //Enemy collision and attack
                if (checkCollisionWithEntity(player,e)) {
                    checkEnemyAttack();
                    if(e.isHasCollision()) {
                        e.jumpBack(width, height);
                        if  (e.getCurrDirection()==LEFT) {
                            e.setSelfMovementPosition(0);
                            e.setCurrDirection(RIGHT);
                        } else if (e.getCurrDirection() == RIGHT) {
                            e.setSelfMovementPosition(enemyMovementLength);
                            e.setCurrDirection(LEFT);
                        }
                    }
                }
            }
            // for speeding up or slowing down enemy movement
            enemieMoveCounter = (enemieMoveCounter+1)%3;
        }
    }

    private void attack(Enemy e,int i) {
        if ( player.isFighting() && attackTimer <= 0 && player.getAttackHitBox().getRectangle().intersects(e.getHitBox().getRectangle())) {
            e.setCurrHealth(e.getCurrHealth()-player.getDamage());
            logger.log(System.Logger.Level.INFO, "Player hit enemy!");
            attackTimer = 1;
            if (e.getCurrHealth()<=0) {
                currLevel.getEnemies().remove(i);
                currLevel.setEnemiesNum(currLevel.getEnemies().size());
                logger.log(System.Logger.Level.INFO, "Player killed an enemy!");
            }
        }
    }

    private void checkEnemyAttack() {
        if (invincibilityTimer == 0 && !player.isInvincible()) {
            invincibilityTimer = 1; //start the counter
            player.setCurrHealth(player.getCurrHealth()-1);
            logger.log(System.Logger.Level.INFO,"Player got hit by enemy! Current health has decreased.");
            if (player.getCurrHealth() <=0) {
                logger.log(System.Logger.Level.INFO, "Player died, game over...");
                this.running = false;
                this.gameOverMenu = true;
            }
        }
    }

    private void setObjects() {
        for (int i = 0; i < this.currLevel.getBackgroundObjectsNum(); i++) {
            this.currLevel.getBackgroundObjects().get(i).setHitBox(bushXOffset, bushYOffset);
        }
        for (int i = 0; i < this.currLevel.getPotionsNum(); i++) {
            this.currLevel.getPotions().get(i).setHitBox(0, 0);
        }
        for (int i = 0; i < this.currLevel.getEnemiesNum(); i++) {
            Enemy e = this.currLevel.getEnemies().get(i);
            e.setCurrHealth(e.getMaxHealth());
            e.setHitBox(0, 0);
        }
        for (int i = 0; i < this.currLevel.getDoorsNum(); i++) {
            this.currLevel.getDoors().get(i).setHitBox(0,0);
        }
        for (int i = 0; i < this.currLevel.getGameButtonsNum(); i++) {
            this.currLevel.getGameButtons().get(i).setHitBox(buttonXOffset, buttonYOffset);
        }
    }

    // Saving and loading functions --------------------------------------------------------

    private void resetGame() {
        logger.log(System.Logger.Level.INFO, "Resetting cooldowns an player stats");
        this.inviPotionCountDown = 0;
        this.damagePotionCountDown = 0;
        this.attackTimer = 0;
        this.invincibilityTimer = 0;
        this.interactingTimer = 0;

        this.player = new Player();
        player.setInteracting(false);
        player.setFighting(false);
        player.setInvincible(false);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            WorldConfig worldConfig = objectMapper.readerWithView(Views.gameView.class).readValue(
                    new File("saves/game-world-info.json" ), WorldConfig.class
            );

            this.maxLevelNum = worldConfig.totalLevelsNum;
            this.levelsNum = 0;

            player.setWidth(worldConfig.playerWidth);
            player.setHeight(worldConfig.playerHeight);
            player.setxOffset(worldConfig.playerXOffset);
            player.setyOffset(worldConfig.playerYOffset);
            player.setMaxHealth(worldConfig.playerMaxHealth);
            player.setCurrHealth(player.getMaxHealth());
            player.setxCoord(worldConfig.playerStartX);
            player.setyCoord(worldConfig.playerStartY);

            player.setHitBox(worldConfig.playerXOffset, worldConfig.playerYOffset);
            player.setAttackHitBox();

        } catch (IOException ex) {
            logger.log(System.Logger.Level.INFO,"Something went wrong with json");
        }
    }

    public void loadSavedGame() {
        try {
            logger.log(System.Logger.Level.INFO, "Loading saved game...");
            ObjectMapper objectMapper = new ObjectMapper();

            resetGame();

            GameData gameData = objectMapper.readerWithView(Views.gameView.class).readValue(
                    new File("saves/current-game-state.json" ), GameData.class
            );

            levels = gameData.getLevels();
            for (Level level : levels) {
                if (level.getLevelType() == gameData.getCurrPlayerLevel()) {
                    currLevel = level;
                    break;
                }
            }

            this.levelsNum =gameData.getTotalLevelNum();

            this.player.setMaxHealth(gameData.getMaxPlayerHealth());
            this.player.setCurrHealth(gameData.getCurrPlayerHealth());
            this.player.setxCoord(gameData.getCurrPlayerX());
            this.player.setyCoord(gameData.getCurrPlayerY());
            this.player.setHitBox(player.getxOffset(),player.getyOffset());
            this.player.setAttackHitBox();

            this.inviPotionCountDown = gameData.getInviPotionTimer();
            this.damagePotionCountDown = gameData.getDamagePotionTimer();

            setObjects();
            logger.log(System.Logger.Level.INFO,"Loaded saved game successfully!");

        } catch (IOException ex) {
            logger.log(System.Logger.Level.INFO,"Something went wrong with json");
        }
    }

    private Level loadLevelFromJson(String levelName){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("saves/" + levelName);

            Level levelToRead = objectMapper.readerWithView(Views.gameView.class).readValue(jsonFile, Level.class);
            logger.log(System.Logger.Level.INFO,"Loaded level" + levelName);
            return levelToRead;
        } catch (IOException ex) {
            logger.log(System.Logger.Level.INFO,"Something went wrong with json");
        }
        return null;
    }

    private void loadAllLevels() {
        logger.log(System.Logger.Level.INFO, "Loading all levels for new game!");
        Level l;
        for (int i = 0; i < maxLevelNum; i++) {
            l = loadLevelFromJson("level"+(i+1)+".json");
            if (l!=null) {
                levels.add(l);
                l.setDoorsWereUnlocked(false);
                levelsNum++;
            } else {
                logger.log(System.Logger.Level.INFO,"Level number " +(i+1) + " is empty!");
            }
        }
    }

    public void saveGame() {
        logger.log(System.Logger.Level.INFO, "Saving game...");
        GameData gameData = new GameData();

        gameData.setCurrPlayerHealth(this.player.getCurrHealth());
        gameData.setMaxPlayerHealth(this.player.getMaxHealth());
        gameData.setCurrPlayerLevel(currLevel.getLevelType());
        gameData.setCurrPlayerX(this.player.getxCoord());
        gameData.setCurrPlayerY(this.player.getyCoord());
        gameData.setTotalLevelNum(levelsNum);
        gameData.setDamagePotionTimer(damagePotionCountDown);
        gameData.setInviPotionTimer(inviPotionCountDown);

        gameData.setLevels(levels);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithView(Views.gameView.class).writeValue(
                    new File("saves/current-game-state.json" ), gameData
            );
            logger.log(System.Logger.Level.INFO,"game saved succesfully");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            logger.log(System.Logger.Level.INFO,"Something went wrong with json");
        }
    } /* ----------------------------------------------------------------------------- */


    //Setters and Getters
    public boolean isPaused() {
        return isPaused;
    }

    public boolean isMainMenuOn() {
        return mainMenuOn;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isGameOverMenu() {
        return gameOverMenu;
    }

    public void setMainMenuOn(boolean mainMenuOn) {
        this.mainMenuOn = mainMenuOn;
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

    public List<GameObject> getGameObjects() {
        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.addAll(this.currLevel.getGameButtons());
        gameObjects.addAll(this.currLevel.getDoors());
        gameObjects.addAll(this.currLevel.getPotions());
        gameObjects.addAll(this.currLevel.getBackgroundObjects());
        return gameObjects;
    }

    /** For testing*/
    public void setInteractingTimer(int t) {
        this.interactingTimer = t;
    }

    public void setInviPotionCountDown(int inviPotionCountDown) {
        this.inviPotionCountDown = inviPotionCountDown;
    }

    public void setDamagePotionCountDown(int damagePotionCountDown) {
        this.damagePotionCountDown = damagePotionCountDown;
    }
}
