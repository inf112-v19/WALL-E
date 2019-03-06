package inf112.skeleton.app.CardFunctionality;

import inf112.skeleton.app.CardFunctionality.Card.Turn;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class DeckTest {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void correctNumberOfCards() {
        assertEquals(84, deck.size());
    }

    @Test
    public void correctNumberOfBackupCards() {
        int count = 0;
        int size = deck.size();
        for (int i = 0; i < size; i++)
            if (deck.handOut().isBackup())
                count++;

        assertEquals(6, count);
    }

    @Test
    public void correctNumberOfMove1Cards() {
        int count = 0;
        int size = deck.size();
        for (int i = 0; i < size; i++)
            if (deck.handOut().getMoves() == 1)
                count++;

        assertEquals(18, count);
    }

    @Test
    public void correctNumberOfMove2Cards() {
        int count = 0;
        int size = deck.size();
        for (int i = 0; i < size; i++)
            if (deck.handOut().getMoves() == 2)
                count++;

        assertEquals(12, count);
    }

    @Test
    public void correctNumberOfMove3Cards() {
        int count = 0;
        int size = deck.size();
        for (int i = 0; i < size; i++)
            if (deck.handOut().getMoves() == 3)
                count++;

        assertEquals(6, count);
    }

    @Test
    public void correctNumberOfTurnRightCards() {
        int count = 0;
        int size = deck.size();
        for (int i = 0; i < size; i++)
            if (deck.handOut().getTurn() == Turn.RIGHT)
                count++;

        assertEquals(18, count);
    }

    @Test
    public void correctNumberOfTurnLeftCards() {
        int count = 0;
        int size = deck.size();
        for (int i = 0; i < size; i++)
            if (deck.handOut().getTurn() == Turn.LEFT)
                count++;

        assertEquals(18, count);
    }

    @Test
    public void correctNumberOfUTurnCards() {
        int count = 0;
        int size = deck.size();
        for (int i = 0; i < size; i++)
            if (deck.handOut().getTurn() == Turn.UTURN)
                count++;

        assertEquals(6, count);
    }

    @Test
    public void neverEmptyDeck() {
        for (int i = 0; i < deck.size() * 3; i++)
            deck.handOut();

        assertFalse(deck.isEmpty());
    }

    @Test
    public void moveCardProperties() {
        int moves = 10;
        int priority = 50;
        Card card = deck.createMoveCard(moves, priority);
        assertTrue(card.isMove());
        assertFalse(card.isBackup());
        assertFalse(card.isTurn());
        assertEquals(priority, card.getPriority());
        assertEquals(moves, card.getMoves());
    }

    @Test
    public void turnCardProperties() {
        Turn turn = Turn.RIGHT;
        int priority = 50;
        Card card = deck.createTurnCard(turn, priority);
        assertTrue(card.isTurn());
        assertFalse(card.isBackup());
        assertFalse(card.isMove());
        assertEquals(priority, card.getPriority());
        assertEquals(0, card.getMoves());
    }

    @Test
    public void backupCardProperties() {
        int priority = 50;
        Card card = deck.createBackupCard(priority);
        assertTrue(card.isBackup());
        assertFalse(card.isTurn());
        assertFalse(card.isMove());
        assertEquals(priority, card.getPriority());
        assertEquals(0, card.getMoves());
    }
}
