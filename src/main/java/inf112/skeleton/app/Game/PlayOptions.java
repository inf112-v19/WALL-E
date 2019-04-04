package inf112.skeleton.app.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.nio.channels.spi.SelectorProvider;

public class PlayOptions implements Screen {
    private static int MAP_CHOICE = 0;
    private RoboRally game;
    private float WIDTH;
    private float HEIGHT;
    private int POSSIBLE_MAPCHOICES = 1;
    private Stage stage;
    private Skin skin;
    private List<String> maps;
    private String[] list;
    private ScrollPane scrollPane;
    private Label mapSelect;
    private String currentMap;
    private BitmapFont font;
    private SpriteBatch batch;

    public PlayOptions(RoboRally game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        skin = new Skin(Gdx.files.internal("uiskin.json"));
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        list = new String[]{"0,1"};
        maps = new List<String>(skin);
        maps.setItems(list);
        scrollPane = new ScrollPane(maps);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setTransform(true);
        currentMap = Integer.toString(getMAP_CHOICE());
        batch = new SpriteBatch();
        font = new BitmapFont();

        TextButton vsCPU = new TextButton("Play vs CPU" ,skin);
        TextButton map = new TextButton("Select map",skin);
        TextButton back = new TextButton("Back", skin);
        mapSelect = new Label("<"+currentMap+">",skin);

        vsCPU.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.GAME);
            }
        });

        map.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setMapChoice();
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.MENU);
            }
        });

        table.row().pad(10,0,10,0);
        table.add(vsCPU).colspan(2).fillX().uniformX();
        table.row();
        table.add(map);
        table.add(mapSelect);
        table.row().pad(10,0,10,0);
        table.row().pad(10,0,10,0);
        table.add(back).colspan(2).fillX().uniformX();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        /**
        currentMap = Integer.toString(getMAP_CHOICE());
        mapSelect = new Label("<"+currentMap+">",skin);
        batch.begin();
        font.draw(batch,""+ getMAP_CHOICE(),WIDTH/2,HEIGHT/2);
        batch.end();
         **/
    }
    private void setMapChoice() {
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
