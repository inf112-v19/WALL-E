package inf112.skeleton.app.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Objects.Actor.MyActor;

public class HealthBar {
    float health;
    SpriteBatch batch;
    private Texture healthTexture;
    private BitmapFont font;
    private String actorHealth;
    private float WIDTH;
    private float HEIGHT;
    private float textPositionX;
    private float textPositionY;
    private MyActor actor;
    private int yPos;

    public HealthBar(MyActor actor, String playerName, int yPos) {
        this.actor = actor;
        batch = new SpriteBatch();
        healthTexture = new Texture("blank.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale((float) 2);
        HEIGHT = Gdx.graphics.getHeight();
        WIDTH = Gdx.graphics.getWidth();
        actorHealth = playerName + ": ";
        double x = WIDTH - (WIDTH * 0.35);
        double y = HEIGHT - (HEIGHT * 0.0008);
        textPositionX = (float) x;
        textPositionY = (float) y;
        this.yPos = yPos;
    }

    public void render() {
        health = actor.getHealth();
        batch.begin();
        font.draw(batch, actorHealth, textPositionX, textPositionY - (HEIGHT / 100) * (3 * yPos));

        // Health-bar
        batch.setColor(Color.WHITE);
        batch.draw(healthTexture, WIDTH - (WIDTH / 600) * 158, HEIGHT - ((HEIGHT / 100) * 2) - (HEIGHT / 100) * (3 * yPos), (WIDTH / 200) * 35, (HEIGHT / 300) * 7);
        batch.setColor(Color.BLACK);
        batch.draw(healthTexture, WIDTH - (WIDTH / 500) * 130, HEIGHT - (HEIGHT / 500) * 7 - ((HEIGHT / 100) * (3 * yPos)), WIDTH / 6, HEIGHT / 80);

        if (health > 0.6f) {
            batch.setColor(Color.GREEN);
        } else if (health > 0.2f) {
            batch.setColor(Color.ORANGE);
        } else {
            batch.setColor(Color.RED);
        }
        if (health > 0)
            batch.draw(healthTexture, WIDTH - (WIDTH / 500) * 130, HEIGHT - (HEIGHT / 500) * 7 - ((HEIGHT / 100) * (3 * yPos)), WIDTH / 6 * health, HEIGHT / 80);
        batch.end();
    }
}
