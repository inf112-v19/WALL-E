package inf112.skeleton.app.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen implements Screen {
    private static final int HEIGHT = Gdx.graphics.getHeight();
    private static final int WIDTH = Gdx.graphics.getWidth();
    private RoboRally game;
    private BitmapFont font;
    private SpriteBatch batch;
    private String winner;
    private Texture tanks;
    private Texture victory;
    private Texture defeat;

    GameOverScreen(RoboRally game, String winner) {
        this.game = game;
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.winner = winner;
        tanks = new Texture(Gdx.files.internal("victory-tanks.png"));
        victory = new Texture(Gdx.files.internal("Victory.png"));
        defeat = new Texture(Gdx.files.internal("defeat.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if(this.winner=="Computer"){
            batch.draw(defeat,WIDTH/2-victory.getWidth()/2,HEIGHT/2+(HEIGHT/20)*6);
        } else {
            batch.draw(victory, WIDTH / 2 - victory.getWidth() / 2, HEIGHT / 2 + (HEIGHT / 20) * 6);
        }
        batch.draw(tanks,WIDTH/2-tanks.getWidth()/2,HEIGHT/2-(HEIGHT/20)*1);
        font.getData().setScale(4);
        font.draw(batch, winner + " won!", WIDTH / 2 - (WIDTH / 12), HEIGHT / 2 + (HEIGHT / 7));
        font.getData().setScale(2);
        font.draw(batch, "Press ENTER to play again or ESCAPE to exit", WIDTH / 2 - (WIDTH / 7), HEIGHT / 2);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            MyGame gameScreen = new MyGame(game);
            gameScreen.create();
            game.setScreen(gameScreen);
        }
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
