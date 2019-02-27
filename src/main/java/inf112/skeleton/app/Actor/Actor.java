package inf112.skeleton.app.Actor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Actor extends ApplicationAdapter implements InputProcessor {
    private Deck deck = new Deck();
    private ArrayList<Card> handout = new ArrayList<>(9);

    // Objectify and visualize all cards - then make it possible to choose
    private ArrayDeque<Card> chosen = new ArrayDeque<>(5);

    private Batch batch;
    private Texture aTexture;
    private com.badlogic.gdx.scenes.scene2d.Actor actor;

    public Actor(){
    }

    private void chooseCard(int i) {
        Card card = handout.get(i);
        handout.remove(i);
        chosen.addFirst(card);
        while (chosen.size() > 5) {
            Card deletedCard = chosen.removeLast();
            handout.add(deletedCard);
        }
    }

    private void handOut() {
        handout.clear();
        for (int i = 0; i < 9; i++) {
            handout.add(deck.handOut());
        }
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);

        // Sprite
        batch = new SpriteBatch();
        aTexture = new Texture(Gdx.files.internal("robbie.png"));
        actor = new com.badlogic.gdx.scenes.scene2d.Actor();

        // Get cards, place in handout (From Deck.handout)
        handOut();
    }

    @Override
    public void render() {
        int middleWidth = Gdx.graphics.getWidth()/2;
        int middleHeight = Gdx.graphics.getHeight()/2;

        batch.begin();
        batch.draw(aTexture, middleWidth+actor.getX(), middleHeight+actor.getY(), 100, 80);
        actor.draw(batch, 1);
        batch.end();
        //chosen.getLast().render();
    }




    //Actor Input
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) actor.moveBy(-32, 0);
        if (keycode == Input.Keys.RIGHT) actor.moveBy(32, 0);
        if (keycode == Input.Keys.UP) actor.moveBy(0, 32);
        if (keycode == Input.Keys.DOWN) actor.moveBy(0, -32);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // choose cards with keypad 1-9 / vector and add iteratively to chosen arraylist
        if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            chooseCard(keycode);
        }

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
