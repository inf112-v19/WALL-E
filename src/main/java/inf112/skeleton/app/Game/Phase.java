package inf112.skeleton.app.Game;

import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class Phase {

    MyGame game;
    ArrayList<MyActor> actors;
    MyActor actor;

    public Phase(MyGame game, ArrayList<MyActor> actors, MyActor currentActor) {
        this.game = game;
        this.actors = actors;
        this.actor = currentActor;
    }

    public void playPhase(int cardIndex) {
        for(MyActor actor : actors) {
            if(actor.chosen.size() != 0) {
                System.out.println(actor.getName() + " remaining cards: " + actor.chosen.size());
                Card toUse = actor.chosen.get(cardIndex);
                useCard(toUse, actor);
                game.shootLaserWithActor(actor);
            }
            else {
                System.out.println(actor.getName() + " has to choose cards again");
                return;
            }
        }
    }

    private void useCard(Card toUse, MyActor actor) {
        String type = getType(toUse);
        game.removeCardFromChosenAndHandout(toUse, actor);

        int moveDist = game.getPXSIZE();
        assert type != null;
        switch (type) {
            case "Move":
                System.out.println(actor.getName() + " should move " + actor.getDir());
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
        game.lessHpLockCards();
        actor.setLastHandout(game.handout);
        }

}
