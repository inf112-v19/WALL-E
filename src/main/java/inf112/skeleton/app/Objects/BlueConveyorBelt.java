package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class BlueConveyorBelt implements IObject {
    private static Tile blueConvTile;
    private MyGame.Dir conveyorDirection;
    public int y;
    public int x;
    private Sprite notUsedForConveyors;
    private int conveyorVelocity;


    BlueConveyorBelt(RectangleMapObject TiledBlueConveyor, GridOfTiles grid, int velocity) {
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

    void handleConveyorTransport(MyActor actor, GridOfTiles grid) {
        int toMove = grid.pxSize;
        MyGame.Dir oldDirectionForActor = actor.getDir();
        actor.moveInDirection(toMove, conveyorDirection, grid);
        actor.setDir(oldDirectionForActor);
    }

    public Tile checkForAdjacentConveyors(GridOfTiles grid) {
        Tile above = grid.getTileWfloats(blueConvTile.y + 1, blueConvTile.x);
        Tile below = grid.getTileWfloats(blueConvTile.y - 1, blueConvTile.x);
        Tile left = grid.getTileWfloats(blueConvTile.y, blueConvTile.x - 1);
        Tile right = grid.getTileWfloats(blueConvTile.y, blueConvTile.x + 1);

        if (above.isConveyor) {
            conveyorDirection = MyGame.Dir.NORTH;
            return above;
        } else if (below.isConveyor) {
            conveyorDirection = MyGame.Dir.SOUTH;
            return below;
        } else if (left.isConveyor) {
            conveyorDirection = MyGame.Dir.WEST;
            return left;
        } else if (right.isConveyor) {
            conveyorDirection = MyGame.Dir.EAST;
            return right;
        } else return null;
    }

    private MyGame.Dir getConveyorDirection(RectangleMapObject conveyor) {
        String directionFromTile = (String) conveyor.getProperties().get("direction");
        switch (directionFromTile) {
            case "NORTH":
                return MyGame.Dir.NORTH;
            case "SOUTH":
                return MyGame.Dir.SOUTH;
            case "WEST":
                return MyGame.Dir.WEST;
            case "EAST":
                return MyGame.Dir.EAST;
        }
        return null;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public Tile getTile() {
        return blueConvTile;
    }
}

