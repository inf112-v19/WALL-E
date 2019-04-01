package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

import static inf112.skeleton.app.Objects.ObjectMaker.tilesWithFlags;

public class Flag implements IObject {
    public int y;
    public int x;
    private Sprite sprite;
    Tile flagTile;

    Flag(RectangleMapObject TiledFlag, GridOfTiles grid) {
        y = (int) TiledFlag.getRectangle().getY();
        x = (int) TiledFlag.getRectangle().getX();

        Texture texture = new Texture("Flag.png");
        sprite = new Sprite(texture);
        sprite.setSize(100, 100);

        flagTile = grid.getTileWfloats(y, x);
        flagTile.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        this.sprite = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    void handleFlag(MyActor actor, GridOfTiles grid) {
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        if (flagTile.equals(actorTile)) {
            actor.setBackupTile(flagTile);
            actor.visitedTile(flagTile);
            if (checkFlagsForActor(actor)) {
                actor.gameOver = true;
                System.out.println("Actor visited all flags");
            } else System.out.println("More flags remaining!");
        }
    }

    private boolean checkFlagsForActor(MyActor actor) {
        for (Tile t : tilesWithFlags) {
            if (!actor.tilesVisited.contains(t)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Sprite getSprite() {
        sprite.setY(y);
        sprite.setX(x);
        return sprite;
    }

    public Tile getTile() {
        return this.flagTile;
    }
}
