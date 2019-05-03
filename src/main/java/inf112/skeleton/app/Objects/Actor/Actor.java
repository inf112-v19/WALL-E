package inf112.skeleton.app.Objects.Actor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class Actor extends ApplicationAdapter implements InputProcessor {
    private float actorLeft;
    private float actorRight;
    private float actorTop;
    private float actorBottom;
    private ArrayList<Card> handout = new ArrayList<>(9);
    private ArrayList<Card> chosen = new ArrayList<>(5);
    private float actorBackupX;
    private float actorBackupY;
    private boolean hasBackup;
    private Deck deck = new Deck();
    private Batch batch;
    private Texture aTexture;
    private BitmapFont font;
    private String output;
    private String output2;
    private Directions dir;
    private com.badlogic.gdx.scenes.scene2d.Actor actor = new com.badlogic.gdx.scenes.scene2d.Actor();
    private boolean rendered = false;

    float getX() {
        return actor.getX();
    }

    float getY() {
        return actor.getY();
    }

    private void chooseCard(int i) {
        Card card = handout.get(i);
        chosen.add(0, card);
        while (chosen.size() > 5) {
            Card deletedCard = chosen.remove(chosen.size() - 1);
            handout.add(deletedCard);
        }
    }

    private void handOut() {
        handout.clear();
        for (int i = 0; i < 9; i++) {
            handout.add(deck.handOut());
        }
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);

        dir = Directions.NORTH;
        hasBackup = false;
        font = new BitmapFont();
        output = "";
        output2 = "";

        batch = new SpriteBatch();
        aTexture = new Texture(Gdx.files.internal("robbie.png"));

        handOut();
    }

    @Override
    public void render() {
        rendered = true;
        int middleWidth = (Gdx.graphics.getWidth() / 2) + 25;
        int middleHeight = Gdx.graphics.getHeight() / 2;

        actorBottom = actor.getY();
        actorLeft = actor.getX();
        actorTop = actor.getY() + 70;
        actorRight = actor.getX() + 100;
        float textPositionX = (Gdx.graphics.getWidth() / 2) - 300;
        float textPositionY = Gdx.graphics.getHeight() - 30;
        String messageHandout = "Press backspace to deal cards";
        String messageNewHandout = "Press Left ALT followed by backspace for new handout";

        batch.begin();
        font.draw(batch, messageHandout, textPositionX - 400, textPositionY + 15);
        font.draw(batch, messageNewHandout, textPositionX - 400, textPositionY);
        font.draw(batch, output, textPositionX, textPositionY);
        font.draw(batch, output2, textPositionX, textPositionY + 15);
        batch.draw(aTexture, middleWidth + actor.getX(), middleHeight + actor.getY(), 100, 80);
        actor.draw(batch, 1);
        batch.end();
    }

    public ArrayList<Card> getChosen() {
        return chosen;
    }

    @Override
    public boolean keyDown(int keycode) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if (!rendered && width == 0 && height == 0) {
            width = 1000;
            height = 1000;
        }

        int actorXpos = width / 2;
        int actorYpos = height / 2;

        int deltaX = 142;
        int deltaY = 78;


        if (keycode == Input.Keys.LEFT) {
            actorXpos -= deltaX;
            if (actorXpos + actorLeft < 0) {
                if (hasBackup) {
                    actor.setPosition(actorBackupX, actorBackupY);
                    System.out.println("Returned to backup");
                }
            } else {
                actor.moveBy(-deltaX, 0);
            }
        }

        if (keycode == Input.Keys.B) {
            actorBackupX = actor.getX();
            actorBackupY = actor.getY();
            hasBackup = true;
            System.out.println("Backup set to: " + actorBackupX + ", " + actorBackupY);
        }

        if (keycode == Input.Keys.RIGHT) {
            actorXpos += deltaX;
            if (actorXpos + actorRight > width) {
                if (hasBackup) {
                    actor.setPosition(actorBackupX, actorBackupY);
                    System.out.println("Returned to backup");
                }
            } else {
                actor.moveBy(deltaX, 0);
            }
        }

        if (keycode == Input.Keys.ALT_LEFT) {
            handOut();

        }

        if (keycode == Input.Keys.ENTER) {
            if (chosen.size() > 0) {
                Card action = chosen.get(chosen.size() - 1);
                chosen.remove(chosen.size() - 1);
                String type = getType(action);

                float moveX = deltaX * action.getMoves();
                float moveY = deltaY * action.getMoves();

                assert type != null;
                switch (type) {
                    case "Move":
                        System.out.println("Actor should move " + dir + " by: " + action.getMoves());

                        if (dir == Directions.NORTH) {
                            actor.moveBy(0, moveY);
                        } else if (dir == Directions.EAST) {
                            actor.moveBy(moveX, 0);
                        } else if (dir == Directions.WEST) {
                            actor.moveBy(-moveX, 0);
                        } else if (dir == Directions.SOUTH) {
                            actor.moveBy(0, -moveY);
                        }

                        break;
                    case "Backup":
                        actorBackupX = actor.getX();
                        actorBackupY = actor.getY();
                        hasBackup = true;
                        System.out.println("New Backup position set as: [" + actorBackupX + ", " + actorBackupY + "]");

                        break;
                    case "Turn":
                        if (action.getTurn() == Card.Turn.LEFT) {
                            turnLeft();
                        } else if (action.getTurn() == Card.Turn.RIGHT) {
                            turnRight();
                        } else if (action.getTurn() == Card.Turn.UTURN) {
                            turnRight();
                            turnRight();
                        }
                        System.out.println("It was a turn card. Actor turned " + action.getTurn());
                        break;
                }
            } else {
                System.out.println("No cards left in chosen");
            }
        }

        if (keycode == Input.Keys.BACKSPACE) {
            StringBuilder s = new StringBuilder("Cards in handout: ");
            int num = 1;
            for (Card c : handout) {
                s.append(num).append(": ");
                String type = getType(c);
                assert type != null;
                if (type.equals("Move"))
                    s.append(type).append(" ").append(c.getMoves()).append(" step(s)").append(", ");
                else if (type.equals("Turn")) s.append(type).append(" ").append(c.getTurn()).append(", ");
                else s.append(type).append(", ");
                num++;
            }
            System.out.println(s);
            output = s.toString();
            output2 = "Press the number of the card in the required order to select, and then ENTER to perform moves!";
        }

        if (keycode == Input.Keys.UP) {
            actorYpos += deltaY;
            if (actorYpos + actorTop > height) {
                if (hasBackup) {
                    actor.setPosition(actorBackupX, actorBackupY);
                    System.out.println("Returned to backup");
                }
            } else {
                actor.moveBy(0, deltaY);
            }
        }

        if (keycode == Input.Keys.DOWN) {
            actorYpos -= deltaY;
            if (actorYpos + actorBottom < 0) {
                if (hasBackup) {
                    actor.setPosition(actorBackupX, actorBackupY);
                    System.out.println("Returned to backup");
                }
            } else {
                actor.moveBy(0, -deltaY);
            }
        }

        return false;
    }

    private void turnLeft() {
        if (dir == Directions.NORTH) {
            dir = Directions.WEST;
        } else if (dir == Directions.WEST) {
            dir = Directions.SOUTH;
        } else if (dir == Directions.SOUTH) {
            dir = Directions.EAST;
        } else if (dir == Directions.EAST) {
            dir = Directions.NORTH;
        }
    }

    private void turnRight() {
        if (dir == Directions.NORTH) {
            dir = Directions.EAST;
        } else if (dir == Directions.EAST) {
            dir = Directions.SOUTH;
        } else if (dir == Directions.SOUTH) {
            dir = Directions.WEST;
        } else if (dir == Directions.WEST) {
            dir = Directions.NORTH;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (chosen.size() >= 5) System.out.println("You can't choose more cards");

        else if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            chooseCard(keycode - 8);
            System.out.println("You chose: " + getType(handout.get(keycode - 8)) + " | Num :" + (keycode - 8));
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public enum Directions {
        NORTH,
        EAST,
        WEST,
        SOUTH
    }
}