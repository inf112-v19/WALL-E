package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class Scene implements ApplicationListener {

    public class MyActor extends Actor {
        Texture skateboard = new Texture(Gdx.files.internal("images/skateboard.png"));
        Texture tile = new Texture(Gdx.files.internal("images/tile_2.png"));

        @Override
        public void draw(Batch batch, float alpha) {


            for (int i = 0; i < 480; i += 20) {
                for (int j = 0; j < 320; j += 20) {
                    batch.draw(tile, i, j);
                }
            }
            batch.draw(skateboard, 240, 166);
        }
    }

        private Stage stage;

        @Override
        public void create() {
            stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

            MyActor myActor = new MyActor();
            stage.addActor(myActor);
        }

        @Override
        public void resize(int width, int heigth) {

        }

        @Override
        public void render() {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void dispose() {
            stage.dispose();

        }
    }

