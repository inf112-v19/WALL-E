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
    private SpriteBatch card;
    private Texture cardTexture;

    Card(Turn turn, int moves, int priority, boolean isBackup) {
        this.turn = turn;
        this.moves = moves;
        this.priority = priority;
        this.isBackup = isBackup;
    }

    Turn getTurn() {
        return this.turn;
    }

    int getMoves() {
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

    private String getType(Card card) {
        if (card.isMove())
            return "move";
        else if (card.isBackup())
            return "backup";
        else if (card.isTurn())
            return "turn";
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
            case "move":
                if (this.moves == 1) cardTexture = new Texture((Gdx.files.internal("arrow1step.png")));
                if (this.moves == 2) cardTexture = new Texture((Gdx.files.internal("arrow2step.png")));
                if (this.moves == 3) cardTexture = new Texture((Gdx.files.internal("arrow3step.png")));
                break;
            case "backup":
                cardTexture = new Texture((Gdx.files.internal("arrow1stepback.png")));
                break;
            case "turn":
                if (this.turn == Turn.LEFT) cardTexture = new Texture((Gdx.files.internal("rotate90Left.png")));
                if (this.turn == Turn.RIGHT) cardTexture = new Texture((Gdx.files.internal("rotate90Right.png")));
                if (this.turn == Turn.UTURN) cardTexture = new Texture((Gdx.files.internal("rotate180.png")));
                break;
        }
    }

    @Override
    public void render() {
        card.begin();
        card.draw(cardTexture, x, y, 32, 32);
        card.end();
    }

    public enum Turn {
        LEFT, RIGHT, UTURN, NONE
    }
}
