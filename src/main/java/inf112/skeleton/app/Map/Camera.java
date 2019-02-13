package inf112.skeleton.app.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera extends ApplicationAdapter implements InputProcessor {

    OrthographicCamera camera;

    public Camera() {
        this.camera =  new OrthographicCamera();
    }



    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera.setToOrtho(false, w, h);



    }

    @Override
    public void render() {
        camera.viewportHeight = 2024;
        camera.viewportWidth = 2024;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT) {
            camera.translate(-32, 0);
        }
        if(keycode == Input.Keys.RIGHT) {
            camera.translate(32, 0);
        }
        if(keycode == Input.Keys.UP) {
            camera.translate(0, 32);
        }
        if(keycode == Input.Keys.DOWN) {
            camera.translate(0, -32);
        }
        return false;
    }



    @Override
    public boolean keyUp(int i) {
        return false;
    }



    @Override
    public boolean keyTyped(char c) {
        return false;
    }



    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
