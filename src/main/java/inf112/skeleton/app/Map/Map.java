package inf112.skeleton.app.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Map {
    private TiledMap tiledMap;

    public Map(String mapFile) {
        this.tiledMap = new TmxMapLoader().load(mapFile);
    }

    //get map layer by it's name
    public MapLayer getMapLayer(String layerName) {
        return tiledMap.getLayers().get(layerName);
    }

    public MapLayer getMapLayer(int i) {
        return tiledMap.getLayers().get(i);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public int getTileSize() {
        TiledMapTileLayer layer = (TiledMapTileLayer) getMapLayer(0);
        return (int) layer.getTileWidth();
    }
}
