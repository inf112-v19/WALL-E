package inf112.skeleton.app.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderer extends ApplicationAdapter  {

    //Camera camera = getCamera();
    public static TiledMap map;
   public TiledMap map1;
    public TiledMap map2;
    public TiledMap map3;
    public TiledMapRenderer renderer;

    @Override
    public void create() {
        map1 = new TmxMapLoader().load("ROBORALLY_MAP.tmx");
        map2 = new TmxMapLoader().load("RoboMap.tmx");
        map3 = new TmxMapLoader().load("MapTest.tmx");
        Maps.addMap(map1);
        Maps.addMap(map2);
        Maps.addMap(map3);



        renderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(Camera.getCamera());
        renderer.render();
    }

    public static void setMap(TiledMap selectedMap){
        map = selectedMap;
    }
}
