package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.GridFunctionality.Tile;

public interface IObject {
    Sprite getSprite();
    Tile getTile();
}
