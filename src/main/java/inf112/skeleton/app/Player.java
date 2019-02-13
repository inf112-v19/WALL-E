package inf112.skeleton.app;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor {
    private int HP = 100;
    Card[] inventory;
    Array<Action> playerActions = new Array<>();

    float moveX;
    float moveY;

    public Player(){
        // this = new Actor();
        initInventory(5);

    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {
        return HP;
    }

    public void cardsToActions(){
        for (int i = 0; i < inventory.length ; i++) {
            Card addActionFromThisCard = inventory[i];

           // playerActions.add();
        }
    }

    public Card[] initInventory(int size){
        inventory = new Card[size];
        for (int i = 0; i <inventory.length ; i++) {
            Card newHandOut = new Deck().handOut();
            inventory[i] = newHandOut;
        }
        return inventory;
    }

    public Card getCardFromInventory(){
        int topCard = inventory.length - 1;
        Card returnCard = inventory[topCard];
        inventory[topCard] = null;
        return returnCard;
    }
}
