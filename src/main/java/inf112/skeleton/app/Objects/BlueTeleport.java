package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class BlueTeleport implements IObject {
    public int y;
    public int x;
    Sprite notUsedForTeleports;
    Tile bTeleportTileFrom;

    public BlueTeleport(RectangleMapObject TiledBlueTeleport, GridOfTiles grid) {
        y = (int) TiledBlueTeleport.getRectangle().getY();
        x = (int) TiledBlueTeleport.getRectangle().getX();

        bTeleportTileFrom = grid.getTileWfloats(y, x);
        bTeleportTileFrom.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        this.notUsedForTeleports = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    public void handleTeleportation(MyActor actor, GridOfTiles grid) {
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        if (bTeleportTileFrom.equals(actorTile)) {
            ObjectMaker.blueTeleports.remove(this);
            BlueTeleport to = (BlueTeleport) ObjectMaker.blueTeleports.get(0);
            actor.setX(to.x);
            actor.setY(to.y);
            System.out.println("Actor teleported from " + bTeleportTileFrom + " to " + to.getTile());
            ObjectMaker.blueTeleports.add(this);
        }
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    public Tile getTile() {
        return this.bTeleportTileFrom;
    }
}
