package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Random;

import inf112.skeleton.app.Card.Turn;

public class Deck {
    ArrayList<Card> deck;
    
    public Deck () {
        deck = new ArrayList<Card>();
        createDeck();
    }
    
    private void createDeck() {
        // Adds U-TURNs to the deck
        for (int p=10; p<=60; p+=10) {
            deck.add(createTurnCard(Turn.UTURN, p));
        }
        // Adds TURN-LEFTs to the deck
        for (int p=70; p<=410; p+=20) {
            deck.add(createTurnCard(Turn.LEFT, p));
        }
        // Adds TURN-RIGHTs to the deck
        for (int p=80; p<=420; p+=20) {
            deck.add(createTurnCard(Turn.RIGHT, p));
        }
        // Adds BACKUPs to the deck
        for (int p=430; p<=480; p+=10) {
            deck.add(createBackupCard(p));
        }
        // Adds MOVE 1s to the deck
        for (int p=490; p<=650; p+=10) {
            deck.add(createMoveCard(1, p));
        }
        // Adds MOVE 2s to the deck
        for (int p=670; p<=780; p+=10) {
            deck.add(createMoveCard(1, p));
        }
        // Adds MOVE 3s to the deck
        for (int p=790; p<=840; p+=10) {
            deck.add(createMoveCard(1, p));
        }
    }

    public Card handOut() {
        // Retrieves random card from the deck,
        // and removes it from the deck afterwards
        Random rand = new Random();
        int randIndex = rand.nextInt(deck.size()); 
        Card randCard = deck.get(randIndex);
        deck.remove(randIndex);
        return randCard;
    }
    
    public Card createMoveCard (int moves, int priority) {
        return new Card(Turn.NONE, moves, priority, false);
    }
    
    public Card createTurnCard(Turn turn, int priority) {
        return new Card(turn, 0, priority, false);
    }
    
    public Card createBackupCard(int priority) {
        return new Card(Turn.NONE, 0, priority, true);
    }
    
    
}
