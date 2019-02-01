package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private float width = 500;
    private float height = 500;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (int i = 0; i <height; i++) {
            font.draw(batch,"|" , i, i);
            for (int j = 0; j <width; j++) {
                font.draw(batch, "          |", i, j);

            }
        }
        batch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();

    }

    public String stringCreator(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < height-1; i++) {
            s.append("|");

            for (int j = 0; j <width-1 ; j++) {
                s.append("_");
            }
        }
        return s.toString();
    }

    //Forslag fra Halvor





}
