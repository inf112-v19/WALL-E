package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;
import java.util.ArrayList;

public class MyLaser implements IObject {
    private int y;
    private int x;
    private GridOfTiles grid;
    public Tile laserTile;
    private MyActor actor;

    public MyLaser(GridOfTiles grid, MyActor actor, Tile initialTileForLaser, float initialVelocity, int shotLength){
        this.grid = grid;
        this.actor = actor;
        this.laserTile = initialTileForLaser;
    }

    MyLaser(RectangleMapObject TiledLaser, GridOfTiles grid){
        y = (int) TiledLaser.getRectangle().getY();
        x = (int) TiledLaser.getRectangle().getX();

        laserTile = grid.getTileWfloats(y, x);
        laserTile.addObjOnTile(this);
    }

    public void handleLaser(MyActor actor, GridOfTiles grid){
        Tile actorTile = grid.getTileWfloats(actor.getY(), actor.getX());
        if (laserTile.equals(actorTile)) {
            actor.takeDamage(0.1);
        }
    }

    public void shootLaser(){
        MyGame.Dir shotDir;
        ArrayList<Tile> tilesToShootOn = new ArrayList<>();
        if (this.laserTile.getObjOnTile().contains(this.actor)){
            shotDir=actor.getDir();
            tilesToShootOn = getTilesInDirection(shotDir);
        }
        for (Tile t : tilesToShootOn) {
            if (!t.getObjOnTile().isEmpty()){
                for (IObject object : t.getObjOnTile()) {
                    if (object instanceof MyActor){
                        System.out.println("Laser shot by " + actor.getName() +" hit: " + ((MyActor) object).getName());
                        ((MyActor) object).takeDamage(0.2);
                    }
                }
            }
        }
    }

    private ArrayList<Tile> getTilesInDirection(MyGame.Dir dir){
        ArrayList<Tile> remainingTiles = new ArrayList<>();
        int x = laserTile.getX();
        int y = laserTile.getY();

        switch (dir){
            case EAST:
                for (int i = x+1; i <grid.Nrow ; i++) {
                    remainingTiles.add(grid.getTile(y, i));
                }
                break;
            case WEST:
                for (int i = x-1; i >= 0 ; i--) {
                     remainingTiles.add(grid.getTile(y, i));
                }
                break;
            case SOUTH:
                for (int i = y-1; i >= 0; i--) {
                    remainingTiles.add(grid.getTile(i, x));
                }
                break;
            case NORTH:
                for (int i = y+1; i < grid.Ncol; i++) {
                    remainingTiles.add(grid.getTile(i, x));
                }
                break;
        }
        return remainingTiles;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public Tile getTile() {
        return this.laserTile;
    }

}
