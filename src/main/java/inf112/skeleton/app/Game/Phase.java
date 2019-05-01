package inf112.skeleton.app.Game;

import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class Phase {

    /**
     * @param actors
     * One phase is one use of a card, one movement from conveyour or other objects
     */
    MyGame game ;
    ArrayList<MyActor> actors;
    MyActor actor;

    public Phase(MyGame game, ArrayList<MyActor> actors, MyActor currentActor) {
        this.game = game;
        this.actors = actors;
        this.actor = currentActor;
    }

    public void playPhase(int cardIndex) {
        if (cardIndex <= 0) {
            System.out.println("Something went wrong.");
            return;
        }
        for (MyActor actor : actors) {
            if (actor.chosen.size() > 0) {
                Card toUse = actor.chosen.get(cardIndex);
                useCard(toUse, actor);
            }
            game.shootLaserWithActor(actor);
        }
    }

    private void useCard(Card toUse, MyActor actor) {
        String type = getType(toUse);
        int moveDist = game.getPXSIZE();
        assert type != null;
        switch (type) {
            case "Move":
                System.out.println(actor.getName() + " should move " + actor.getDir() + " by: " + toUse.getMoves());
                for (int i = 0; i < toUse.getMoves(); i++) {
                    actor.Forward(1, moveDist, game.getGrid());
                }
                break;
            case "Backup":
                System.out.println(actor.getName() + " should move backwards by: " + toUse.getMoves());
                this.actor.backward(1, moveDist, game.getGrid());
                break;
            case "Turn":
                if (toUse.getTurn() == Card.Turn.LEFT) {
                    actor.turnLeft();
                } else if (toUse.getTurn() == Card.Turn.RIGHT) {
                    actor.turnRight();
                } else if (toUse.getTurn() == Card.Turn.UTURN) {
                    actor.uTurn();
                }
                System.out.println("It was a turn card. " + actor.getName() + " turned " + toUse.getTurn());
                break;
        }
        game.lessHpLockCards(actor);
        actor.setLastHandout(game.handout);
    }
}
