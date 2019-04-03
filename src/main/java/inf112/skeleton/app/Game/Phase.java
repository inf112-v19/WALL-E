package inf112.skeleton.app.Game;

import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.ArrayList;
import java.util.Collections;

public class Phase {

    /**
     *
     * @param actors
     * One phase is one use of a card, one movement from conveyour or other objects
     */
    MyGame game;
    ArrayList<MyActor> actors;
    MyActor currentActor;
    int cardIndex;

    public Phase(MyGame game, ArrayList<MyActor> actors, MyActor currentActor, int cardIndex){
       this.game = game;
       this.actors = actors;
       this.currentActor = currentActor;
       this.cardIndex = cardIndex;
    }

    public void playPhase(){
        MyActor actor = currentActor;
        Card toUseForActor = actor.chosen.get(cardIndex);

        game.useCard(toUseForActor, actor);
        if (game.phaseNum>0){
            game.phaseNum -=1;
        }else {
            game.phaseNum = 4;
            game.handOut();
        }
    }
}
