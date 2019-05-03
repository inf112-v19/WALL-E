package inf112.skeleton.app.Objects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Actor.MyActor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnerTest {
    MyActor actor = new MyActor(null, MyGame.Dir.NORTH, false, "test", 0);
    Turner testTurner = new Turner();
    GridOfTiles grid = new GridOfTiles(10, 10, 10);

    @Test
    void handleTurner() {
        testTurner.placeTurnerOnGrid(1, 1, grid);
        actor.setPosition(1, 1, grid);
        MyGame.Dir initialDir = actor.getDir();
        testTurner.handleTurner(actor, grid);
        assertNotEquals(initialDir, actor.getDir());
    }
}