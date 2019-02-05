package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;

public class Physics extends ApplicationAdapter {

    SpriteBatch batch;
    Sprite sprite;
    Texture img;
    World world;
    Body Body;

    @Override
    public void create(){
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);

        //Center to middle of screen
        sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2, Gdx.graphics.getHeight());

        //Create a physics world, where the vector passed in is gravity
        world = new World(new Vector2(0, .98f), true);
    }
}
