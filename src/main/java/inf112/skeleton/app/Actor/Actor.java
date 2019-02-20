package inf112.skeleton.app.Actor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;
import inf112.skeleton.app.CardFunctionality.Card;

import java.util.ArrayList;

public class Actor extends ApplicationAdapter implements InputProcessor {

    //Get cards, place in handout (From deck.handout
    ArrayList<Card> handout = new ArrayList<>(9);

    //Objectify and visualize all cards - then make it possible to choose

    //choose cards with keypad 1-9 / vector and add iteratively to chosen arraylist
    ArrayList<Card> chosen = new ArrayList<>(5);




    @Override
    public boolean keyDown(int keycode) {
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
