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

public class PlayOptions implements Screen {
    private RoboRally game;
    private float WIDTH;
    private float HEIGHT;

    private static int MAP_CHOICE = 0;
    private int POSSIBLE_MAPCHOICES = 1;
    private static int PLAYERS = 1;
    private static int CPUPLAYERS = 0;
    private  int POSSIBLE_PLAYERS = 8;
    private Stage stage;
    private Skin skin;
    private Label mapSelect;
    private Label playersSelect;
    private Label cpuPlayersSelect;
    private BitmapFont font;
    private SpriteBatch batch;
    private int playersY;
    private int cpuPlayersY;
    private int mapY;
    private int textX;

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
        table.setDebug(false);
        stage.addActor(table);

        if(WIDTH>1280){
            playersY = (int) (HEIGHT-(HEIGHT*0.4595));
            cpuPlayersY = (int) (HEIGHT-(HEIGHT*0.495));
            mapY = (int) (HEIGHT-(HEIGHT*0.53));
            textX = (int) (WIDTH-(WIDTH*0.453));
        } else{
            playersY = (int) (HEIGHT-(HEIGHT*0.441));
            cpuPlayersY = (int) (HEIGHT-(HEIGHT*0.493));
            mapY = (int) (HEIGHT-(HEIGHT*0.543));
            textX = (int) (WIDTH-(WIDTH*0.4294));
        }

        batch = new SpriteBatch();
        font = new BitmapFont();

        TextButton play = new TextButton("Play" ,skin);
        TextButton players = new TextButton("How many players:",skin);
        TextButton cpuPlayers = new TextButton("How many CPU players:",skin);
        TextButton map = new TextButton("Select map:",skin);
        TextButton back = new TextButton("Back", skin);
        mapSelect = new Label("<    >",skin);
        playersSelect = new Label("<    >",skin);
        cpuPlayersSelect = new Label("<    >",skin);


        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.GAME);
            }
        });

        players.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setPlayers();
                Gdx.app.log("Players:", Integer.toString(getPlayers()));
            }
        });

        cpuPlayers.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                setCpuPlayers();
            }
        });

        map.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setMapChoice();
                Gdx.app.log("Map:", Integer.toString(getMAP_CHOICE()));
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(game.MENU);
            }
        });

        table.add(play).colspan(2).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(players).fillX().uniformX();
        table.add(playersSelect);
        table.row();
        table.add(cpuPlayers);
        table.add(cpuPlayersSelect);
        table.row().pad(10,0,10,0);
        table.add(map).fillX().uniformX();
        table.add(mapSelect);
        table.row();
        table.add(back).colspan(2).fillX().uniformX();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        //currentMap = Integer.toString(getMAP_CHOICE());
        batch.begin();
        font.draw(batch,""+ MAP_CHOICE, textX, mapY);
        font.draw(batch,""+ PLAYERS, textX, playersY);
        font.draw(batch,""+ CPUPLAYERS, textX, cpuPlayersY);
        batch.end();
    }
    private void setMapChoice() {
        MAP_CHOICE++;
        if (MAP_CHOICE > POSSIBLE_MAPCHOICES) MAP_CHOICE = 0;
    }
    public static int getMAP_CHOICE() {
        return MAP_CHOICE;
    }

    private void setPlayers() {
        PLAYERS++;
        if (PLAYERS + CPUPLAYERS > POSSIBLE_PLAYERS) PLAYERS = 1;
    }
    private void setCpuPlayers() {
        CPUPLAYERS++;
        if(PLAYERS+CPUPLAYERS > POSSIBLE_PLAYERS) CPUPLAYERS = 0;
    }
    public static int getPlayers() {
        return PLAYERS;
    }
    public static int getCPUPlayers() {
        return CPUPLAYERS;
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
