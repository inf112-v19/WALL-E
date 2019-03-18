package inf112.skeleton.app.Game;

import com.badlogic.gdx.Game;

public class RoboRally extends Game {
    @Override
    public void create() {
        MenuScreen menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
