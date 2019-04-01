package inf112.skeleton.app.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Map.Maps;

public class MenuScreen implements Screen {

    private float WIDTH;
    private float HEIGHT;

    private float EXIT_BUTTON_X;
    private float EXIT_BUTTON_y;
    private float EXIT_BUTTON_HEIGHT;
    private float EXIT_BUTTON_WIDTH;

    private float PLAY_BUTTON_X;
    private float PLAY_BUTTON_y;
    private float PLAY_BUTTON_HEIGHT;
    private float PLAY_BUTTON_WIDTH;

    RoboRally game;
    private SpriteBatch batch;
    private Texture playInAct;
    private Texture playAct;
    private Texture exitInAct;
    private Texture exitAct;
    private Texture mapInAct;
    private Texture mapAct;
    private Texture mapChoice;


    private int POSSIBLE_MAPCHOICES = 1;
    private static int MAP_CHOICE = 0;
    private float MAPCHOICE_BUTTON_X;
    private float MAPCHOICE_BUTTON_y;
    private float MAPCHOICE_BUTTON_HEIGHT;
    private float MAPCHOICE_BUTTON_WIDTH;


    public MenuScreen(RoboRally game) {
        this.game = game;

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        playInAct = new Texture(Gdx.files.internal("play_inactive.png"));
        playAct = new Texture(Gdx.files.internal("play_active.png"));
        exitInAct = new Texture(Gdx.files.internal("exit_inactive.png"));
        exitAct = new Texture(Gdx.files.internal("exit_active.png"));
        mapInAct = new Texture(Gdx.files.internal("map_inactive.png"));
        mapAct = new Texture(Gdx.files.internal("map_active.png"));
        batch = new SpriteBatch();


        PLAY_BUTTON_X = (WIDTH / 2) - (playInAct.getWidth() / 2);
        PLAY_BUTTON_y = HEIGHT - (HEIGHT / 4);
        PLAY_BUTTON_HEIGHT = playAct.getHeight();
        PLAY_BUTTON_WIDTH = playAct.getWidth();

        EXIT_BUTTON_X = (WIDTH / 2) - (exitInAct.getWidth() / 2);
        EXIT_BUTTON_y = HEIGHT - (HEIGHT / 4) * 2;
        EXIT_BUTTON_HEIGHT = exitAct.getHeight();
        EXIT_BUTTON_WIDTH = exitAct.getWidth();

        MAPCHOICE_BUTTON_X = (WIDTH / 2) - (mapInAct.getWidth() / 2);
        MAPCHOICE_BUTTON_y = HEIGHT - (HEIGHT / 4) * 3;
        MAPCHOICE_BUTTON_HEIGHT = mapAct.getHeight();
        MAPCHOICE_BUTTON_WIDTH = mapAct.getWidth();
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Play button
        if (Gdx.input.getX() > PLAY_BUTTON_X
                && Gdx.input.getX() < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH
                && HEIGHT - Gdx.input.getY() < PLAY_BUTTON_y + PLAY_BUTTON_HEIGHT
                && HEIGHT - Gdx.input.getY() > PLAY_BUTTON_y) {
            batch.draw(playAct, PLAY_BUTTON_X, PLAY_BUTTON_y - 1);
            batch.draw(exitInAct, EXIT_BUTTON_X, EXIT_BUTTON_y);
            batch.draw(mapInAct, MAPCHOICE_BUTTON_X, MAPCHOICE_BUTTON_y);
            if (Gdx.input.isTouched()) {
                setGameScreen();
            }

            //Exit button
        } else if (Gdx.input.getX() > EXIT_BUTTON_X
                && Gdx.input.getX() < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH
                && HEIGHT - Gdx.input.getY() < EXIT_BUTTON_y + EXIT_BUTTON_HEIGHT
                && HEIGHT - Gdx.input.getY() > EXIT_BUTTON_y) {
            batch.draw(playInAct, PLAY_BUTTON_X, PLAY_BUTTON_y);
            batch.draw(exitAct, EXIT_BUTTON_X, EXIT_BUTTON_y - 1);
            batch.draw(mapInAct, MAPCHOICE_BUTTON_X, MAPCHOICE_BUTTON_y);
            if (Gdx.input.isTouched()) {
                this.dispose();
                Gdx.app.exit();
            }
            //MapChoice
        } else if (Gdx.input.getX() > MAPCHOICE_BUTTON_X
                && Gdx.input.getX() < MAPCHOICE_BUTTON_X + MAPCHOICE_BUTTON_WIDTH
                && HEIGHT - Gdx.input.getY() < MAPCHOICE_BUTTON_y + MAPCHOICE_BUTTON_HEIGHT
                && HEIGHT - Gdx.input.getY() > MAPCHOICE_BUTTON_y) {
            batch.draw(playInAct, PLAY_BUTTON_X, PLAY_BUTTON_y);
            batch.draw(exitInAct, EXIT_BUTTON_X, EXIT_BUTTON_y);
            batch.draw(mapAct, MAPCHOICE_BUTTON_X, MAPCHOICE_BUTTON_y - 1);
            if (Gdx.input.justTouched()) {
                this.setMapChoice();
                System.out.println("Should load map: " + getMAP_CHOICE());
            }
        } else {
            batch.draw(playInAct, PLAY_BUTTON_X, PLAY_BUTTON_y);
            batch.draw(exitInAct, EXIT_BUTTON_X, EXIT_BUTTON_y);
            batch.draw(mapInAct, MAPCHOICE_BUTTON_X, MAPCHOICE_BUTTON_y);
        }


        batch.end();

    }

    public void setGameScreen() {
        MyGame gameScreen = new MyGame(game);
        gameScreen.create();
        game.setScreen(gameScreen);
    }

    public void setMapChoice() {
        MAP_CHOICE++;
        if (MAP_CHOICE > POSSIBLE_MAPCHOICES) MAP_CHOICE = 0;
    }

    public static int getMAP_CHOICE() {
        return MAP_CHOICE;
    }


    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
