
package inf112.skeleton.app.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.Objects.Actor.MyActor;


public class Laser {
    Sprite sprite;
    public static int SPEED = 0;
    private static Texture texture;
    public SpriteBatch laserBatch;
    public MyActor actor;


    float x ;
    float y ;

    public boolean remove = false;

    public Laser(MyActor actor, float x, float y) {
        this.x = x;
        this.y = y;
        this.actor = actor;

        if(texture == null) {
            texture = new Texture("greenLaser.png");
        }
        texture = new Texture("greenLaser.png");
        this.sprite = new Sprite(texture);
        this.sprite.setCenter((this.actor.getSprite().getWidth()/2), (this.actor.getSprite().getHeight()/2));

        laserBatch = new SpriteBatch();
    }

    public void setX(float x){
        this.x=x;
    }
    public void setY(float y){
        this.y = y;
    }

    public void rotateAroundActor(float x, float y, float rotation){
        this.sprite.rotate(rotation);
        //this.sprite.setRotation(rotation);
        this.sprite.setPosition(x, y);
    }

    public void update(float delta) {
        x += SPEED + delta;
        if (x > Gdx.graphics.getWidth() || y > Gdx.graphics.getHeight()) {
            remove = true;
        }
    }

    public void render() {
        laserBatch.begin();
        laserBatch.draw(texture, x, y);
        laserBatch.end();
    }


}
