package inf112.skeleton.app.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlagTest {
    Flag flag;
    private int y;
    private int x;
    private Sprite sprite;
    private Tile flagTile;
    private GridOfTiles grid;
    RectangleMapObject TiledFlag;
    private Texture texture;

    @Before
    public void setup() {
        this.grid = new GridOfTiles(11, 11, 78);
        this.texture = new Texture("Flag.png");
        this.sprite = new Sprite(texture);
    }

    @Test
    public void testFlag() {
        y = (int) TiledFlag.getRectangle().getY();
        x = (int) TiledFlag.getRectangle().getX();
        sprite.setSize(100, 100);
        flagTile = grid.getTile(3, 3);
        flagTile.addObjOnTile(flag);
        assertEquals(flagTile.getObjOnTile(),(IObject) flag);
    }
}
