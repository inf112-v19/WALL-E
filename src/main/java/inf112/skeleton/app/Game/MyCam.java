package inf112.skeleton.app.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

class MyCam extends OrthographicCamera {

    MyCam(TiledMap tiledMap) {
        super();
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int x = (int) layer.getTileWidth();
        int y = (int) layer.getTileHeight();

        int HeightByTiles = layer.getHeight();
        int WidthByTiles = layer.getWidth();

        float pxHeight = HeightByTiles * y;
        float pxWidth = WidthByTiles * x;

        this.setToOrtho(false, pxWidth * 2, pxHeight * 2);
        this.update();
    }
}
