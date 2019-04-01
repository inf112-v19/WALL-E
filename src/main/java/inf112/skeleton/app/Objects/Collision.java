package inf112.skeleton.app.Objects;

import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.List;

public class Collision {
    MyActor actor;
    GridOfTiles grid;

    public Collision(GridOfTiles grid, MyActor actor) {
        this.actor = actor;
        this.grid = grid;
    }

    public void collisionCheck() {
        if (grid == null) return;

        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        List onTile = actorTile.getObjOnTile();

        System.out.println(actorTile);
        for (Object o : onTile) {
            if (o.equals(actor)) continue;
            if (o instanceof Flag) {
                ((Flag) o).handleFlag(actor, grid);
            }
            if (o instanceof BlueTeleport) {
                ((BlueTeleport) o).handleTeleportation(actor, grid);
            }
            if (o instanceof YellowTeleport) {
                ((YellowTeleport) o).handleTeleportation(actor, grid);
            }
            if (o instanceof Hole) {
                ((Hole) o).handleFallingIntoHoles(actor, grid);
                actor.takeDamage(0.1);
            }
            if (o instanceof WrenchesSingle) {
                ((WrenchesSingle) o).handleWrench(actor, grid);
            }
            if (o instanceof WrenchesDouble) {
                ((WrenchesDouble) o).handleWrench(actor, grid);
            }
            if (o instanceof RedConveyorBelt) {
                ((RedConveyorBelt) o).handleConveyorTransport(actor, grid);
            }
            if (o instanceof BlueConveyorBelt) {
                ((BlueConveyorBelt) o).handleConveyorTransport(actor, grid);
            }
        }
    }
}
