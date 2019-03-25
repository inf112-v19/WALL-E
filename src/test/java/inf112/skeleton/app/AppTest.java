package inf112.skeleton.app;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import inf112.skeleton.app.Game.MyGame;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    // Assert there are no exceptions
    @Test(expected = Test.None.class /* no exception expected */)
    public void testRun() throws InterruptedException {
        MyGame game = new MyGame();
        new HeadlessApplication(game);

        TimeUnit.SECONDS.sleep(1);
    }
}
