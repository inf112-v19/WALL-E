package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class RedConveyorBelt implements IObject {
    Sprite notUsedForConveyors;
    public int y;
    public int x;
    Tile redConvTile;

    public RedConveyorBelt(RectangleMapObject TiledRedConveyor, GridOfTiles grid){
        y = (int) TiledRedConveyor.getRectangle().getY();
        x = (int) TiledRedConveyor.getRectangle().getX();

        redConvTile = grid.getTileWfloats(y, x);
        redConvTile.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        this.notUsedForConveyors = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    public void handleConveyorTransport(MyActor actor, GridOfTiles grid){
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public Tile getTile() {
        return null;
    }
}
