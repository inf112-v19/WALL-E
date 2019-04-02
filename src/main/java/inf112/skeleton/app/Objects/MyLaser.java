package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class MyLaser implements IObject {
    private float velocity;
    private int shotLength;
    private Sprite sprite;
    private Tile laserTile;
    private MyActor actor;

    public MyLaser(MyActor actor, Tile initialTileForLaser, Sprite sprite, float initialVelocity, int shotLength){
        this.actor = actor;
        this.laserTile = initialTileForLaser;
        this.sprite = sprite;
        this.velocity = initialVelocity;
        this.shotLength = shotLength;
    }

    public void shootLaser(){
        if (this.laserTile.getObjOnTile().contains(MyActor actor))
    }

    @Override
    public Sprite getSprite() {
        return this.sprite;
    }

    @Override
    public Tile getTile() {
        return this.laserTile;
    }
}
