package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class BlueConveyorBelt implements IObject{
    Sprite notUsedForConveyors;
    public int y;
    public int x;
    Tile blueConvTile;

    public BlueConveyorBelt(RectangleMapObject TiledBlueConveyor, GridOfTiles grid){
        y = (int) TiledBlueConveyor.getRectangle().getY();
        x = (int) TiledBlueConveyor.getRectangle().getX();

        blueConvTile = grid.getTileWfloats(y, x);
        blueConvTile.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        this.notUsedForConveyors = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    public void handleConveyorTransport(MyActor actor, GridOfTiles grid){

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
