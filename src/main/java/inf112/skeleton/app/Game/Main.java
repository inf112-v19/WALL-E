package inf112.skeleton.app.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo-Rally!";
        cfg.width = 1920;
        cfg.height = 1080;
        cfg.resizable = false;

        new LwjglApplication(new RoboRally(), cfg);
    }
}
