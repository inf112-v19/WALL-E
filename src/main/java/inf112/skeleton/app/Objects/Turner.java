package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class Turner implements IObject {
    public int y;
    public int x;
    Tile turnerTile;

    Turner(RectangleMapObject TiledTurner, GridOfTiles grid) {
        y = (int) TiledTurner.getRectangle().getY();
        x = (int) TiledTurner.getRectangle().getX();

        turnerTile = grid.getTileWfloats(y, x);
        turnerTile.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    void handleTurner(MyActor actor, GridOfTiles grid) {
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        if (turnerTile.equals(actorTile)) {
            actor.turnRight();
        }
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public Tile getTile() {
        return this.turnerTile;
    }
}
