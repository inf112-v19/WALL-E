package inf112.skeleton.app.Actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.NewGame;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ActorTest {
    private NewGame game;

    @Before
    public void setUp() throws InterruptedException {
        // TODO: Use headless config
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo-Rally Test";
        cfg.width = 100;
        cfg.height = 100;

        game = new NewGame(1);
        new LwjglApplication(game, cfg);

        // Required for create(), render() ???
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void chooseCard() {
        // Setup
        game.actor.handOut();

        int i = 0;
        while (i++ < 9) game.actor.chooseCard(0);

        assertEquals("Should choose no more or less than 5 cards", 5, game.actor.chosen.size());

        // Teardown
        game.actor.handout.clear();
    }

    @Test
    public void handOut() {
        assertTrue("Actor should start out with 0 cards", game.actor.handout.isEmpty());
        game.actor.handOut();
        assertEquals("Actor should have 9 cards", 9, game.actor.handout.size());
    }

    @Test
    public void keyDown() {
        float initX = game.actor.getX();
        float initY = game.actor.getY();
        float posX = initX;
        float posY = initY;

        // Move player DOWN
        game.actor.keyDown(Input.Keys.DOWN);
        assertEquals("Move down 32px", posY - 32, game.actor.getY());
        assertEquals("Remain on same x-level", posX, game.actor.getX());
        posY = game.actor.getY();

        // Move player UP
        game.actor.keyDown(Input.Keys.UP);
        assertEquals("Move up 32px", posY + 32, game.actor.getY());
        assertEquals("Remain on same x-level", posX, game.actor.getX());
        posY = game.actor.getY();

        // Move player LEFT
        game.actor.keyDown(Input.Keys.LEFT);
        assertEquals("Remain on same y-level", posY, game.actor.getY());
        assertEquals("Move left 32px", posX - 32, game.actor.getX());
        posX = game.actor.getX();

        // Move player RIGHT
        game.actor.keyDown(Input.Keys.RIGHT);
        assertEquals("Remain on same y-level", posY, game.actor.getY());
        assertEquals("Move right 32px", posX + 32, game.actor.getX());
        posX = game.actor.getX();

        // Assert that we are back in starting position
        assertEquals("Back to starting pos", initY, posY);
        assertEquals("Back to starting pos", initX, posX);
    }
}
