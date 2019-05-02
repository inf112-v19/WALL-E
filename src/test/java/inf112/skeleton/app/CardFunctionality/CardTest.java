package inf112.skeleton.app.CardFunctionality;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private Card card;
    private Card.Turn turn;
    private int moves;
    private int priority;
    private boolean isBackup;


    @Before
    public void setup() {
        card = new Card(turn, moves, priority, isBackup);
    }

    @Test
    public void testHeight() {
        float height = card.getHeight();
        assertEquals(height, card.getHeight());
    }

    @Test
    public void testWidth() {
        float width = card.getWidth();
        assertEquals(width, card.getWidth());
    }

    @Test
    public void testMove() {
        moves = 1;
        card = new Card(turn, moves, priority, isBackup);
        assertEquals(1, card.getMoves());
        assertNotEquals(2, card.getMoves());

        moves = 2;
        card = new Card(turn, moves, priority, isBackup);
        assertEquals(2, card.getMoves());
        assertNotEquals(1, card.getMoves());

        moves = 3;
        card = new Card(turn, moves, priority, isBackup);
        assertEquals(3, card.getMoves());
        assertNotEquals(2, card.getMoves());

    }

    @Test
    public void testSetGet() {
        card.setY(10);
        card.setX(10);
        assertEquals(10, card.getX());
        assertEquals(10, card.getY());
        assertNotEquals(12, card.getY());
        assertNotEquals(12, card.getX());
    }

    @Test
    public void testTurn() {
        turn = Card.Turn.LEFT;
        card = new Card(turn, moves, priority, isBackup);
        assertEquals(Card.Turn.LEFT, card.getTurn());

        turn = Card.Turn.RIGHT;
        card = new Card(turn, moves, priority, isBackup);
        assertEquals(Card.Turn.RIGHT, card.getTurn());

        turn = Card.Turn.UTURN;
        card = new Card(turn, moves, priority, isBackup);
        assertEquals(Card.Turn.UTURN, card.getTurn());

        turn = Card.Turn.NONE;
        card = new Card(turn, moves, priority, isBackup);
        assertEquals(Card.Turn.NONE, card.getTurn());
    }
}
