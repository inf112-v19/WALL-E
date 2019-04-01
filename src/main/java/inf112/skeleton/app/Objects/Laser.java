
package inf112.skeleton.app.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.Objects.Actor.MyActor;


public class Laser {
    Sprite sprite;
    public static final int SPEED = 400;
    private static Texture texture;


    float x ;
    float y ;

    public boolean remove = false;

    public Laser(float x, float y) {
        this.x = x;
        this.y = y;

        if(texture == null) {
            texture = new Texture("greenLaser.png");
        }
    }

    public void update(float delta) {
        x+= SPEED+delta;
        if(x> Gdx.graphics.getWidth() || y>Gdx.graphics.getHeight()) {
            remove = true;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }


}
