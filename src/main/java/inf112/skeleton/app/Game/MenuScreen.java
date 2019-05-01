package inf112.skeleton.app.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    private RoboRally game;
    private Stage stage;
    private Skin skin;


    MenuScreen(RoboRally game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("uiskin.json"));
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        TextButton play = new TextButton("Play" ,skin);
        TextButton multiplayer = new TextButton("Multiplayer", skin);
        TextButton preferences = new TextButton("Settings", skin);
        TextButton exit = new TextButton("Exit", skin);

        table.add(play).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(multiplayer).fillX().uniformX();
        table.row();
        table.add(preferences).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(exit).fillX().uniformX();



        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.PLAYOPTIONS);
            }
        });

        multiplayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.MULTIPLAYER);
            }
        });

        preferences.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.PREFERENCES);
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }


    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
