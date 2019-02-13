package inf112.skeleton.app;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;

import java.util.ArrayList;

public class Player extends Actor {
    private int HP = 100;
    private ArrayList<Card> inventory;
    private Array<Action> playerActions = new Array<>();

    // Should be affected by HP
    // (less HP, less new cards)
    // not yet implemented
    int inventorySize;

    float moveX;
    float moveY;

    public Player(){
        //this = new Actor();
        this.inventorySize = 5;
        inventory = new ArrayList<Card>();
    }

    // Player plays current card,
    // then removes it from inventory
    // Returns value of priority
    public int turn (){
        Card currentCard = inventory.get(0);
        // ... plays card, not yet implemented
        inventory.remove(0);
        return currentCard.getPriority();
    }

    public void drawCards(Deck deck){
        for (int i = 0; i<inventorySize; i++){
            inventory.add(deck.handOut());
        }
    }

    // The player selects cards to use,
    // in which order
    public void selectCards(){
        // ... not yet implemented
    }

    public void cardsToActions(){
        for (int i = 0; i<inventory.size() ; i++){
            Card addActionFromThisCard = inventory.get(i);

           // playerActions.add();
        }
    }

    public void performCurrentAction(){
        // ... not yet implemented
    }

    public void setHP(int HP) { this.HP = HP; }

    public int getHP() {
        return HP;
    }
}
