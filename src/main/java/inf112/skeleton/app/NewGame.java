package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import inf112.skeleton.app.Actor.Actor;
import inf112.skeleton.app.CardFunctionality.Deck;
import inf112.skeleton.app.Map.MapRenderer;
import inf112.skeleton.app.Map.Objects;

import java.util.ArrayList;

public class NewGame extends ApplicationAdapter implements InputProcessor {
    public static OrthographicCamera camera;
    public Actor actor;
    public Objects flag;
    private int nPlayers;
    private ArrayList<Actor> players;
    private Deck deck;
    public MapRenderer mapRenderer;

    public NewGame(int nPlayers) {
        this.nPlayers = nPlayers;
        actor = new Actor();
        flag = new Objects();
        camera = new OrthographicCamera();
        players = new ArrayList<>();
        mapRenderer = new MapRenderer();
        deck = new Deck();
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.position.x = 1800/2;
        camera.position.y = 1800/2;
        camera.update();

        // Map
        mapRenderer.create();

        // Actor
        actor.create();

        //Objects
        flag.create();


        // Cards
        // TODO: add cards
       //if(actor.viewRender) actor.view.create();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.viewportHeight = 4000;
        camera.viewportWidth = 5000;
        camera.update();

        mapRenderer.render();
        flag.render();
        actor.render();

        //if(actor.viewRender) actor.renderCard();
    }

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
