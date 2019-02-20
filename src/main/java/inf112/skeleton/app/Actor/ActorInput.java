package inf112.skeleton.app.Actor;

import com.badlogic.gdx.InputProcessor;

public class ActorInput implements InputProcessor {




    @Override
    public boolean keyDown(int keycode) {
        //Hvis du trykker K, får du et nytt kort
        /*
        Implementere en måte å få en ny handout av et kort
         */
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
