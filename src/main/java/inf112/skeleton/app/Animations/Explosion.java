package inf112.skeleton.app.Animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    public static final float FRAME_DUR = 0.15f;
    public static final int SIZE = 32;
    public static final int OFFSET = 8;

    public static Animation anim = null;
    float x,y;
    float stateTime;
    public boolean remove =  false;

    public Explosion(float x, float y){
        this.x = x-OFFSET;
        this.y = y-OFFSET;
        stateTime = 0;

        if (anim == null){
            anim = new Animation(FRAME_DUR, TextureRegion.split(new Texture("explosion.png"),SIZE,SIZE)[0]);
        }
    }

    public void update (float deltaTime) {
        stateTime += deltaTime;
        if(anim.isAnimationFinished(stateTime)){
            remove = true;
        }
    }

    public void render (SpriteBatch batch){
        batch.draw((TextureRegion) anim.getKeyFrame(stateTime),x,y,200,200);
    }
}
