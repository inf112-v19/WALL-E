package inf112.skeleton.app.Actor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class Actor extends ApplicationAdapter implements InputProcessor {
    ArrayList<Card> handout = new ArrayList<>(9);
    public float actorLeft;
    public float actorRight;
    public float actorTop;
    public float actorBottom;
    private float actorBackupX;
    private float actorBackupY;
    //TiledMapTile start = MapRenderer.map.getTileSets().getTile(0);
    //TiledMapTile position;

    ArrayList<Card> chosen = new ArrayList<>(5);
    private Deck deck = new Deck();
    private Batch batch;
    private Texture aTexture;
    private Directions dir;
    private com.badlogic.gdx.scenes.scene2d.Actor actor = new com.badlogic.gdx.scenes.scene2d.Actor();
    private boolean rendered = false;
    public boolean viewRender = false;
    public Card view;

    float getX() {
        return actor.getX();
    }

    float getY() {
        return actor.getY();
    }

    void chooseCard(int i) {
        Card card = handout.get(i);
        //handout.remove(i);
        chosen.add(card);
        while (chosen.size() > 5) {
            Card deletedCard = chosen.remove(chosen.size()-1);
            handout.add(deletedCard);
        }
    }

    void handOut() {
        handout.clear();
        for (int i = 0; i < 9; i++) {
            handout.add(deck.handOut());
        }
    }

    public enum Directions {
        NORTH,
        EAST,
        WEST,
        SOUTH
    }


    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);

        dir = Directions.NORTH;

        // Sprite
        batch = new SpriteBatch();
        aTexture = new Texture(Gdx.files.internal("robbie.png"));

        // Get cards, place in handout (From Deck.handout)
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

        batch.begin();
        batch.draw(aTexture, middleWidth + actor.getX(), middleHeight + actor.getY(), 40, 40);
        actor.draw(batch, 1);
        //actor.localToStageCoordinates((Vector2) start);
        batch.end();
        // TODO: Render card
    }

    public ArrayList<Card> getChosen() {
        return chosen;
    }

    public TiledMapTile locate(){
        //return (TiledMapTile) actor.localToStageCoordinates((Vector2) position);
        return null;
    }
    // Actor Input
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

        int deltaX = 50;
        int deltaY = 40;


        if (keycode == Input.Keys.LEFT) {
            actorXpos -= deltaX;
            if (actorXpos + actorLeft < 0) {
                actor.setPosition(actorBackupX, actorBackupY);
                System.out.println("Returned to backup");
            } else {
                actor.moveBy(-deltaX, 0);
            }
        }

        if (keycode == Input.Keys.RIGHT) {
            actorXpos += deltaX;
            if (actorXpos + actorRight > width) {
                actor.setPosition(actorBackupX, actorBackupY);
                System.out.println("Returned to backup");
            } else {
                actor.moveBy(deltaX, 0);
            }
        }

        if (keycode == Input.Keys.ENTER) {
            if (chosen.size() > 0) {
                Card action = chosen.get(chosen.size() - 1);
                chosen.remove(chosen.size() - 1);
                String type = getType(action);

                if (type == "Move") {
                    System.out.println("Actor should move " + dir + " by: " + action.getMoves());


                    float moveX = deltaX * action.getMoves();
                    float moveY = deltaY * action.getMoves();

                    if (dir == Directions.NORTH) {
                        actor.moveBy(0, moveY);
                    } else if (dir == Directions.EAST) {
                        actor.moveBy(moveX, 0);
                    } else if (dir == Directions.WEST) {
                        actor.moveBy(-moveX, 0);
                    } else if (dir == Directions.SOUTH) {
                        actor.moveBy(0, -moveY);
                    }

                } else if (type.equals("Backup")) {
                    actorBackupX = actorXpos;
                    actorBackupY = actorYpos;
                    System.out.println("New Backup position set as: [" + actorBackupX +", " + actorBackupY +"]");
                } else if (type == "Turn") {
                    if (action.getTurn() == Card.Turn.LEFT) {
                        turnLeft();
                    } else if (action.getTurn() == Card.Turn.RIGHT) {
                        turnRight();
                    } else if (action.getTurn() == Card.Turn.UTURN) {
                        turnRight();
                        turnRight();
                    }
                    System.out.println("It was a turn card. Actor turned " + action.getTurn());
                }
            } else {
                System.out.println("No cards left in chosen");
            }
        }

        if(keycode== Input.Keys.ALT_LEFT){
            actorBackupX = actorXpos;
            actorBackupY = actorYpos;
        }

        if (keycode == Input.Keys.BACKSPACE) {
            StringBuilder s = new StringBuilder("Cards in handout: ");
            for (Card c : handout) {
                String type = getType(c);
                if(type.equals("Move")) s.append(type).append("-").append(c.getMoves()).append(", ");
                else if (type.equals("Turn")) s.append(type).append("-").append(c.getTurn()).append(", ");
                else s.append(type).append(", ");
            }
            System.out.println(s);
        }

        if (keycode == Input.Keys.UP) {
            actorYpos += deltaY;
            if (actorYpos + actorTop > height) {
                actor.setPosition(actorBackupX, actorBackupY);
                System.out.println("Returned to backup");
                //actor.moveBy(0, 0);
                //actorYpos -= deltaY;
            } else {
                actor.moveBy(0, deltaY);
            }

            //Card action = chosen.pop();
        }

        if (keycode == Input.Keys.DOWN) {
            actorYpos -= deltaY;
            if (actorYpos + actorBottom < 0) {
                actor.setPosition(actorBackupX, actorBackupY);
                System.out.println("Returned to backup");
                //actor.moveBy(0, 0);
                //actorYpos += deltaY;
            } else {
                actor.moveBy(0, -deltaY);
            }
        }

        return false;
    }

    public void turnLeft() {
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

    public void turnRight() {
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
        // choose cards with keypad 1-9 / vector and add iteratively to chosen arraylist
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

    public void renderCard() {
        view.render();
    }
}