package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Animations.Explosion;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class Hole implements IObject {
    public int y;
    public int x;
    private Sprite notUsedForHoles;
    Tile hole;
    SpriteBatch sb;

    Hole(RectangleMapObject TiledHole, GridOfTiles grid) {
        y = (int) TiledHole.getRectangle().getY();
        x = (int) TiledHole.getRectangle().getX();

        hole = grid.getTileWfloats(y, x);
        hole.addObjOnTile(this);
    }

    public void remove(GridOfTiles grid) {
        this.notUsedForHoles = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    void handleFallingIntoHoles(MyActor actor, GridOfTiles grid) {
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        Tile actorFellFrom = actor.getPreviousTile();
        if (hole.equals(actorTile)) {
            System.out.println("Actor fell into a hole! HP lost: ");
            actor.explosions.add(new Explosion(actor.getX(), actor.getY()));
            actor.takeDamage(0.1);

            if (actor.getBackupTile() != null) {
                actor.backToBackup(grid);
            } else {
                System.out.println("This doesn't work, would like it to tho..");
                System.out.println("Actor had no backup, started from previous tile: " + actorFellFrom);
                actor.setPosition(actorFellFrom.y, actorFellFrom.x, grid);
            }
        }
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
