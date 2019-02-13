package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Game extends ApplicationAdapter implements InputProcessor {
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer renderer;

    SpriteBatch sb;
    Texture texture;
    Sprite sprite;

    private int nPlayers;
    private ArrayList<Player> players;
    private Deck deck;

    public Game (int nPlayers){
        this.nPlayers = nPlayers;
        players = new ArrayList<Player>();
        deck = new Deck();
    }

    public void run(){
        while (round()) {}
    }


    // Returns true if game is to continue,
    // returns false if someone has won
    public boolean round(){
        // Each player draws their cards
        for (Player p : players)
            p.drawCards(deck);

        // Each player selects their cards in order
        for (Player p : players)
            p.selectCards();

        for (int turn=0; turn<5; turn++)
            turn();

        return false;
    }

    // All players performs one turn
    // (each plays one card)
    public void turn(){
        int[] priorities;
        int CurPriority;
        for (Player p : players)
            CurPriority = p.turn();
        // ...
    }

    // Selects which player plays first,
    // based on the priority of their cards
    public void selectOrder(){
        // ... not yet implemented
    }

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        tiledMap = new TmxMapLoader().load("RoboMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);

        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("robbie.png"));
        sprite = new Sprite(texture);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.viewportHeight = 750;
        camera.viewportWidth = 725;
        camera.update();
        renderer.setView(camera);
        renderer.render();

        // sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(texture,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, 32, 30);
        // sprite.draw(sb);
        sb.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        //Any key on the keyboard is pressed down, not held - and is directed into input

        // trykke en tast, som gjør at en spiller trekker et kort, eller gjør en bevegelse
        return false;
    }


    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            camera.translate(-32, 0);
        if (keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if (keycode == Input.Keys.UP)
            camera.translate(0, 32);
        if (keycode == Input.Keys.DOWN)
            camera.translate(0, -32);
        if (keycode == Input.Keys.U)
            //move robot
            if (keycode == Input.Keys.NUM_1)
                tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);

        Card c = new Deck().handOut();
        if(c.getMoves() >= 0){
            float movement = 32*c.getMoves();
            position.x += movement;
            sprite.setPosition(position.x, position.y);
        }
        return true;
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
