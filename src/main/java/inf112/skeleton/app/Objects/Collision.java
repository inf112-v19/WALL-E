package inf112.skeleton.app.Objects;

import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
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

        System.out.println(actorTile);
        for (int i = 0; i <onTile.size() ; i++) {
            if (onTile.get(i).equals(actor)) continue;
            if (onTile.get(i) instanceof  Flag){
                ((Flag) onTile.get(i)).handleFlag(actor, grid);
            }
            if (onTile.get(i) instanceof BlueTeleport){
                ((BlueTeleport) onTile.get(i)).handleTeleportation(actor, grid);
            }
            if (onTile.get(i) instanceof YellowTeleport){
                ((YellowTeleport) onTile.get(i)).handleTeleportation(actor, grid);
            }
            if (onTile.get(i) instanceof Hole){
                ((Hole) onTile.get(i)).handleFallingIntoHoles(actor, grid);
            }
            if (onTile.get(i) instanceof WrenchesSingle){
                ((WrenchesSingle) onTile.get(i)).handleWrench(actor, grid);
            }
            if (onTile.get(i) instanceof WrenchesDouble){
                ((WrenchesDouble) onTile.get(i)).handleWrench(actor, grid);
            }

        }

    }
}
