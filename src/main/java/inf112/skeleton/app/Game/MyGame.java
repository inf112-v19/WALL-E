package inf112.skeleton.app.Game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Map.Map;
import inf112.skeleton.app.Objects.Actor.Actor;
import inf112.skeleton.app.Objects.Actor.MyActor;
import inf112.skeleton.app.Objects.IObject;
import inf112.skeleton.app.Objects.ObjectMaker;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class MyGame extends ApplicationAdapter implements InputProcessor {
    public int PXSIZE;
    public TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    public MyActor actor;
    public GridOfTiles grid;
    public Map map;
    public Deck deck;
    private Card temp;
    private Card[] CardArr;
    private Boolean[] booleans;
    private int cardX;
    private Sprite BackBoard;
    private Sprite Health;
    private Batch batch;
    private Texture texture;
    ArrayList<Card> handout = new ArrayList<>(9);
    ArrayList<Card> chosen = new ArrayList<>(5);
    private BitmapFont font;
    private String playerInstructionBackspace;
    private String playerInstructionALT;
    private String playerInstructionSelect;
    private String cardString;
    private float textPositionX;
    private float textPositionY;
    Card testCard;
    private int cardStartX;


    @Override
    public void create() {
        map = new Map("map_v1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap());
        this.PXSIZE = getTileSize();
        camera = new MyCam(map.getTiledMap());
        tiledMap = new TmxMapLoader().load("map_v1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new MyCam(tiledMap);
        camera.translate(-900, -1300);

        this.grid = initGrid();
        Gdx.input.setInputProcessor(this);
        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("robbie.png"));

        texture = new Texture("arrow3step.png");
        BackBoard = new Sprite(texture);
        BackBoard.setSize(300, 150);
        BackBoard.setPosition(-140, 700);

        //Text

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        double x = Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() * 0.97);
        double y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() * 0.035);
        textPositionX = (float) x;
        textPositionY = (float) y;
        playerInstructionSelect = "";
        cardString = "";


        /*
         * Add hp
         * add dmg
         * add sprites for hearts
         */

        deck = new Deck();
        handOut();
        testCard = handout.get(2);
        testCard.create();
        chosen = new ArrayList<>(5);
        //To be used later for drawing and rendering cards
        CardArr = new Card[5];
        booleans = new Boolean[5];

        cardStartX = Gdx.graphics.getWidth()/12;

        ObjectMaker objectMaker = new ObjectMaker(map, grid);
        actor = objectMaker.actor;
        grid.getTileWfloats(0, 0).addObjOnTile(actor);


    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        sb.setProjectionMatrix(camera.combined);

        //Text
        playerInstructionBackspace = "Press backspace to deal cards";
        playerInstructionALT = "Press Left ALT followed by backspace for new handout";


        batch.begin();
        font.draw(batch, playerInstructionBackspace, textPositionX, textPositionY);
        font.draw(batch, playerInstructionALT, textPositionX, textPositionY - 35);
        font.draw(batch, playerInstructionSelect, textPositionX, 130);
        font.draw(batch, cardString, textPositionX, 100);
        //font.draw(batch, "" + (Gdx.graphics.getHeight() - Gdx.input.getY()) + " , " + Gdx.input.getX(), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();

        //testCard.setX(300);
        //testCard.setY(100);
        //testCard.render();

        Sprites();
        //drawHUD();

        createCards();

    if(Gdx.input.isTouched()){
    //kort 1
        if (chosen.size() >= 5) System.out.println("You can't choose more cards");
        else {
            if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(0).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(0).getY() + handout.get(0).getHeight() && Gdx.input.getX() > handout.get(0).getX() && Gdx.input.getX() < handout.get(0).getX() + handout.get(0).getWidth()) {
                chooseCard(0);
                System.out.println("You chose: " + getType(handout.get(0)) + " | Num :" + (1));
            }
            //kort 2
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(1).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(1).getY() + handout.get(1).getHeight() && Gdx.input.getX() > handout.get(1).getX() && Gdx.input.getX() < handout.get(1).getX() + handout.get(1).getWidth()) {
                    chooseCard(1);
                    System.out.println("You chose: " + getType(handout.get(1)) + " | Num :" + (2));
            }
            //kort 3
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(2).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(2).getY() + handout.get(2).getHeight() && Gdx.input.getX() > handout.get(2).getX() && Gdx.input.getX() < handout.get(2).getX() + handout.get(2).getWidth()) {
                chooseCard(2);
                    System.out.println("You chose: " + getType(handout.get(2)) + " | Num :" + (3));
            }
            //kort 4
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(3).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(3).getY() + handout.get(3).getHeight() && Gdx.input.getX() > handout.get(3).getX() && Gdx.input.getX() < handout.get(3).getX() + handout.get(3).getWidth()) {
                    chooseCard(3);
                    System.out.println("You chose: " + getType(handout.get(3)) + " | Num :" + (4));
            }
            //kort 5
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(4).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(4).getY() + handout.get(4).getHeight() && Gdx.input.getX() > handout.get(4).getX() && Gdx.input.getX() < handout.get(4).getX() + handout.get(4).getWidth()) {
                    chooseCard(4);
                    System.out.println("You chose: " + getType(handout.get(4)) + " | Num :" + (5));
            }
            //kort 6
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(5).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(5).getY() + handout.get(5).getHeight() && Gdx.input.getX() > handout.get(5).getX() && Gdx.input.getX() < handout.get(5).getX() + handout.get(5).getWidth()) {
                    chooseCard(5);
                    System.out.println("You chose: " + getType(handout.get(5)) + " | Num :" + (6));
            }
            //kort 7
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(6).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(6).getY() + handout.get(6).getHeight() && Gdx.input.getX() > handout.get(6).getX() && Gdx.input.getX() < handout.get(6).getX() + handout.get(6).getWidth()) {
                    chooseCard(6);
                    System.out.println("You chose: " + getType(handout.get(6)) + " | Num :" + (7));
            }
            //kort 8
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(7).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(7).getY() + handout.get(7).getHeight() && Gdx.input.getX() > handout.get(7).getX() && Gdx.input.getX() < handout.get(7).getX() + handout.get(7).getWidth()) {
                    chooseCard(7);
                    System.out.println("You chose: " + getType(handout.get(7)) + " | Num :" + (8));
            }
            //kort 9
            else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(8).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(8).getY() + handout.get(8).getHeight() && Gdx.input.getX() > handout.get(8).getX() && Gdx.input.getX() < handout.get(8).getX() + handout.get(8).getWidth()) {
                    chooseCard(8);
                    System.out.println("You chose: " + getType(handout.get(8)) + " | Num :" + (9));
            }
        }

    }

        //System.out.println(100+handout.get(1).getHeight());


    }

    private int getTileSize() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
        return (int) layer.getTileWidth();
    }

    private void drawHUD() {
        sb.begin();
        BackBoard.draw(sb);
        for (int i = 0; i < 5; i++) {
            if (booleans[i] != true) CardArr[i].draw(sb);
        }
        sb.end();
    }

    private void Sprites() {
        sb.begin();
        for (IObject obj : grid.getAll()) {
            if (obj.getSprite() != null) obj.getSprite().draw(sb);
        }
        sb.end();
    }

    private GridOfTiles initGrid() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
        int HeightNTiles = layer.getHeight();
        int WidthNTiles = layer.getWidth();
        return new GridOfTiles(HeightNTiles, WidthNTiles, PXSIZE);
    }

    void handOut() {
        handout.clear();
        for (int i = 0; i < 9; i++) {
            handout.add(deck.handOut());
        }
    }

    void chooseCard(int i) {
        Card card = handout.get(i);
        chosen.add(0, card);
        while (chosen.size() > 5) {
            Card deletedCard = chosen.remove(chosen.size() - 1);
            handout.add(deletedCard);
        }
    }

    void createCards() {
        int cardX = 0;
        int cardY = 100;
        for (int i = 0; i < handout.size(); i++) {
            Card c = handout.get(i);
            c.x = cardStartX + cardX;
            c.y = cardY;
            cardX += c.cardWidth + 30;
            c.create();
            c.render();
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        float x = actor.getX();
        float y = actor.getY();
        Tile current = grid.getTileWfloats(x, y);

        int moveDist = PXSIZE;

        if (keycode == Input.Keys.RIGHT) {
            actor.turnRight();
        }
        if (keycode == Input.Keys.LEFT) {
            actor.turnLeft();
        }
        if (keycode == Input.Keys.UP) {
            actor.Forward(1, moveDist, grid);
        }
        if (keycode == Input.Keys.DOWN) {
            actor.Forward(1, moveDist * (-1), grid);
        }

        //__________________________________________________________
        if (keycode == Input.Keys.ENTER) {
            while (chosen.size() > 0) {
                Card action = chosen.get(chosen.size() - 1);
                chosen.remove(chosen.size() - 1);
                String type = getType(action);

                if (type == "Move") {
                    System.out.println("Actor should move " + actor.getDir() + " by: " + action.getMoves());
                    actor.Forward(1 * action.getMoves(), moveDist, grid);

                } else if (type.equals("Backup")) {
                    System.out.println("Actor should move backwards by: " + action.getMoves());
                    actor.backward(1,moveDist, grid);
                    //actor.setBackupTile(current);
                    //System.out.println("New Backup position set as: [" + current + "]");

                } else if (type == "Turn") {
                    if (action.getTurn() == Card.Turn.LEFT) {
                        actor.turnLeft();
                    } else if (action.getTurn() == Card.Turn.RIGHT) {
                        actor.turnRight();
                    } else if (action.getTurn() == Card.Turn.UTURN) {
                        actor.uTurn();
                    }
                    System.out.println("It was a turn card. Actor turned " + action.getTurn());
                }
            } //else {
            System.out.println("No cards left in chosen");
            //}
        }

        if (keycode == Input.Keys.ALT_LEFT) {
            handOut();

        }

        if (Gdx.input.isTouched()) {
            Gdx.app.exit();
        }

        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }

        if (keycode == Input.Keys.B) {
            actor.setBackupTile(current);
            System.out.println("Backup set to: " + current);
        }

        if (keycode == Input.Keys.BACKSPACE) {
            StringBuilder s = new StringBuilder("Cards in handout: ");
            int num = 1;
            for (Card c : handout) {
                s.append(num + ": ");
                String type = getType(c);
                if (type.equals("Move"))
                    s.append(type).append(" ").append(c.getMoves()).append(" step(s)").append(", ");
                else if (type.equals("Turn")) s.append(type).append(" ").append(c.getTurn()).append(", ");
                else s.append(type).append(", ");
                num++;
            }
            System.out.println(s);
            cardString = s.toString();
            playerInstructionSelect = "Press the number of the card in the required order to select, and then ENTER to perform moves!";
        }

        return false;
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

    public void isPressed(){
        //if(Gdx.input.getY()<)
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

    public enum Dir {
        NORTH,
        EAST,
        WEST,
        SOUTH
    }
}
