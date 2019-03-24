package inf112.skeleton.app.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Objects.Actor.Actor;
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

    public HealthBar(MyActor actor, String playerName){
        this.actor = actor;
        batch = new SpriteBatch();
        healthTexture = new Texture("blank.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        //font.getData().scale((float)1.2);
        font.getData().setScale((float)2);
        HEIGHT = Gdx.graphics.getHeight();
        WIDTH = Gdx.graphics.getWidth();
        actorHealth = playerName+": ";
        double x = WIDTH - (WIDTH * 0.35);
        double y = HEIGHT - (HEIGHT * 0.038);
        textPositionX = (float) x;
        textPositionY = (float) y;
    }

    public void render(){
        health = actor.getHealth();
        batch.begin();
        font.draw(batch, actorHealth, textPositionX, textPositionY);

        // Health-bar
        batch.setColor(Color.WHITE);
        batch.draw(healthTexture, WIDTH-(WIDTH/600)*158,HEIGHT-(HEIGHT/100)*6,(WIDTH/200)*35, (HEIGHT/300)*7);
        batch.setColor(Color.BLACK);
        batch.draw(healthTexture, WIDTH-(WIDTH/500)*130,HEIGHT-(HEIGHT/500)*27,WIDTH/6, HEIGHT/80);

        if (health> 0.6f) {
            batch.setColor(Color.GREEN);
        }else if (health > 0.2f) {
            batch.setColor(Color.ORANGE);
        }else {
            batch.setColor(Color.RED);
        }
        if(health>0) batch.draw(healthTexture, WIDTH-(WIDTH/500)*130,HEIGHT-(HEIGHT/500)*27,WIDTH/6*health, HEIGHT/80);
        batch.end();
    }
}
