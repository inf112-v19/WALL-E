package inf112.skeleton.app.Objects;

import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.Actor;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.List;

public class Collision {
    MyActor actor;
    GridOfTiles grid;

    public Collision(GridOfTiles grid, MyActor actor){
        this.actor = actor;
        this.grid = grid;
    }

    public void collisionCheck(){
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        List onTile = actorTile.getObjOnTile();

        for (int i = 0; i <onTile.size() ; i++) {
            if (onTile.get(i) instanceof  Flag){
                ((Flag) onTile.get(i)).handle(actor, grid);
            }

        }

    }
}
