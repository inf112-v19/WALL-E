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
    public float x;
    public float y;
    private SpriteBatch card;
    private Texture cardTexture;
    public float cardWidth = Gdx.graphics.getWidth()/15;
    public float cardHeight = Gdx.graphics.getHeight()/6;
    public boolean isShowing;

    Card(Turn turn, int moves, int priority, boolean isBackup) {
        this.turn = turn;
        this.moves = moves;
        this.priority = priority;
        this.isBackup = isBackup;
        isShowing = true;
    }

    public Turn getTurn() {
        return this.turn;
    }

    public int getMoves() {
        return this.moves;
    }

    int getPriority() {
        return this.priority;
    }

    boolean isBackup() {
        return this.isBackup;
    }

    boolean isMove() {
        return this.moves > 0;
    }

    boolean isTurn() {
        return this.turn != Turn.NONE;
    }

    @Override
    public String toString() {
        return "turn: " + turn + ", moves: " + moves + ", priority: " + priority + ", backup: " + isBackup;
    }

    public static String getType(Card card) {
        if (card.isMove())
            return "Move";
        else if (card.isBackup())
            return "Backup";
        else if (card.isTurn())
            return "Turn";
        return null;
    }

    public Card getCard() {
        return this;
    }

    public void setX(float set) {
        this.x = set;
    }

    public void setY(float set) {
        this.y = set;
    }

    @Override
    public void create() {
        card = new SpriteBatch();
        String type = getType(this);
        assert type != null;
        switch (type) {
            case "Move":
                if (this.moves == 1) cardTexture = new Texture((Gdx.files.internal("Move1.png")));
                if (this.moves == 2) cardTexture = new Texture((Gdx.files.internal("Move2.png")));
                if (this.moves == 3) cardTexture = new Texture((Gdx.files.internal("Move3.png")));
                break;
            case "Backup":
                cardTexture = new Texture((Gdx.files.internal("MoveBack.png")));
                break;
            case "Turn":
                if (this.turn == Turn.LEFT) cardTexture = new Texture((Gdx.files.internal("TurnLeft.png")));
                if (this.turn == Turn.RIGHT) cardTexture = new Texture((Gdx.files.internal("TurnRight.png")));
                if (this.turn == Turn.UTURN) cardTexture = new Texture((Gdx.files.internal("UTurn.png")));
                break;
        }
    }

    @Override
    public void render() {
        if(isShowing) {
            card.begin();
            card.draw(cardTexture, x, y, cardWidth, cardHeight);
            card.end();
        }
    }

    public void draw(SpriteBatch sb) {

    }

    public enum Turn {
        LEFT, RIGHT, UTURN, NONE
    }

    public float getHeight(){
        return cardHeight;
    }

    public float getWidth(){
        return cardWidth;
    }

    public float getX(){ return x;}

    public float getY(){ return y;}
}
