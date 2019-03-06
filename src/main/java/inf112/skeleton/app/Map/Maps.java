package inf112.skeleton.app.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;

public class Maps {
    static ArrayList<TiledMap> maps = new ArrayList<>();

    static void addMap(TiledMap map) {
        maps.add(map);
    }

    public static TiledMap getMap(int index) {
        return maps.get(index);
    }
}
