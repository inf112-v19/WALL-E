package inf112.skeleton.app.Game;

import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.ArrayList;


public class Round {
    MyGame game;
    ArrayList<MyActor> actors;
    MyActor currentActor;

    public Round(MyGame game, ArrayList<MyActor> actors, MyActor currentActor){
        this.game = game;
        this.actors = actors;
        this.currentActor = currentActor;
    }
    public void playRound(){
        int phaseNum=0;
            Phase phase = new Phase(game, actors, currentActor, phaseNum);
            phase.playPhase();
            game.render();
            if (phaseNum<=5) phaseNum++;
            else phaseNum = 0;
        }
}
