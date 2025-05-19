package cz.cvut.fel.pjv.dudkolau;

import cz.cvut.fel.pjv.dudkolau.Model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;
import static cz.cvut.fel.pjv.dudkolau.Graphics.*;

public class Main extends Application {
    private final static System.Logger logger = System.getLogger(Game.class.getName());
    private final Game game = new Game();
    private Image[] currPlayerImages = animSTAND_RIGHT;
    private Image currFightImage = fightLeftImage;
    private Image currSplashImage = splashLeftImage;
    private  Directions lastDirection = Directions.NONE;
    private int animationCounter = 0;
    private boolean pausedMenuButtonsAdded = false;
    private boolean mainMenuButtonsAdded = false;
    private int currSplashXOffset = splashOffset;
    private int currSplashYOffset = splashOffset;
    private boolean showHitBoxes = false;

    //Declaring all buttons ---------------------------
    private Button continueButton = new Button();
    private Button exitGameButton = new Button();
    private Button saveGameButton = new Button();
    private Button loadSavedButton = new Button();
    private Button startNewButton = new Button();
    private Button menuButton = new Button();


    @Override
    public void start(Stage stage) {
        logger.log(System.Logger.Level.INFO, "starting game...");
        stage.setResizable(false);
        stage.setTitle("Beyond the Forest");

        //Setting up scene
        logger.log(System.Logger.Level.INFO, "Setting up scene...");
        game.setTileDimension(tileDimension);
        Canvas canvas = new Canvas(game.getWidth(), game.getHeight());
        //using anchorpane instead of stackpane allows moving buttons more freely
        AnchorPane pane = new AnchorPane(canvas);
        Scene scene = new Scene(pane, game.getWidth(), game.getHeight());


        //Initializing buttons
        loadButtons();
        continueButton.setFocusTraversable(false);
        saveGameButton.setFocusTraversable(false);
        menuButton.setFocusTraversable(false);
        loadSavedButton.setFocusTraversable(false);
        exitGameButton.setFocusTraversable(false);
        startNewButton.setFocusTraversable(false);


        //MAIN GAME LOOP -----------------------------------------------------
        AnimationTimer timer = new AnimationTimer() {
            long lastCall;
            @Override
            public void handle(long l) {
                if ((l - lastCall) >= 25_000_000) {
                        //GAMEPLAY
                    if (game.isRunning()) {
                        updateRunningGame(pane,canvas);
                        // PAUSED MENU
                    } else if (game.isPaused()){
                        updatePausedGame(pane, canvas);
                        //MAIN MENU
                    } else if (game.isMainMenuOn()) {
                        updateMainMenu(pane,canvas);
                        //GAME OVER
                    } else if (game.isGameOverMenu()) {
                        updateGameOverMenu(pane,canvas);
                    }

                    lastCall = l;
                    if (animationCounter>= 2147483600) { //so the counter doesn't overflow
                        animationCounter=0;
                    }
                }
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();
        // -----------------------------------------------------------------------------------------

        //Event Handlers ----------------------------------------------------
        scene.setOnKeyPressed(keyEvent -> {
            try {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    game.setPaused(!game.isPaused());
                    game.setRunning(!game.isRunning());
                }
                if (keyEvent.getCode() == KeyCode.A) {
                    game.getPlayer().setLastDirection(game.getPlayer().getCurrDirection());
                    game.getPlayer().setCurrDirection(Directions.LEFT);
                } else if (keyEvent.getCode() == KeyCode.D) {
                    game.getPlayer().setLastDirection(game.getPlayer().getCurrDirection());
                    game.getPlayer().setCurrDirection(Directions.RIGHT);
                } else if (keyEvent.getCode() == KeyCode.S) {
                    game.getPlayer().setLastDirection(game.getPlayer().getCurrDirection());
                    game.getPlayer().setCurrDirection(Directions.DOWN);
                } else if (keyEvent.getCode() == KeyCode.W) {
                    game.getPlayer().setLastDirection(game.getPlayer().getCurrDirection());
                    game.getPlayer().setCurrDirection(Directions.UP);
                } else if (keyEvent.getCode() == KeyCode.E) {
                    game.getPlayer().setInteracting(true);
                } else if (keyEvent.getCode() == KeyCode.SPACE) {
                    game.getPlayer().setFighting(true);
                }
            } catch (Exception e) {
                e.getCause();
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            try {
                if (keyEvent.getCode() == KeyCode.A ||
                        keyEvent.getCode() == KeyCode.D ||keyEvent.getCode() == KeyCode.W ||
                        keyEvent.getCode() == KeyCode.S) {
                    game.getPlayer().setLastDirection(game.getPlayer().getCurrDirection());
                    game.getPlayer().setCurrDirection(Directions.NONE);
                } else if (keyEvent.getCode() == KeyCode.E) {
                    game.getPlayer().setInteracting(false);
                } else if (keyEvent.getCode() == KeyCode.SPACE) {
                    game.getPlayer().setFighting(false);
                }
            } catch (Exception e) {
                e.getCause();
            }
        });

        continueButton.setOnAction(event -> {
            game.setPaused(!game.isPaused());
            game.setRunning(!game.isRunning());
        });

        exitGameButton.setOnAction(event -> {
            logger.log(System.Logger.Level.INFO, "Exiting...");
            System.exit(0);
        });

        saveGameButton.setOnAction(event -> game.saveGame());

        loadSavedButton.setOnAction(event -> game.startGame(false));

        menuButton.setOnAction(event -> {
            game.setPaused(false);
            game.setRunning(false);
            game.setMainMenuOn(true);
        });

        startNewButton.setOnAction(event -> game.startGame(true));
    }

    private void updateRunningGame(AnchorPane pane, Canvas canvas) {
        game.update();
        setCurrPlayerImages();
        if (pausedMenuButtonsAdded) {
            logger.log(System.Logger.Level.INFO, "Player unpaused game...");
            pane.getChildren().removeAll(menuButton, continueButton, saveGameButton);
            pausedMenuButtonsAdded = false;
        }
        if (mainMenuButtonsAdded) {
            logger.log(System.Logger.Level.INFO, "Player started game from main menu...");
            pane.getChildren().removeAll(exitGameButton,loadSavedButton,startNewButton);
            mainMenuButtonsAdded = false;
        }
        render(canvas);
        if (animationCounter%3==0) {
            animate();
        }
        animationCounter++;
    }

    private void updatePausedGame(AnchorPane pane, Canvas canvas) {
        if (!pausedMenuButtonsAdded) {
            loadButtons();
            logger.log(System.Logger.Level.INFO, "Player paused game...");
            pane.getChildren().addAll(menuButton, continueButton, saveGameButton);
            pausedMenuButtonsAdded = true;
        }
        render(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundPaused,0,0);
        gc.drawImage(pausedMenu, 155,110);
    }

    private void updateMainMenu(AnchorPane pane, Canvas canvas) {
        if (pausedMenuButtonsAdded) {
            pane.getChildren().removeAll(menuButton, continueButton, saveGameButton);
            pausedMenuButtonsAdded = false;
        }
        if (!mainMenuButtonsAdded) {
            loadButtons();
            logger.log(System.Logger.Level.INFO, "Player enetered main menu...");
            exitGameButton.setLayoutX(578);
            exitGameButton.setLayoutY(400);
            pane.getChildren().addAll(loadSavedButton, startNewButton, exitGameButton);
            mainMenuButtonsAdded = true;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(mainMenuBackground,0,0);
        gc.drawImage(ghostCycle[ghostCycleIndex],90,150);
        gc.drawImage(animSTAND_MENU[playerImageIndex], 220,280);
        if (animationCounter%3==0) {
            animate();
        }
        animationCounter++;
    }

    private void updateGameOverMenu(AnchorPane pane, Canvas canvas) {
        if (!mainMenuButtonsAdded) {
            exitGameButton.setLayoutX(450);
            exitGameButton.setLayoutY(400);
            startNewButton.setLayoutX(450);
            startNewButton.setLayoutY(260);
            loadSavedButton.setLayoutX(450);
            loadSavedButton.setLayoutY(330);
            pane.getChildren().addAll(loadSavedButton, startNewButton, exitGameButton);
            mainMenuButtonsAdded = true;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(gameOverBackground,0,0);
    }

    private void setCurrPlayerImages() {
        Directions currDir = game.getPlayer().getCurrDirection();
        if (currDir==Directions.NONE && lastDirection == Directions.RIGHT) {
            currPlayerImages = animSTAND_RIGHT;
            currFightImage = fightRightImage;
            currSplashImage = splashRightImage;
            lastDirection = Directions.NONE;

        } else if (currDir==Directions.NONE && lastDirection == Directions.LEFT) {
            currPlayerImages = animSTAND_LEFT;
            currFightImage = fightLeftImage;
            currSplashImage = splashLeftImage;
            lastDirection = Directions.NONE;

        }  else if (currDir==Directions.NONE && lastDirection == Directions.UP) {
            currPlayerImages = animSTAND_UP;
            currFightImage = animSTAND_UP[playerImageIndex];
            currSplashImage = splashUpImage;
            lastDirection = Directions.NONE;

        }  else if (currDir==Directions.NONE && lastDirection == Directions.DOWN) {
            currPlayerImages = animSTAND_DOWN;
            currFightImage = animSTAND_DOWN[playerImageIndex];
            currSplashImage = splashDownImage;
            lastDirection = Directions.NONE;

        } else if (currDir==Directions.RIGHT) {
            currPlayerImages = animRIGHT;
            currFightImage = fightRightImage;
            currSplashImage = splashRightImage;
            lastDirection = Directions.RIGHT;

        } else if (currDir==Directions.UP) {
            currPlayerImages = animUP;
            currFightImage = animUP[playerImageIndex];
            currSplashImage = splashUpImage;
            lastDirection = Directions.UP;

        } else if (currDir==Directions.DOWN) {
            currPlayerImages = animDOWN;
            currFightImage = animDOWN[playerImageIndex];
            currSplashImage = splashDownImage;
            lastDirection = Directions.DOWN;

        } else if (currDir==Directions.LEFT) {
            currPlayerImages = animLEFT;
            currFightImage = fightLeftImage;
            currSplashImage = splashLeftImage;
            lastDirection = Directions.LEFT;
        }
    }

    private void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(backgroundImage, 0, 0);

        boolean drewPlayer = false;
        int playerY = game.getPlayer().getHitBox().getyCoord();

        renderButtons(gc);

        renderDoors(gc);

        renderPotions(gc);

        renderBackgroundAndPlayer(playerY, gc, drewPlayer);

        renderEnemies(gc);

        renderHearts(gc);

        renderEffects(gc);

        renderHitboxes(gc);
    }

    private void renderEffects(GraphicsContext gc) {
        if (game.getPlayer().isInvincible()) {
            gc.drawImage(inviEffectImage, 5, 5);
        }
        if (game.getDamagePotionCountDown()>0) {
            gc.drawImage(dmgEffectImage, 50,5);
        }
    }

    private void renderHearts(GraphicsContext gc) {
        int currHealth = game.getPlayer().getCurrHealth();
        int maxHealth = game.getPlayer().getMaxHealth();
        for (int i = 0; i<currHealth;i++) {
            gc.drawImage(fullHeartImage, width-50*maxHealth + i*50,5);
        }
        for (int i = 0; i<(maxHealth-currHealth);i++) {
            gc.drawImage(emptyHeartImage,width-50*(maxHealth-currHealth)+ i*50, 5);
        }
    }

    private void renderEnemies(GraphicsContext gc) {
        for (Enemy enemy : game.getCurrLevel().getEnemies()) {
            gc.drawImage(enemyImage, enemy.getxCoord()*tileDimension, enemy.getyCoord()*tileDimension);
        }
    }

    /**
     * Render player
     * - Manage rendering order of visual objects
     * based on player location, makes player to
     * appear either infront of or behind the object.
     */
    private void renderBackgroundAndPlayer(int playerY, GraphicsContext gc, boolean drewPlayer) {
        int objectX;
        int objectY;
        BackgroundObject bo;
        for (int i = 0; i < game.getCurrLevel().getBackgroundObjects().size(); i++) {
            bo = game.getCurrLevel().getBackgroundObjects().get(i);
            objectY = bo.getyCoord();
            objectX = bo.getxCoord();

            if (playerY -tileDimension>objectY && playerY < objectY+bo.getHeight()/tileDimension) {
                gc.drawImage(new Image(bo.getImageName()),objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());
                renderPlayer(gc);
                drewPlayer = true;
            } else {
                if (!drewPlayer) {
                    renderPlayer(gc);
                    drewPlayer =true;
                }
                gc.drawImage(new Image(bo.getImageName()), objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());
            }
            if (!drewPlayer) {
                renderPlayer(gc);
            }
        }
    }

    private void renderPotions(GraphicsContext gc) {
        Potion currPotion;
        for (int i = 0; i < game.getCurrLevel().getPotionsNum(); i++) {
            currPotion = game.getCurrLevel().getPotions().get(i);
            gc.drawImage(new Image(currPotion.getImageName()), currPotion.getxCoord()* game.getTileDimension(),currPotion.getyCoord()* game.getTileDimension());
        }
    }

    private void renderDoors(GraphicsContext gc) {
        Door currDoor;
        for (int i = 0; i < game.getCurrLevel().getDoorsNum(); i++) {
            currDoor = game.getCurrLevel().getDoors().get(i);
            if (currDoor.isLocked()) {
                gc.drawImage(new Image(currDoor.getLockedImageName()), currDoor.getxCoord() * game.getTileDimension(), currDoor.getyCoord() * game.getTileDimension());
            } else {
                gc.drawImage(new Image(currDoor.getImageName()), currDoor.getxCoord() * game.getTileDimension(), currDoor.getyCoord() * game.getTileDimension());
            }
        }
    }

    private void renderButtons(GraphicsContext gc) {
        GameButton b;
        for (int i = 0; i < game.getCurrLevel().getGameButtonsNum(); i++) {
            b = game.getCurrLevel().getGameButtons().get(i);
            if (b.isPressed()) {
                gc.drawImage(gameButtonPressedImage, b.getxCoord() * game.getTileDimension(), b.getyCoord() * game.getTileDimension());
            } else {
                gc.drawImage(gameButtonImage, b.getxCoord() * game.getTileDimension(), b.getyCoord() * game.getTileDimension());
            }
        }
    }

    public void animate() {
        playerImageIndex = (playerImageIndex + 1) % currPlayerImages.length;
        if (game.isMainMenuOn()) {
            ghostCycleIndex =  (ghostCycleIndex + 1) % ghostCycle.length;
        }
    }

    private void drawRectangle(GraphicsContext gc, int x, int y, int w, int h){
        gc.setStroke(Color.RED);
        Rectangle rect = new Rectangle();
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2);
        gc.strokeRect(x*tileDimension, y*tileDimension, w, h);
    }

    private void renderPlayer(GraphicsContext gc) {
        Player player = game.getPlayer();
        if (currPlayerImages == animDOWN || currPlayerImages == animSTAND_DOWN) {
            currSplashYOffset = splashOffset;
            currSplashXOffset = 0;
        } else  if (currPlayerImages == animUP || currPlayerImages == animSTAND_UP) {
            currSplashYOffset = -splashOffset;
            currSplashXOffset = 0;
        } else if (currPlayerImages == animLEFT || currPlayerImages == animSTAND_LEFT) {
            currSplashYOffset = 0;
            currSplashXOffset = -splashOffset;
        } else if (currPlayerImages == animRIGHT || currPlayerImages == animSTAND_RIGHT) {
            currSplashYOffset = 0;
            currSplashXOffset = splashOffset;
        }

        if (player.isFighting()) {
            gc.drawImage(currFightImage, player.getxCoord() * game.getTileDimension(),
                    player.getyCoord() * game.getTileDimension());
            gc.drawImage(currSplashImage, player.getAttackHitBox().getxCoord() * game.getTileDimension()+currSplashXOffset,
                    player.getAttackHitBox().getyCoord() * game.getTileDimension()+currSplashYOffset);
        } else {
            gc.drawImage(currPlayerImages[playerImageIndex], player.getxCoord() * game.getTileDimension(),
                    player.getyCoord() * game.getTileDimension());
        }
    }

    private void renderHitboxes(GraphicsContext gc) {
        //Render hitboxes if needed
        if (showHitBoxes) {
            for (GameObject gameObject : game.getGameObjects()) {
                drawRectangle(gc, gameObject.getHitBox().getxCoord(), gameObject.getHitBox().getyCoord(), gameObject.getHitBox().getWidth(), gameObject.getHitBox().getHeight());
            }
            for (Enemy enemy : game.getCurrLevel().getEnemies()) {
                drawRectangle(gc, enemy.getHitBox().getxCoord(), enemy.getHitBox().getyCoord(), enemy.getHitBox().getWidth(), enemy.getHitBox().getHeight());
            }
            drawRectangle(gc, game.getPlayer().getHitBox().getxCoord(), game.getPlayer().getHitBox().getyCoord(), game.getPlayer().getHitBox().getWidth(), game.getPlayer().getHitBox().getHeight());
            drawRectangle(gc, game.getPlayer().getAttackHitBox().getxCoord(), game.getPlayer().getAttackHitBox().getyCoord(), game.getPlayer().getAttackHitBox().getWidth(), game.getPlayer().getAttackHitBox().getHeight());
        }
    }

    private void loadButtons() {
        continueButton.setGraphic(new ImageView(continueImage));
        continueButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        continueButton.setBackground(Background.fill(Color.TRANSPARENT));
        continueButton.setLayoutX(180);
        continueButton.setLayoutY(370);

        menuButton.setGraphic(new ImageView(menuButtonImage));
        menuButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        menuButton.setBackground(Background.fill(Color.TRANSPARENT));
        menuButton.setLayoutX(180);
        menuButton.setLayoutY(300);

        saveGameButton.setGraphic(new ImageView(saveGameImage));
        saveGameButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        saveGameButton.setBackground(Background.fill(Color.TRANSPARENT));
        saveGameButton.setLayoutX(180);
        saveGameButton.setLayoutY(230);

        loadSavedButton.setGraphic(new ImageView(loadSavedImage));
        loadSavedButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        loadSavedButton.setBackground(Background.fill(Color.TRANSPARENT));
        loadSavedButton.setLayoutX(450);
        loadSavedButton.setLayoutY(300);

        startNewButton.setGraphic(new ImageView(startNewImage));
        startNewButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        startNewButton.setBackground(Background.fill(Color.TRANSPARENT));
        startNewButton.setLayoutX(480);
        startNewButton.setLayoutY(200);

        exitGameButton.setGraphic(new ImageView(exitGameImage));
        exitGameButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        exitGameButton.setBackground(Background.fill(Color.TRANSPARENT));
        exitGameButton.setLayoutX(180);
        exitGameButton.setLayoutY(300);
    }

    public static void main(String[] args) { launch(args); }
}