package inf112.skeleton.app.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.Game.MenuScreen;
import inf112.skeleton.app.Game.NewGame;

public class MapRenderer extends ApplicationAdapter {
    public static TiledMap map;
    public static TiledMapRenderer renderer;
    public static String map1Name = "map_v1.tmx";
    public static String map2Name = "map_v2.tmx";

    public static void setMap(TiledMap selectedMap) {
        map = selectedMap;
    }

    public static TiledMap whatMapToCreate() {
        int whatMapToCreate = MenuScreen.getMAP_CHOICE();
        TiledMap returnMap = null;
        switch (whatMapToCreate) {
            case 0:
                returnMap = new TmxMapLoader().load(map1Name);
                renderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load(map1Name));
                break;
            case 1:
                returnMap = new TmxMapLoader().load(map2Name);
                renderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load(map2Name));
                break;
        }
        return returnMap;
    }

    public static String whatMapToCreateString() {
        int whatMapToCreate = MenuScreen.getMAP_CHOICE();
        String returnMap = null;
        switch (whatMapToCreate) {
            case 0:
                returnMap = "map_v1.tmx";
                break;
            case 1:
                returnMap = "map_v2.tmx";
                break;
        }
        return returnMap;
    }

    @Override
    public void create() {
        whatMapToCreate();
    }

    @Override
    public void render() {
        renderer.setView(NewGame.camera);
        renderer.render();
    }

    public Vector2 getTileCord(int index) {
        TiledMapTile tile = map.getTileSets().getTile(index);
        // TODO: return the coordinates of a specific tile
        return null;
    }
}
