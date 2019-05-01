package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;
import java.util.ArrayList;

public class MyLaser implements IObject {
    private final Texture texture;
    private final SpriteBatch laserBatch;
    private GridOfTiles grid;
    private float velocity;
    private int shotLength;
    private Sprite sprite;
    private Tile laserTile;
    private MyActor actor;
    public ArrayList<Sprite> renderArray;

    public MyLaser(GridOfTiles grid, MyActor actor, Tile initialTileForLaser, float initialVelocity, int shotLength){
        this.grid = grid;
        this.actor = actor;
        this.laserTile = initialTileForLaser;
        this.velocity = initialVelocity;
        this.shotLength = shotLength;

        this.renderArray = new ArrayList<>();
        this.texture = new Texture("greenLaser.png");
        this.sprite = new Sprite(new Texture("greenLaser.png"));
        this.laserBatch = new SpriteBatch();
    }

    public void shootLaser(){
        MyGame.Dir shotDir;
        ArrayList<Tile> tilesToShootOn = new ArrayList<>();
        if (this.laserTile.getObjOnTile().contains(this.actor)){
            shotDir=actor.getDir();
            tilesToShootOn = getTilesInDirection(shotDir);
        }
        for (Tile t : tilesToShootOn) {
            //System.out.println("Laser passes; " + t);
            storeToRenderArray(t.getY(), t.getX());
            if (!t.getObjOnTile().isEmpty()){
                for (IObject object : t.getObjOnTile()) {
                    if (object instanceof MyActor){
                        System.out.println("Laser shot by " + actor.getName()+" hit: " + ((MyActor) object).getName());
                        ((MyActor) object).takeDamage(0.2);
                    }
                }
            }
        }


    }

    private void storeToRenderArray(float y, float x) {
        Sprite toStore = this.sprite;
        toStore.setY(y);
        toStore.setX(x);
        this.renderArray.add(toStore);
    }

    public ArrayList<Tile> getTilesInDirection(MyGame.Dir dir){
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
        return this.sprite;
    }

    @Override
    public Tile getTile() {
        return this.laserTile;
    }

    public void render(Sprite sprite, float x, float y) {
        laserBatch.begin();
        laserBatch.draw(sprite.getTexture(), x, y);
        laserBatch.end();
    }

    public ArrayList<Sprite> getRenderArray() {
        return this.renderArray;
    }
}
