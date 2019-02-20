package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TiledTest extends ApplicationAdapter implements InputProcessor {

    //Creating new branch fur dis
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;

    //Character Update
    SpriteBatch sb;
    Texture texture;
    Sprite sprite;

    //Adding maplayer
    //nonp
    MapLayer conveyorBlue;
    TextureRegion textureRegion;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        //tiledMap = new TmxMapLoader().load("MapTest.tmx");
        tiledMap = new TmxMapLoader().load("ROBORALLY_MAP.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(this);

        //Sprite
        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("robbie.png"));
        sprite = new Sprite(texture);

        //Adding objectlayer
        conveyorBlue = tiledMap.getLayers().get("Conveyor_BLUE");
        textureRegion = new TextureRegion(texture, 64,64);

        TextureMapObject tmo = new TextureMapObject(textureRegion);
        tmo.setX(0);
        tmo.setY(0);
        conveyorBlue.getObjects().add(tmo);
    }

    @Override
    public void render() {
       /* Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.viewportHeight = 2024;
        camera.viewportWidth = 2024;
        camera.update();
        */


        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        sb.setProjectionMatrix(camera.combined);
        sb.begin();
       // sb.draw(texture,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2, 100, 80);
        sprite.draw(sb);
        sb.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.NUM_3)
            camera.zoom -= 40;
        if (keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        SpriteBatch a = sb;
        Actor actor = new Actor();
        actor.moveBy(10, 0);
    return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        sprite.setPosition(position.x, position.y);
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
