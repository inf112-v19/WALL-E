package inf112.skeleton.app.Objects.Actor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface IActor {
    Boolean isCPU();
    Sprite getSprite();
    boolean alive();
}
