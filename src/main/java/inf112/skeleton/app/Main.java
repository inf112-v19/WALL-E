package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo-Rally";
        cfg.width = 1920;
        cfg.height = 1080;

       // new LwjglApplication(new Scene(), cfg);
        new LwjglApplication(new TiledTest(), cfg);
       // new LwjglApplication(new Physics(), cfg);
    }
}