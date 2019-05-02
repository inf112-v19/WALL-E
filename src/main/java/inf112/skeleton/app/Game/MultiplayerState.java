package inf112.skeleton.app.Game;

import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.io.*;
import java.util.ArrayList;

public class MultiplayerState implements Serializable {
    static int amountOfFlags;
    // TiledMap tiledMap;
    ArrayList<MyActor> actors;
    MyActor currentActor;
    // Map map;
    // TODO: Add map filename
    Deck deck;
    ArrayList<Card> handout;
    int cardStartX;
    //HealthBar healthBar;
    //HealthBar healthBar2;

    public MultiplayerState(ArrayList<MyActor> actors, MyActor currentActor, Deck deck, ArrayList<Card> handout, int cardStartX) {
        this.actors = actors;
        this.currentActor = currentActor;
        this.deck = deck;
        this.handout = handout;
        this.cardStartX = cardStartX;
    }

    byte[] toBytes() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out;
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
