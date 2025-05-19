package cz.cvut.fel.pjv.dudkolau.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.cvut.fel.pjv.dudkolau.Model.HitBox.checkCollisionWithObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameTest {
    private Game testGame;
    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testGame = new Game();
        testGame.setTileDimension(5);
        testGame.startNewGame();
        testPlayer = testGame.getPlayer();
    }

    @Test
    void setHitBox() {
        Enemy testEnemy = new Enemy();
        testEnemy.setHitBox(0,0);
        assertNotNull(testEnemy.getHitBox());
        assertNotNull(testPlayer.getHitBox());
    }

    @Test
    void testCollision(){
        BackgroundObject bo = testGame.getCurrLevel().getBackgroundObjects().getFirst();
        testPlayer.setxCoord(bo.getxCoord()-5);
        testPlayer.setyCoord(bo.getyCoord()-5);
        testPlayer.setHitBox(0,0);
        assertTrue(checkCollisionWithObject(testPlayer,bo));
    }

    @Test
    void saveAndLoadGame() {
        int xPosition = testPlayer.getxCoord();
        testPlayer.move(Directions.RIGHT, testGame.getWidth(), testGame.getWidth(), testGame.getTileDimension());
        testGame.saveGame();

        Game newGame = new Game();
        newGame.loadSavedGame();

        int newXPosition = newGame.getPlayer().getxCoord();

        assertNotEquals(xPosition,newXPosition);
    }

    @Test
    public void testLevelTransition() {
        Level firstLevel = testGame.getCurrLevel();
        Door testedDoors = firstLevel.getDoors().getFirst();
        testPlayer.setInteracting(true);
        testPlayer.setxCoord(testedDoors.getxCoord()-testPlayer.getWidth()/ testGame.getTileDimension());
        testPlayer.setyCoord(testedDoors.getyCoord());
        testPlayer.setHitBox(0,0);
        testPlayer.setLastDirection(Directions.RIGHT);
        testGame.setInteractingTimer(0);

        testGame.update();
        Level newLevel = testGame.getCurrLevel();
        assertNotEquals(firstLevel, newLevel);
    }

    @Test
    public void testStartNewGame() {
        Game game = mock(Game.class);
        when(game.isRunning()).thenReturn(true);
        when(game.getPlayer()).thenReturn(new Player());
        when(game.getCurrLevel()).thenReturn(new Level());
        game.startGame(true);
        assertTrue(game.isRunning());
        assertNotNull(game.getPlayer());
        assertNotNull(game.getCurrLevel());
    }

    @Test
    public void testPotionEffectDuration() {
        testPlayer.setInvincible(true);
        testPlayer.setDamage(5);

        testGame.setInviPotionCountDown(100);
        testGame.setDamagePotionCountDown(200);
        for (int i = 0; i < 101; i++) {
            testGame.update(); }
        assertFalse(testPlayer.isInvincible());
        assertEquals(5,testPlayer.getDamage());
        for (int i = 0; i < 101; i++) {
            testGame.update(); }
        assertNotEquals(5,testPlayer.getDamage());
    }
}