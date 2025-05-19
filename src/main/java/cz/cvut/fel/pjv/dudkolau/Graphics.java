package cz.cvut.fel.pjv.dudkolau;

import javafx.scene.image.Image;

public class Graphics {

    public static Image backgroundPaused = new Image("backgroundPaused.png");
    public static Image backgroundImage = new Image("background_wall.png");
    public static Image saveGameImage = new Image("saveGameButton.png");
    public static Image exitGameImage = new Image("exitGameButton.png");
    public static Image continueImage = new Image("continueButton.png");
    public static Image startNewImage = new Image("startNewButton.png");
    public static Image loadSavedImage = new Image("loadSavedButton.png");
    public static Image menuButtonImage = new Image("menuButton.png");
    public static Image gameButtonImage  = new Image("button.png");
    public static Image gameButtonPressedImage = new Image("pressedButton.png");
    public static Image pausedMenu = new Image("pausedBackground.png");
    public static Image mainMenuBackground = new Image("mainMenu.png");
    public static Image gameOverBackground = new Image("gameOverMenu.png");
    public static Image enemyImage = new Image("ghost.png");
    public static Image emptyHeartImage = new Image("heartEmpty.png");
    public static Image fullHeartImage = new Image("heartFull.png");

    public static Image inviEffectImage = new Image("inviEffect.png");
    public static Image dmgEffectImage = new Image("damageEffect.png");

    public static Image fightLeftImage = new Image("fightLEFT.png");
    public static  Image splashLeftImage = new Image("splashLEFT.png");
    public static Image fightRightImage = new Image("fightRIGHT.png");
    public static  Image splashRightImage = new Image("splashRIGHT.png");
    public static  Image splashUpImage = new Image("splashUP.png");
    public static  Image splashDownImage = new Image("splashDOWN.png");

    public static Image[] ghostCycle = {
            new Image("ghostCycle-1.png"),
            new Image("ghostCycle-2.png"),
            new Image("ghostCycle-3.png"),
            new Image("ghostCycle-4.png"),
            new Image("ghostCycle-5.png"),
            new Image("ghostCycle-6.png"),
            new Image("ghostCycle-7.png"),
            new Image("ghostCycle-8.png"),
            new Image("ghostCycle-9.png"),
            new Image("ghostCycle-10.png"),
            new Image("ghostCycle-11.png")
    };

    public static Image[] animSTAND_MENU = {
            new Image("mainMenuPlayer-1.png"),
            new Image("mainMenuPlayer-2.png"),
            new Image("mainMenuPlayer-3.png"),
            new Image("mainMenuPlayer-4.png"),
            new Image("mainMenuPlayer-5.png"),
            new Image("mainMenuPlayer-6.png"),
            new Image("mainMenuPlayer-7.png"),
            new Image("mainMenuPlayer-8.png")
    };

    public static Image[] animRIGHT = {
            new Image("animRIGHT-1.png"),
            new Image("animRIGHT-2.png"),
            new Image("animRIGHT-3.png"),
            new Image("animRIGHT-4.png"),
            new Image("animRIGHT-5.png"),
            new Image("animRIGHT-6.png"),
            new Image("animRIGHT-7.png"),
            new Image("animRIGHT-8.png")
    };

    public static Image[] animUP = {
            new Image("animUP-1.png"),
            new Image("animUP-2.png"),
            new Image("animUP-3.png"),
            new Image("animUP-4.png"),
            new Image("animUP-5.png"),
            new Image("animUP-6.png"),
            new Image("animUP-7.png"),
            new Image("animUP-8.png")
    };

    public static Image[] animDOWN = {
            new Image("animDOWN-1.png"),
            new Image("animDOWN-2.png"),
            new Image("animDOWN-3.png"),
            new Image("animDOWN-4.png"),
            new Image("animDOWN-5.png"),
            new Image("animDOWN-6.png"),
            new Image("animDOWN-7.png"),
            new Image("animDOWN-8.png")
    };

    public static Image[] animSTAND_RIGHT = {
            new Image("animSTAND_RIGHT-1.png"),
            new Image("animSTAND_RIGHT-2.png"),
            new Image("animSTAND_RIGHT-3.png"),
            new Image("animSTAND_RIGHT-4.png"),
            new Image("animSTAND_RIGHT-5.png"),
            new Image("animSTAND_RIGHT-6.png"),
            new Image("animSTAND_RIGHT-7.png"),
            new Image("animSTAND_RIGHT-8.png"),
    };

    public static Image[] animSTAND_UP = {
            new Image("animSTAND_UP-1.png"),
            new Image("animSTAND_UP-2.png"),
            new Image("animSTAND_UP-3.png"),
            new Image("animSTAND_UP-4.png"),
            new Image("animSTAND_UP-5.png"),
            new Image("animSTAND_UP-6.png"),
            new Image("animSTAND_UP-7.png"),
            new Image("animSTAND_UP-8.png"),
    };

    public static Image[] animSTAND_DOWN = {
            new Image("animSTAND_DOWN-1.png"),
            new Image("animSTAND_DOWN-2.png"),
            new Image("animSTAND_DOWN-3.png"),
            new Image("animSTAND_DOWN-4.png"),
            new Image("animSTAND_DOWN-5.png"),
            new Image("animSTAND_DOWN-6.png"),
            new Image("animSTAND_DOWN-7.png"),
            new Image("animSTAND_DOWN-8.png"),
    };

    public static Image[] animSTAND_LEFT = {
            new Image("animSTAND_LEFT-1.png"),
            new Image("animSTAND_LEFT-2.png"),
            new Image("animSTAND_LEFT-3.png"),
            new Image("animSTAND_LEFT-4.png"),
            new Image("animSTAND_LEFT-5.png"),
            new Image("animSTAND_LEFT-6.png"),
            new Image("animSTAND_LEFT-7.png"),
            new Image("animSTAND_LEFT-8.png"),
    };

    public static Image[] animLEFT = {
            new Image("animLEFT-1.png"),
            new Image("animLEFT-2.png"),
            new Image("animLEFT-3.png"),
            new Image("animLEFT-4.png"),
            new Image("animLEFT-5.png"),
            new Image("animLEFT-6.png"),
            new Image("animLEFT-7.png"),
            new Image("animLEFT-8.png"),
    };

    public static int playerImageIndex = 0; //in animation cycle
    public static int ghostCycleIndex = 0;
}
