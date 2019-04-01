package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class BlueConveyorBelt implements IObject {
    public MyGame.Dir conveyorDirection;
    Sprite notUsedForConveyors;
    public int y;
    public int x;
    static Tile blueConvTile;
    int conveyorVelocity;


    public BlueConveyorBelt(RectangleMapObject TiledBlueConveyor, GridOfTiles grid, int velocity){
        y = (int) TiledBlueConveyor.getRectangle().getY();
        x = (int) TiledBlueConveyor.getRectangle().getX();

        blueConvTile = grid.getTileWfloats(y, x);
        blueConvTile.isConveyor = true;
        blueConvTile.addObjOnTile(this);
        conveyorDirection = getConveyorDirection(TiledBlueConveyor);
        velocity = this.conveyorVelocity;
    }

    public void remove(GridOfTiles grid) {
        this.notUsedForConveyors = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    public void handleConveyorTransport(MyActor actor, GridOfTiles grid) {
        int toMove = grid.pxSize;
        MyGame.Dir oldDirectionForActor = actor.getDir();
        actor.moveInDirection(toMove, conveyorDirection, grid);
        actor.setDir(oldDirectionForActor);
    }

    public MyGame.Dir getConveyorDirection(RectangleMapObject conveyor){
        String directionFromTile = (String) conveyor.getProperties().get("direction");
        switch (directionFromTile){
            case "NORTH" : return MyGame.Dir.NORTH;
            case "SOUTH" : return MyGame.Dir.SOUTH;
            case "WEST" : return MyGame.Dir.WEST;
            case "EAST" : return MyGame.Dir.EAST;
        }
        return null;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public  Tile getTile() {
        return this.blueConvTile;
    }
}

