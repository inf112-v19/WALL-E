package inf112.skeleton.app.Actor;

import org.junit.Before;
import org.junit.Test;

public class ActorTest {

    private Actor actor;

    @Before
    public void setUp() {
        actor = new Actor();
        actor.create();
    }

    @Test
    public void chooseCard() {
    }

    @Test
    public void handOut() {
    }

    @Test
    public void keyDown() {
        float initX = actor.getX();
        float initY = actor.getY();
    }
}