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
    private double scale;
    private int winnerFont;
    private int replayFont;

    GameOverScreen(RoboRally game, String winner) {
        this.game = game;
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.winner = winner;
        tanks = new Texture(Gdx.files.internal("victory-tanks.png"));
        victory = new Texture(Gdx.files.internal("Victory.png"));
        defeat = new Texture(Gdx.files.internal("defeat.png"));
        scale = 1;
        winnerFont = 4;
        replayFont = 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(WIDTH<=1280){
            scale = 0.7;
            winnerFont = 3;
            replayFont = 1;
        }

        batch.begin();
        if(this.winner=="Computer"){
            batch.draw(defeat,WIDTH/2-(int)((victory.getWidth()/2)*scale),HEIGHT/2+(HEIGHT/20)*6, (int)(defeat.getWidth()*scale),(int)(defeat.getHeight()*scale));
        } else {
            batch.draw(victory, WIDTH / 2 - (int) ((victory.getWidth() / 2)*scale), HEIGHT / 2 + (HEIGHT / 20) * 6,(int)(victory.getWidth()*scale),(int)(victory.getHeight()*scale));
        }
        batch.draw(tanks,WIDTH/2-(int)(tanks.getWidth()/2*scale),HEIGHT/2-(HEIGHT/20)*1,(int)(tanks.getWidth()*scale),(int)(tanks.getHeight()*scale));
        font.getData().setScale(winnerFont);
        font.draw(batch, winner + " won!", WIDTH / 2 - (int)((WIDTH / 10)), HEIGHT / 2- (HEIGHT/30)*2);
        font.getData().setScale(replayFont);
        font.draw(batch, "Press ENTER to play again or ESCAPE to exit", WIDTH / 2 - (int)((WIDTH / 7)*scale), HEIGHT / 2-(HEIGHT/10)*2);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.changeScreen(game.GAME);
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
