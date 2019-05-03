package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class WrenchesSingle implements IObject {
    public int y;
    public int x;
    Tile wrenchTile;

    public WrenchesSingle(RectangleMapObject TiledWrench, GridOfTiles grid) {
        y = (int) TiledWrench.getRectangle().getY();
        x = (int) TiledWrench.getRectangle().getX();

        wrenchTile = grid.getTileWfloats(y, x);
        wrenchTile.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    void handleWrench(MyActor actor, GridOfTiles grid) {
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        if (wrenchTile.equals(actorTile)) {
            actor.restoreHealth(0.25);
            actor.setBackupTile(wrenchTile);
        }
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public Tile getTile() {
        return this.wrenchTile;
    }
}
