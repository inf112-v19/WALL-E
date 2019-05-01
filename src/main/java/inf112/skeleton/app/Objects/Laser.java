package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;
import inf112.skeleton.app.Objects.IObject;

import java.util.ArrayList;

public class Laser implements IObject{
    public MyActor actorWhoShot;
    public boolean active;
    public int length;

    public int y;
    public int x;
    private Sprite laserSprite;
    public ArrayList<Tile> laserTiles;

    public Laser(boolean active, MyActor actor, GridOfTiles grid, MyGame.Dir direction){
        this.active = active;
        if (this.active) {
            this.actorWhoShot = actor;
            y = actorWhoShot.getTile().getY();
            x = actorWhoShot.getTile().getX();
            this.length = getLengthWithDirection(actorWhoShot, grid, direction);

            Texture texture = new Texture("greenLaser.png");
            laserSprite = new Sprite(texture);
            laserSprite.setSize(100, 100);

            for (Tile laserTile : laserTiles) {
                laserTile.addObjOnTile(this);
            }
        }
    }

    private int getLengthWithDirection(MyActor actor, GridOfTiles grid, MyGame.Dir direction) {
        actor = actorWhoShot;
        int length = 0;
        switch (direction){
            case NORTH:
                for (int i = actor.getTile().getY(); i < grid.Ncol ; i++) {
                    laserTiles.add(grid.getTile(y, i));
                    length++;
                }
            case SOUTH:
                for (int i = actor.getTile().getY(); i > 0; i++) {
                    laserTiles.add(grid.getTile(y, i));
                    length++;
                }
            case WEST:
                for (int i = actor.getTile().getX(); i > 0; i++) {
                    laserTiles.add(grid.getTile(i, x));
                    laserSprite.rotate(90);
                    length++;
                }
            case EAST:
                for (int i = actor.getTile().getX(); i < grid.Nrow ; i++) {
                    laserTiles.add(grid.getTile(i, x));
                    laserSprite.rotate(90);
                    length++;
                }
        }
        return length;
    }

    public void remove(GridOfTiles grid) {
        this.laserSprite = null;
        Tile tile = grid.getTileWfloats(this.y, this.x);
        tile.getObjOnTile().remove(this);
    }

    public void handleLaser(MyActor actor, GridOfTiles grid){
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        for (Tile laserTile : laserTiles) {
            if (laserTile.equals(actorTile)){
                if (!actor.equals(actorWhoShot)) continue;
                actor.takeDamage(0.1);
            }
        }
    }

    @Override
    public Sprite getSprite() {
        return laserSprite;
    }

    @Override
    public Tile getTile() {
        //origin of shot
        return laserTiles.get(0);
    }


}