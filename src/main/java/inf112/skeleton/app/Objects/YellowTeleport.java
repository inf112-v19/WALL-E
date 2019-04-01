package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class YellowTeleport implements IObject {
    public int y;
    public int x;
    Sprite notUsedForTeleports;
    Tile yTeleportTileFrom;

    public YellowTeleport(RectangleMapObject TiledYellowTeleport, GridOfTiles grid) {
        y = (int) TiledYellowTeleport.getRectangle().getY();
        x = (int) TiledYellowTeleport.getRectangle().getX();

        yTeleportTileFrom = grid.getTileWfloats(y, x);
        yTeleportTileFrom.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        this.notUsedForTeleports = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    public void handleTeleportation(MyActor actor, GridOfTiles grid) {
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        if (yTeleportTileFrom.equals(actorTile)) {
            ObjectMaker.yellowTeleports.remove(this);
            YellowTeleport to = (YellowTeleport) ObjectMaker.yellowTeleports.get(0);
            actor.setX(to.x);
            actor.setY(to.y);
            System.out.println("Actor teleported from " + yTeleportTileFrom + " to " + to.getTile());
            ObjectMaker.yellowTeleports.add(this);
        }
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    public Tile getTile() {
        return this.yTeleportTileFrom;
    }
}
