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
    public float cardWidth = Gdx.graphics.getWidth()/12;
    public float cardHeight = Gdx.graphics.getHeight()/6;

    Card(Turn turn, int moves, int priority, boolean isBackup) {
        this.turn = turn;
        this.moves = moves;
        this.priority = priority;
        this.isBackup = isBackup;
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
                if (this.moves == 1) cardTexture = new Texture((Gdx.files.internal("arrow1step.png")));
                if (this.moves == 2) cardTexture = new Texture((Gdx.files.internal("arrow2step.png")));
                if (this.moves == 3) cardTexture = new Texture((Gdx.files.internal("arrow3step.png")));
                break;
            case "Backup":
                cardTexture = new Texture((Gdx.files.internal("arrow1stepback.png")));
                break;
            case "Turn":
                if (this.turn == Turn.LEFT) cardTexture = new Texture((Gdx.files.internal("rotate90Left.png")));
                if (this.turn == Turn.RIGHT) cardTexture = new Texture((Gdx.files.internal("rotate90Right.png")));
                if (this.turn == Turn.UTURN) cardTexture = new Texture((Gdx.files.internal("rotate180.png")));
                break;
        }
    }

    @Override
    public void render() {
        card.begin();
        card.draw(cardTexture, x, y, cardWidth, cardHeight);
        card.end();
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
}
