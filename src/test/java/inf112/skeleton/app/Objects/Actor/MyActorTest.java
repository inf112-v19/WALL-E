package inf112.skeleton.app.Objects.Actor;

import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyActorTest {
    MyActor actor = new MyActor("", MyGame.Dir.NORTH, false, "tester",1);
    GridOfTiles grid = new GridOfTiles(4,4, 10);

    @Before
   public void before(){
        actor.setX(0);
        actor.setY(0);
        actor.setHealth(1);
        actor.isDead = false;
    }
    @Test
    public void forward() {
        assertEquals(0.0, actor.y, 0);
        actor.Forward(1, 1, grid);
        assertEquals(1.0, actor.y, 0);
    }

    @Test
    public void backward() {
    }

    @Test
    public void turnRight() {
    }

    @Test
    public void turnLeft() {
    }

    @Test
    public void uTurn() {
    }

    @Test
    public void setX() {
    }

    @Test
    public void setY() {
    }

    @Test
    public void takeDamage() {
    }

    @Test
    public void getHealth() {
    }

    @Test
    public void setHealth() {
    }

    @Test
    public void getY() {
    }

    @Test
    public void getX() {
    }

    @Test
    public void isCPU() {
    }

    @Test
    public void alive() {
    }

    @Test
    public void getDir() {
    }

    @Test
    public void restoreHealth() {
    }
}