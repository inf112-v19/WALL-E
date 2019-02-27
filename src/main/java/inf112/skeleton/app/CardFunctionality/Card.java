package inf112.skeleton.app.CardFunctionality;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card extends ApplicationAdapter {
    private Turn turn;
    private int moves;
    private int priority;
    private boolean isBackup;
    private float x;
    private float y;
    
    public Card (Turn turn, int moves, int priority, boolean isBackup) {
        this.turn = turn;
        this.moves = moves;
        this.priority = priority;
        this.isBackup = isBackup;
    }
    
    public enum Turn {
        LEFT, RIGHT, UTURN, NONE
    }


    
    public Turn getTurn() {
        return this.turn;
    }
    
    public int getMoves() {
        return this.moves;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public boolean isBackup() {
        return this.isBackup;
    }
    
    public boolean isMove() {
        return this.moves > 0;
    }
    
    public boolean isTurn() {
        return this.turn != Turn.NONE;
    }

    public String getType(Card card) {
        if (card.isMove())
            return "move";
        else if (card.isBackup())
            return "backup";
        else if (card.isTurn())
            return "turn";
        return null;
    }

    public Card getCard(){ return this;}

    public void setX(float set){
        this.x = set;
    }
    public void setY(float set){
        this.y = set;
    }

    /***
     * The two methods under create and render cards
     *
     */
    private SpriteBatch card;
    private Texture cardTexture;

    @Override
    public void create() {
        card = new SpriteBatch();
        String type = getType(this);
        if(type=="move"){
            if(this.moves==1) cardTexture = new Texture((Gdx.files.internal("arrow1step.png")));
            if(this.moves==2) cardTexture = new Texture((Gdx.files.internal("arrow2step.png")));
            if(this.moves==3) cardTexture = new Texture((Gdx.files.internal("arrow3step.png")));
        }else if(type=="backup"){
            cardTexture = new Texture((Gdx.files.internal("arrow1stepback.png")));
        }else if(type=="turn"){
            if (this.turn==Turn.LEFT) cardTexture =  new Texture((Gdx.files.internal("rotate90Left.png")));
            if (this.turn==Turn.RIGHT) cardTexture =  new Texture((Gdx.files.internal("rotate90Right.png")));
            if (this.turn==Turn.UTURN) cardTexture =  new Texture((Gdx.files.internal("rotate180.png")));
        }
    }

    @Override
    public void render(){
        card.begin();
        card.draw(cardTexture, x, y, 32, 32);
        card.end();
    }
}
