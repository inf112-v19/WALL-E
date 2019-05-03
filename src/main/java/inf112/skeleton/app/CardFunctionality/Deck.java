package inf112.skeleton.app.CardFunctionality;

import inf112.skeleton.app.CardFunctionality.Card.Turn;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        createDeck();
    }

    private void createDeck() {
        for (int p = 10; p <= 60; p += 10) {
            deck.add(createTurnCard(Turn.UTURN, p));
        }
        for (int p = 70; p <= 410; p += 20) {
            deck.add(createTurnCard(Turn.LEFT, p));
        }
        for (int p = 80; p <= 420; p += 20) {
            deck.add(createTurnCard(Turn.RIGHT, p));
        }
        for (int p = 430; p <= 480; p += 10) {
            deck.add(createBackupCard(p));
        }
        for (int p = 490; p <= 660; p += 10) {
            deck.add(createMoveCard(1, p));
        }
        for (int p = 670; p <= 780; p += 10) {
            deck.add(createMoveCard(2, p));
        }
        for (int p = 790; p <= 840; p += 10) {
            deck.add(createMoveCard(3, p));
        }
    }

    public Card handOut() {
        if (deck.isEmpty())
            createDeck();

        Random rand = new Random();
        int randIndex = rand.nextInt(deck.size());
        Card randCard = deck.get(randIndex);
        deck.remove(randIndex);
        return randCard;
    }

    public void chooseCardFromHandout(int i, ArrayList<Card> handout, MyActor currentActor){
        Card card = handout.get(i);
        currentActor.chosen.add(i, card);
        while (currentActor.chosen.size() > 5) {
            Card deletedCard = currentActor.chosen.remove(currentActor.chosen.size() - 1);
            handout.add(deletedCard);
        }
    }

    boolean isEmpty() {
        return deck.isEmpty();
    }

    int size() {
        return deck.size();
    }

    Card createMoveCard(int moves, int priority) {
        return new Card(Turn.NONE, moves, priority, false);
    }

    Card createTurnCard(Turn turn, int priority) {
        return new Card(turn, 0, priority, false);
    }

    Card createBackupCard(int priority) {
        return new Card(Turn.NONE, 0, priority, true);
    }


}
