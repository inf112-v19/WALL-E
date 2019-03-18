package inf112.skeleton.app.Objects.Actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.Game.MyGame;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ActorTest {
    private MyGame game;

    @Before
    public void setUp() {
        game = new MyGame();
        new HeadlessApplication(game);

        /*LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo-Rally!";
        cfg.width = 1080;
        cfg.height = 720;
        cfg.resizable = false;

        new LwjglApplication(game, cfg);*/

        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < 5000) {
            try {
                game.actor.getX();
                break;
            } catch (NullPointerException ignored) {

            }
        }
    }

    @Test
    public void chooseCard() {
        // Setup
        game.handOut();

        int i = 0;
        while (i++ < 9) game.chooseCard(0);

        assertEquals("Should choose no more or less than 5 cards", 5, game.chosen.size());

        // Teardown
        game.handout.clear();
    }

    @Test
    public void handOut() {
        assertTrue("Actor should start out with 0 cards", game.handout.isEmpty());
        game.handOut();
        assertEquals("Actor should have 9 cards", 9, game.handout.size());
    }

    @Test
    public void keyDown() {
        float initX, initY, posX, posY;
        initX = posX = game.actor.getX();
        initY = posY = game.actor.getY();

        // Move player UP
        game.keyDown(Input.Keys.UP);
        assertEquals("Move up 78px", posY + 78, game.actor.getY());
        assertEquals("Remain on same x-level", posX, game.actor.getX());
        posY = game.actor.getY();

        // Move player DOWN
        game.keyDown(Input.Keys.DOWN);
        assertEquals("Move down 78px", posY - 78, game.actor.getY());
        assertEquals("Remain on same x-level", posX, game.actor.getX());
        posY = game.actor.getY();

        // Move player RIGHT
        game.keyDown(Input.Keys.RIGHT);
        assertEquals("Remain on same y-level", posY, game.actor.getY());
        assertEquals("Move right 142px", posX + 142, game.actor.getX());
        posX = game.actor.getX();

        // Move player LEFT
        game.keyDown(Input.Keys.LEFT);
        assertEquals("Remain on same y-level", posY, game.actor.getY());
        assertEquals("Move left 142px", posX - 142, game.actor.getX());
        posX = game.actor.getX();

        // Assert that we are back in starting position
        assertEquals("Back to starting pos", initY, posY);
        assertEquals("Back to starting pos", initX, posX);
    }
}
