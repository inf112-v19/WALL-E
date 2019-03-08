package inf112.skeleton.app.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.Actor.Actor;

public class Objects extends ApplicationAdapter {
    //Flags
    private Batch flag;
    private Texture flagTex;
    private int flagWidth = 100;
    private int flagHeight = 100;

    //Backups/Repairs

    @Override
    public void create(){
        flag = new SpriteBatch();
        flagTex = new Texture(Gdx.files.internal("flag.png"));

    }

    @Override
    public void render(){
        int middleWidth = Gdx.graphics.getWidth()/2;
        int middleHeight = Gdx.graphics.getHeight()/2;

        //Render flag
        flag.begin();
        flag.draw(flagTex,middleWidth+300, middleHeight-180, 150, 100);
        flag.end();
    }

}
