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

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class Actor extends ApplicationAdapter implements InputProcessor {
    ArrayList<Card> handout = new ArrayList<>(9);
    public float actorLeft;
    public float actorRight;
    public float actorTop;
    public float actorBottom;
    ArrayDeque<Card> chosen = new ArrayDeque<>(5);
    private Deck deck = new Deck();
    private Batch batch;
    private Texture aTexture;
    private com.badlogic.gdx.scenes.scene2d.Actor actor = new com.badlogic.gdx.scenes.scene2d.Actor();
    private boolean rendered = false;
    public boolean viewRender = false;
    public Card view;

    float getX() {
        return actor.getX();
    }

    float getY() {
        return actor.getY();
    }

    void chooseCard(int i) {
        Card card = handout.get(i);
        handout.remove(i);
        chosen.addFirst(card);
        while (chosen.size() > 5) {
            Card deletedCard = chosen.removeLast();
            handout.add(deletedCard);
        }
    }

    void handOut() {
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

        // Get cards, place in handout (From Deck.handout)
        handOut();
    }

    @Override
    public void render() {
        rendered = true;
        int middleWidth = Gdx.graphics.getWidth() / 2;
        int middleHeight = Gdx.graphics.getHeight() / 2;

        actorBottom = actor.getY();
        actorLeft = actor.getX();
        actorTop = actor.getY() + 70;
        actorRight = actor.getX() + 100;

        batch.begin();
        batch.draw(aTexture, middleWidth + actor.getX(), middleHeight + actor.getY(), 100, 80);
        actor.draw(batch, 1);
        batch.end();
        // TODO: Render card
    }

    public ArrayDeque getChosen() {
        return chosen;
    }

    // Actor Input
    @Override
    public boolean keyDown(int keycode) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if (!rendered && width == 0 && height == 0) {
            width = 1000;
            height = 1000;
        }

        int middleWidth = width / 2;
        int middleHeight = height / 2;

        int speedWidth = 142;
        int speedHeight = 78;


        if (keycode == Input.Keys.LEFT) {
            middleWidth -= speedWidth;

            if (middleWidth + actorLeft < 0) {
                actor.moveBy(0, 0);
                middleWidth += speedWidth;
            } else {
                actor.moveBy(-speedWidth, 0);
            }
        }

        if (keycode == Input.Keys.RIGHT) {
            middleWidth += speedWidth;
            if (middleWidth + actorRight > width) {
                actor.moveBy(0, 0);
                middleWidth -= speedWidth;
            } else {
                actor.moveBy(speedWidth, 0);
            }
        }

        if (keycode == Input.Keys.ENTER) {
            /*int i = 0;
            view = handout.get(i);
            viewRender = true;
            if (keyDown(Input.Keys.DOWN)) {
                chooseCard(i);
                viewRender = false;
            }*/
            if (handout.size() > 0) {
                Card action = handout.get(handout.size() - 1);
                handout.remove(handout.size() - 1);
                String type = getType(action);
                if (type == "Move") {
                    System.out.println("Actor should move by: " + action.getMoves());
                    float move = speedWidth * action.getMoves();
                    actor.moveBy(move, 0);
                } else if (type == "Backup") {
                    System.out.println("Actor should back up by: " + action.getMoves());
                    float backup = speedWidth * action.getMoves();
                    actor.moveBy(-backup, 0);
                } else {
                    System.out.println("It was a turn card");
                }
            } else {
                System.out.println("No cards left in handout");
            }

        }

        if (keycode==Input.Keys.BACKSPACE){
            String s = "Cards in handout: ";
            for (int i = 0; i <handout.size() ; i++) {
                Card c = handout.get(i);
                 s += getType(c) + "-" + c.getMoves() +", ";
            }
            System.out.println(s);
        }

        if (keycode == Input.Keys.UP) {
            middleHeight += speedHeight;
            if (middleHeight + actorTop > height) {
                actor.moveBy(0, 0);
                middleHeight -= speedHeight;
            } else {
                actor.moveBy(0, speedHeight);
            }

            //Card action = chosen.pop();
        }

        if (keycode == Input.Keys.DOWN) {
            middleHeight -= speedHeight;
            if (middleHeight + actorBottom < 0) {
                actor.moveBy(0, 0);
                middleHeight += speedHeight;
            } else {
                actor.moveBy(0, -speedHeight);
            }
        }

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

    public void renderCard() {
        view.render();
    }
}