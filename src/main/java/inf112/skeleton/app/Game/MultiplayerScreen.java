package inf112.skeleton.app.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MultiplayerScreen implements Screen, Input.TextInputListener {
    private RoboRally game;
    private float WIDTH;
    private float HEIGHT;
    private int POSSIBLE_MAPCHOICES = 1;
    private Stage stage;
    private Skin skin;
    private TextField ipInput;
    private String ipAdress;
    private Input.TextInputListener inputListener;

    public MultiplayerScreen(RoboRally game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton host = new TextButton("Host game",skin);
        TextButton join = new TextButton("Join game",skin);
        TextButton play = new TextButton("Play",skin);
        TextButton back = new TextButton("Back", skin);


        inputListener = new Input.TextInputListener() {
            @Override
            public void input(String s) {
                ipAdress = s;
            }

            @Override
            public void canceled() {
                ipAdress = "";
                System.out.println("Cancelled");
            }
        };

        host.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Button funksjonalitet her
            }
        });

        join.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.getTextInput(inputListener, "IP Adress", "", "Write IP here");
            }
        });

        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.GAME);
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.MENU);
            }
        });

        table.add(host).colspan(2).center();
        table.row().pad(10,0,10,0);
        table.add(join);
        table.add(play);
        table.row();
        table.add(back).colspan(2).bottom().center();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Gdx.app.log("Text:",ipAdress);
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

    @Override
    public void input(String s) {

    }

    @Override
    public void canceled() {

    }
}
