package inf112.skeleton.app.Objects.Actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class ActorTest {
    private MyGame game;
    private Deck deck;
    private GridOfTiles grid;

    @Before
    public void setUp() {
        this.deck = new Deck();
        this.game = new MyGame();
        this.grid = new GridOfTiles(11, 11, 78);
        new HeadlessApplication(game);
    }

    @Test
    public void chooseCardFromHandout() {
        ArrayList<Card> testOut = new ArrayList<>(9);

        for (int i = 0; i <9 ; i++) {
            testOut.add(deck.handOut());
        }
        MyActor actor = new MyActor("redTanks1.png", MyGame.Dir.NORTH, false, "testbot", 0);
        // Setup

        int i = 0;
        while (i++<9)deck.chooseCardFromHandout(0, testOut, actor);

        assertEquals("Should choose no more or less than 5 cards", 5, actor.chosen.size());

        // Teardown
        testOut.clear();
    }

    @Test
    public void handOut() {
        game.handOut();
        assertEquals("Actor should have 9 cards", 9, game.handout.size());
    }

    @Test
    public void keyDown() {
        MyActor actor = new MyActor("redTanks1.png", MyGame.Dir.NORTH, false, "testbot", 0);
        float initX, initY, posX, posY;
        Tile initTile = grid.getTile(0, 0);
        initX = posX = 0;
        initY = posY = 0;

        // Move player UP
        //game.keyDown(Input.Keys.UP);
        actor.Forward(1, 78, grid);
        assertEquals("Move up 78px", posY+78, actor.getY());
        assertEquals("Remain on same x-level", posX, actor.getX());
        posY = actor.getY();

        // Move player DOWN
        actor.Forward(1, -78, grid);
        assertEquals("Move down 78px", posY - 78, actor.getY());
        assertEquals("Remain on same x-level", posX, actor.getX());
        posY = actor.getY();

        // Move player RIGHT
       /* game.keyDown(Input.Keys.RIGHT);
        game.keyDown(Input.Keys.UP);*/
       actor.setDir(MyGame.Dir.EAST);
       actor.Forward(1, 78, grid);
        assertEquals("Remain on same y-level", posY, actor.getY());
        assertEquals("Move right 78px", posX + 78, actor.getX());
        posX = actor.getX();

        // Move player LEFT
        actor.setDir(MyGame.Dir.WEST);
        actor.Forward(1, 78, grid);
        assertEquals("Remain on same y-level", posY, actor.getY());
        assertEquals("Move left 78px", posX - 78, actor.getX());
        posX = actor.getX();

        // Assert that we are back in starting position
        assertEquals("Back to starting pos", initY, posY);
        assertEquals("Back to starting pos", initX, posX);
    }
}
