package cz.cvut.fel.pjv.dudkolau;

import cz.cvut.fel.pjv.dudkolau.Model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static cz.cvut.fel.pjv.dudkolau.Constants.*;
import static cz.cvut.fel.pjv.dudkolau.Graphics.*;

public class Main extends Application {
    //private Graphics graphics = new Graphics();
    //private Image playerImage =  new Image("playerImage.png");

    private Game game = new Game();
    private Image[] currPlayerImages = animSTAND_RIGHT;
    private  Directions lastDirection = Directions.NONE;
    private int animationCounter = 0;
    private boolean pausedMenuButtonsAdded = false;
    private boolean mainMenuButtonsAdded = false;
    private boolean showHitBoxes = false;

    private Button continueButton = new Button();
    private Button exitGameButton = new Button();
    private Button saveGameButton = new Button();
    private Button loadSavedButton = new Button();
    private Button startNewButton = new Button();

    private Button menuButton = new Button();
    //private Image backgroundImage = new Image("background_wall.png");
    private Image currBackgroundImage;

    @Override
    public void start(Stage stage) throws Exception {


        game.setTileDimension(tileDimension);
        Canvas canvas = new Canvas(game.getWidth(), game.getHeight());
        AnchorPane pane = new AnchorPane(canvas);
        //using anchorpane instead of stackpane allows moving buttons more freely
        Scene scene = new Scene(pane, game.getWidth(), game.getHeight());


        loadButtons();

        stage.setTitle("Beyond the Forest");
        stage.setResizable(true);



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        game.setPaused(!game.getIsPaused());
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
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
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
            }
        });

        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                game.setPaused(!game.getIsPaused());
                game.setRunning(!game.isRunning());
            }
        });

        exitGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                System.out.println("Exiting!");
                System.exit(0);
            }
        });

        saveGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                System.out.println("Saving...");
                game.saveGame();
            }
        });

        loadSavedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                System.out.println("Loading saved game...");
                game.startGame(false);
            }
        });
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                game.setPaused(false);
                game.setRunning(false);
                game.setMainMenuOn(true);
            }
        });

        startNewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                System.out.println("Starting new game");
                game.startGame(true);
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            long lastCall;
            @Override
            public void handle(long l) {
                if ((l - lastCall) >= 25_000_000) {
                    //GAMEPLAY
                    if (game.isRunning()) {
                        //TODO sprinting?sss
                        game.update();
                        setCurrPlayerImages();
                        if (pausedMenuButtonsAdded) {
                            pane.getChildren().removeAll(menuButton, continueButton, saveGameButton);
                            pausedMenuButtonsAdded = false;
                            System.out.println("Deleted button");
                        }
                        if (mainMenuButtonsAdded) {
                            pane.getChildren().removeAll(exitGameButton,loadSavedButton,startNewButton);
                            mainMenuButtonsAdded = false;
                        }
                        render(canvas);
                        if (animationCounter%3==0) {
                            animate();
                        }
                        animationCounter++;

                    // PAUSED MENU
                    } else if (game.isPaused()){
                        if (!pausedMenuButtonsAdded) {
                            loadButtons();
                            pane.getChildren().addAll(menuButton, continueButton, saveGameButton);
                            pausedMenuButtonsAdded = true;
                            System.out.println("Added button");
                        }
                        render(canvas);
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage(backgroundPaused,0,0);
                        gc.drawImage(pausedMenu, 155,110);

                    //MAIN MENU
                    } else if (game.isMainMenuOn()) {
                        if (pausedMenuButtonsAdded) {
                            pane.getChildren().removeAll(menuButton, continueButton, saveGameButton);
                            pausedMenuButtonsAdded = false;
                            System.out.println("Deleted button");
                        }
                        if (!mainMenuButtonsAdded) {
                            loadButtons();
                            exitGameButton.setLayoutX(578);
                            exitGameButton.setLayoutY(400);
                            pane.getChildren().addAll(loadSavedButton, startNewButton, exitGameButton);
                            mainMenuButtonsAdded = true;
                            System.out.println("Added button");
                        }
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage(mainMenuBackground,0,0);
                        gc.drawImage(ghostCycle[ghostCycleIndex],90,150);
                        gc.drawImage(animSTAND_MENU[playerImageIndex], 220,280);
                        if (animationCounter%3==0) {
                            animate();
                        }
                        animationCounter++;

                        //GAME OVER
                    } else if (game.isGameOverMenu()) {
                        if (!mainMenuButtonsAdded) {
                            exitGameButton.setLayoutX(450);
                            exitGameButton.setLayoutY(400);
                            startNewButton.setLayoutX(450);
                            startNewButton.setLayoutY(260);
                            loadSavedButton.setLayoutX(450);
                            loadSavedButton.setLayoutY(330);
                            pane.getChildren().addAll(loadSavedButton, startNewButton, exitGameButton);
                            mainMenuButtonsAdded = true;
                            System.out.println("Added button");
                        }
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage(gameOverBackground,0,0);
                    }

                    lastCall = l;
                    if (animationCounter>= 2147483600) { //so the counter doesnt overflow
                        animationCounter=0;
                    }
                }
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();


    }

    private void setCurrPlayerImages() {
        //System.out.println(game.getPlayer().getLastDirection());
        //using last direction instead of player.getLastDirection for simpler code and efficiency
        if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.RIGHT) {
            currPlayerImages = animSTAND_RIGHT;
            lastDirection = Directions.NONE;

        } else if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.LEFT) {
            currPlayerImages = animSTAND_LEFT;
            lastDirection = Directions.NONE;

        }  else if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.UP) {
            currPlayerImages = animSTAND_UP;
            lastDirection = Directions.NONE;

        }  else if (game.getPlayer().getCurrDirection()==Directions.NONE && lastDirection == Directions.DOWN) {
            currPlayerImages = animSTAND_DOWN;
            lastDirection = Directions.NONE;

        } else if (game.getPlayer().getCurrDirection()==Directions.RIGHT) {
            currPlayerImages = animRIGHT;
            lastDirection = Directions.RIGHT;

        } else if (game.getPlayer().getCurrDirection()==Directions.UP) {
            currPlayerImages = animUP;
            lastDirection = Directions.UP;

        } else if (game.getPlayer().getCurrDirection()==Directions.DOWN) {
            currPlayerImages = animDOWN;
            lastDirection = Directions.DOWN;

        } else if (game.getPlayer().getCurrDirection()==Directions.LEFT) {
            currPlayerImages = animLEFT;
            lastDirection = Directions.LEFT;
        }
    }


    private void render(Canvas canvas) {
        //TODO pridat vsechny images do graphics
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);


        boolean drewPlayer = false;
        int playerY = game.getPlayer().getHitBox().getyCoord();
        int objectY;
        int objectX;

        //Render buttons
        GameButton b;
        for (int i = 0; i < game.getCurrLevel().getGameButtonsNum(); i++) {
            b = game.getCurrLevel().getGameButtons().get(i);
            if (b.isPressed()) {
                gc.drawImage(new Image("pressedButton.png"), b.getxCoord() * game.getTileDimension(), b.getyCoord() * game.getTileDimension());
            } else {
                gc.drawImage(new Image("button.png"), b.getxCoord() * game.getTileDimension(), b.getyCoord() * game.getTileDimension());
            }
        }
        Door currDoor;
        for (int i = 0; i < game.getCurrLevel().getDoorsNum(); i++) { {
            currDoor = game.getCurrLevel().getDoors().get(i);
            if (currDoor.isLocked()) {
                gc.drawImage(new Image(currDoor.getLockedImageName()), currDoor.getxCoord()* game.getTileDimension(),currDoor.getyCoord()* game.getTileDimension());
            } else {
                gc.drawImage(new Image(currDoor.getImageName()), currDoor.getxCoord()* game.getTileDimension(),currDoor.getyCoord()* game.getTileDimension());

            }
        }

        }
        //TODO toto je zbytecne pro vsechno krome background objects, zmenit to maybe??
        //concept: player shows behind or infront of bushes
        for (int i = 0; i < game.getGameObjects().size(); i++) {

            objectY = game.getGameObjects().get(i).getyCoord();
            objectX = game.getGameObjects().get(i).getxCoord();
            if (playerY-tileDimension>objectY && playerY< objectY+game.getGameObjects().get(i).getHeight()/tileDimension) {
                //System.out.println("Hrac pred kerem, kre cislo: " + i);
                gc.drawImage(new Image(game.getGameObjects().get(i).getImageName()),objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());

                if (game.getPlayer().isFighting()) {
                    gc.drawImage(new Image("fightLEFT.png"), game.getPlayer().getxCoord() * game.getTileDimension(),
                            game.getPlayer().getyCoord() * game.getTileDimension());
                    gc.drawImage(new Image("splashLEFT.png"), game.getPlayer().getxCoord() * game.getTileDimension()-60,
                            game.getPlayer().getyCoord() * game.getTileDimension());
                } else {
                    gc.drawImage(currPlayerImages[playerImageIndex], game.getPlayer().getxCoord() * game.getTileDimension(),
                            game.getPlayer().getyCoord() * game.getTileDimension());
                }
                drewPlayer = true;

            } else {
                if (!drewPlayer) {
                    if (game.getPlayer().isFighting()) {
                        gc.drawImage(new Image("fightLEFT.png"), game.getPlayer().getxCoord() * game.getTileDimension(),
                                game.getPlayer().getyCoord() * game.getTileDimension());
                        gc.drawImage(new Image("splashLEFT.png"), game.getPlayer().getxCoord() * game.getTileDimension()-60,
                                game.getPlayer().getyCoord() * game.getTileDimension());
                    } else {
                        gc.drawImage(currPlayerImages[playerImageIndex], game.getPlayer().getxCoord() * game.getTileDimension(),
                                game.getPlayer().getyCoord() * game.getTileDimension());
                    }
                        drewPlayer=true;

                }
                //for (int i = 0; i<game.getGameObjects().length; i++) {
                gc.drawImage(new Image(game.getGameObjects().get(i).getImageName()), objectX*game.getTileDimension(),
                        objectY*game.getTileDimension());
            }
            if (!drewPlayer) {
                if (game.getPlayer().isFighting()) {
                    gc.drawImage(new Image("fightLEFT.png"), game.getPlayer().getxCoord() * game.getTileDimension(),
                            game.getPlayer().getyCoord() * game.getTileDimension());
                    gc.drawImage(new Image("splashLEFT.png"), game.getPlayer().getxCoord() * game.getTileDimension()-60,
                            game.getPlayer().getyCoord() * game.getTileDimension());
                }else {
                    gc.drawImage(currPlayerImages[playerImageIndex], game.getPlayer().getxCoord() * game.getTileDimension(),
                            game.getPlayer().getyCoord() * game.getTileDimension());
                }

            }
        }

        //Render enemies
        for (Enemy enemy : game.getCurrLevel().getEnemies()) {
            gc.drawImage(new Image("ghost.png"), enemy.getxCoord()*tileDimension, enemy.getyCoord()*tileDimension);
        }


        //Render hearts
        int currHealth = game.getPlayer().getCurrHealth();
        int maxHealth = game.getPlayer().getMaxHealth();
        for (int i = 0; i<currHealth;i++) {
            gc.drawImage(new Image("heartFull.png"), width-50*maxHealth + i*50,5);
        }
        for (int i = 0; i<(maxHealth-currHealth);i++) {
            gc.drawImage(new Image("heartEmpty.png"),width-50*(maxHealth-currHealth)+ i*50, 5);
        }

        //Render Effects
        if (game.getPlayer().isInvincible()) {
            gc.drawImage(new Image("inviEffect.png"), 5, 5);
        }
        if (game.getDamagePotionCountDown()>0) {
            gc.drawImage(new Image("damageEffect.png"), 50,5);
        }




        if (showHitBoxes) {
            //drawRectangle(gc, game.getGameObjects().getFirst().getHitBox().getRectangle());
            for (GameObject gameObject : game.getGameObjects()) {
                drawRectangle(gc, gameObject.getHitBox().getxCoord(), gameObject.getHitBox().getyCoord(), gameObject.getHitBox().getWidth(), gameObject.getHitBox().getHeight());
            }
            for (Enemy enemy : game.getCurrLevel().getEnemies()) {
                drawRectangle(gc, enemy.getHitBox().getxCoord(), enemy.getHitBox().getyCoord(), enemy.getHitBox().getWidth(), enemy.getHitBox().getHeight());
            }
            for (GameButton b2 : game.getCurrLevel().getGameButtons()) {
                drawRectangle(gc, b2.getHitBox().getxCoord(), b2.getHitBox().getyCoord(), b2.getHitBox().getWidth(), b2.getHitBox().getHeight());

            }
            drawRectangle(gc, game.getPlayer().getHitBox().getxCoord(), game.getPlayer().getHitBox().getyCoord(), game.getPlayer().getHitBox().getWidth(), game.getPlayer().getHitBox().getHeight());
            drawRectangle(gc, game.getPlayer().getAttackHitBox().getxCoord(), game.getPlayer().getAttackHitBox().getyCoord(), game.getPlayer().getAttackHitBox().getWidth(), game.getPlayer().getAttackHitBox().getHeight());
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
        //exitGameButton.setFocusTraversable(false);

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
        //exitGameButton.setFocusTraversable(false);


    }

    public static void main(String[] args) { launch(args); }
}