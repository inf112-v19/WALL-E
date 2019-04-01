package inf112.skeleton.app.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Map {
    TiledMap tiledMap;

    public Map(String mapFile) {
        this.tiledMap = new TmxMapLoader().load(mapFile);
    }

    //get map layer by it's name
    public MapLayer getMapLayer(String layerName) {
        MapLayer layer = tiledMap.getLayers().get(layerName);
        return layer;
    }

    public MapLayer getMapLayer(int i) {
        MapLayer layer = tiledMap.getLayers().get(i);
        return layer;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public int getTileSize() {
        TiledMapTileLayer layer = (TiledMapTileLayer) getMapLayer(0);
        return (int) layer.getTileWidth();
    }
}
