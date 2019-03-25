package inf112.skeleton.app.Game;

import com.badlogic.gdx.*;
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
import inf112.skeleton.app.Objects.Actor.MyActor;
import inf112.skeleton.app.Objects.Explosion;
import inf112.skeleton.app.Objects.IObject;
import inf112.skeleton.app.Objects.ObjectMaker;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class MyGame extends ApplicationAdapter implements InputProcessor, Screen {
    public int PXSIZE;
    public TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    public MyActor actor;
    public MyActor actor2;
    public static GridOfTiles grid;
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
    private Texture healthTexture;
    public ArrayList<Card> handout = new ArrayList<>(9);
    public ArrayList<Card> chosen = new ArrayList<>(5);
    //public ArrayList<Explosion> explosions;
    private BitmapFont font;
    private String playerInstructionBackspace;
    private String playerInstructionALT;
    private String playerInstructionSelect;
    private String cardString;
    private float textPositionX;
    private float textPositionY;
    Card testCard;
    private int cardStartX;
    RoboRally game;
    private ObjectMaker objectMaker;
    private int HEIGHT;
    private int WIDTH;
    private String actor1Health;
    private String actor2Health;

    /**
     * Variabel bool playerSwitch for enkel variasjon i bevegelse av player 1 / 2
     * styres etter alle valg av kort er ferdig for spiler, og så bruk av kort for spiller
     */
    private boolean playerSwitch = false;

    public MyGame() {
        this(null);

        objectMaker = new ObjectMaker(null, null);
        actor = objectMaker.actor;
        actor2 = objectMaker.actor2;
    }

    MyGame(RoboRally game) {
        this.game = game;

        /*
         * Add hp
         * add dmg
         * add sprites for hearts
         */

        deck = new Deck();
        handOut();
        testCard = handout.get(2);
        chosen = new ArrayList<>(5);
        //To be used later for drawing and rendering cards
        CardArr = new Card[5];
        booleans = new Boolean[5];
    }

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
        HEIGHT = Gdx.graphics.getHeight();
        WIDTH = Gdx.graphics.getWidth();
        //explosions = new ArrayList<>();

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
        double x = WIDTH - (WIDTH * 0.97);
        double y = HEIGHT - (HEIGHT * 0.035);
        textPositionX = (float) x;
        textPositionY = (float) y;
        playerInstructionSelect = "";
        cardString = "";
        actor1Health = "Player 1: ";
        actor2Health = "Player 2: ";

        cardStartX = Gdx.graphics.getWidth() / 6;


        testCard.create();

        cardStartX = WIDTH / 6;

        objectMaker = new ObjectMaker(map, grid);
        objectMaker.create();
        actor = objectMaker.actor;
        actor2 = objectMaker.actor2;
        actor.create();
        actor2.create();
        grid.getTileWfloats(0, 0).addObjOnTile(actor);
        grid.getTileWfloats(0, 0).addObjOnTile(actor2);

        healthTexture = new Texture(Gdx.files.internal("blank.png"));
    }



        @Override
        public void render ( float v){
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            sb.setProjectionMatrix(camera.combined);


            //Text
            playerInstructionBackspace = "Click to choose cards and press ENTER to run program!";
            playerInstructionALT = "Press Left ALT for new handout";


            batch.begin();
            font.draw(batch, playerInstructionBackspace, textPositionX, textPositionY);
            font.draw(batch, playerInstructionALT, textPositionX, textPositionY - 35);
            font.draw(batch, actor1Health, WIDTH-WIDTH/3, textPositionY);
            font.draw(batch, actor2Health, WIDTH-WIDTH/3, textPositionY-HEIGHT/30);

            // Health-bar
            batch.setColor(Color.WHITE);
            batch.draw(healthTexture, WIDTH-(WIDTH/200)*54,HEIGHT-(HEIGHT/100)*6,(WIDTH/200)*37, (HEIGHT/300)*7);
            batch.setColor(Color.BLACK);
            batch.draw(healthTexture, WIDTH-WIDTH/4,HEIGHT-HEIGHT/19,WIDTH/6, HEIGHT/80);
            batch.setColor(Color.WHITE);
            batch.draw(healthTexture, WIDTH-(WIDTH/200)*54,HEIGHT-(HEIGHT/400)*46,(WIDTH/200)*37, (HEIGHT/300)*7);
            batch.setColor(Color.BLACK);
            batch.draw(healthTexture, WIDTH-WIDTH/4,HEIGHT-HEIGHT/12,WIDTH/6, HEIGHT/80);
            if (actor.getHealth()> 0.6f) {
                batch.setColor(Color.GREEN);
            }else if (actor.getHealth() > 0.2f) {
                batch.setColor(Color.ORANGE);
            }else {
                batch.setColor(Color.RED);
            }
            if(actor.getHealth()>0) batch.draw(healthTexture, WIDTH-WIDTH/4,HEIGHT-HEIGHT/19,WIDTH/6*actor.getHealth(), HEIGHT/80);
            if (actor2.getHealth()> 0.6f) {
                batch.setColor(Color.GREEN);
            }else if (actor2.getHealth() > 0.2f) {
                batch.setColor(Color.ORANGE);
            }else {
                batch.setColor(Color.RED);
            }
            if(actor2.getHealth()>0) batch.draw(healthTexture, WIDTH-WIDTH/4,HEIGHT-HEIGHT/12,WIDTH/6*actor2.getHealth(), HEIGHT/80);
            batch.end();

            Sprites();
            //drawHUD();

            createCards();

            //Explosion

            for(Explosion explosion : actor.explosions){
                sb.begin();
                explosion.render(sb);
                sb.end();
            }

            ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
            for (Explosion explosion :actor.explosions) {
                explosion.update(v);
                if (explosion.remove)
                    explosionsToRemove.add(explosion);
            }
            actor.explosions.removeAll(explosionsToRemove);



            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                //kort 1
                if (chosen.size() >= 5) System.out.println("You can't choose more cards");
                else {
                    if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(0).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(0).getY() + handout.get(0).getHeight() && Gdx.input.getX() > handout.get(0).getX() && Gdx.input.getX() < handout.get(0).getX() + handout.get(0).getWidth()) {
                        if (!handout.get(0).isChosen) {
                            chooseCard(0);
                            handout.get(0).setY(handout.get(0).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(0).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(0)) + " | Num :" + (1));
                            handout.get(0).isChosen = true;
                        }
                    }
                    //kort 2
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(1).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(1).getY() + handout.get(1).getHeight() && Gdx.input.getX() > handout.get(1).getX() && Gdx.input.getX() < handout.get(1).getX() + handout.get(1).getWidth()) {
                        if (!handout.get(1).isChosen) {
                            chooseCard(1);
                            handout.get(1).setY(handout.get(1).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(1).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(1)) + " | Num :" + (2));
                            handout.get(1).isChosen = true;
                        }
                    }
                    //kort 3
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(2).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(2).getY() + handout.get(2).getHeight() && Gdx.input.getX() > handout.get(2).getX() && Gdx.input.getX() < handout.get(2).getX() + handout.get(2).getWidth()) {
                        if (!handout.get(2).isChosen) {
                            chooseCard(2);
                            handout.get(2).setY(handout.get(2).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(2).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(2)) + " | Num :" + (3));
                            handout.get(2).isChosen = true;
                        }
                    }
                    //kort 4
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(3).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(3).getY() + handout.get(3).getHeight() && Gdx.input.getX() > handout.get(3).getX() && Gdx.input.getX() < handout.get(3).getX() + handout.get(3).getWidth()) {
                        if (!handout.get(3).isChosen) {
                            chooseCard(3);
                            handout.get(3).setY(handout.get(3).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(3).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(3)) + " | Num :" + (4));
                            handout.get(3).isChosen = true;
                        }
                    }
                    //kort 5
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(4).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(4).getY() + handout.get(4).getHeight() && Gdx.input.getX() > handout.get(4).getX() && Gdx.input.getX() < handout.get(4).getX() + handout.get(4).getWidth()) {
                        if (!handout.get(4).isChosen) {
                            chooseCard(4);
                            handout.get(4).setY(handout.get(4).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(4).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(4)) + " | Num :" + (5));
                            handout.get(4).isChosen = true;
                        }
                    }
                    //kort 6
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(5).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(5).getY() + handout.get(5).getHeight() && Gdx.input.getX() > handout.get(5).getX() && Gdx.input.getX() < handout.get(5).getX() + handout.get(5).getWidth()) {
                        if (!handout.get(5).isChosen) {
                            chooseCard(5);
                            handout.get(5).setY(handout.get(5).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(5).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(5)) + " | Num :" + (6));
                            handout.get(5).isChosen = true;
                        }
                    }
                    //kort 7
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(6).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(6).getY() + handout.get(6).getHeight() && Gdx.input.getX() > handout.get(6).getX() && Gdx.input.getX() < handout.get(6).getX() + handout.get(6).getWidth()) {
                        if (!handout.get(6).isChosen) {
                            chooseCard(6);
                            handout.get(6).setY(handout.get(6).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(6).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(6)) + " | Num :" + (7));
                            handout.get(6).isChosen = true;
                        }
                    }
                    //kort 8
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(7).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(7).getY() + handout.get(7).getHeight() && Gdx.input.getX() > handout.get(7).getX() && Gdx.input.getX() < handout.get(7).getX() + handout.get(7).getWidth()) {
                        if (!handout.get(7).isChosen) {
                            chooseCard(7);
                            handout.get(7).setY(handout.get(7).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(7).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(7)) + " | Num :" + (8));
                            handout.get(7).isChosen = true;
                        }
                    }
                    //kort 9
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(8).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(8).getY() + handout.get(8).getHeight() && Gdx.input.getX() > handout.get(8).getX() && Gdx.input.getX() < handout.get(8).getX() + handout.get(8).getWidth()) {
                        if (!handout.get(8).isChosen) {
                            chooseCard(8);
                            handout.get(8).setY(handout.get(8).getY() + Gdx.graphics.getHeight() / 20);
                            //handout.get(8).isShowing = false;
                            System.out.println("You chose: " + getType(handout.get(8)) + " | Num :" + (9));
                            handout.get(8).isChosen = true;
                        }
                    }
                }

            }


        }

        private int getTileSize () {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
            return (int) layer.getTileWidth();
        }

        private void drawHUD () {
            sb.begin();
            BackBoard.draw(sb);
            for (int i = 0; i < 5; i++) {
                if (booleans[i] != true) CardArr[i].draw(sb);
            }
            sb.end();
        }

        private void Sprites () {
            sb.begin();
            for (IObject obj : grid.getAll()) {
                if (obj.getSprite() != null) obj.getSprite().draw(sb);
            }
            sb.end();
        }

        private GridOfTiles initGrid () {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
            int HeightNTiles = layer.getHeight();
            int WidthNTiles = layer.getWidth();
            return new GridOfTiles(HeightNTiles, WidthNTiles, PXSIZE);
        }

        public void handOut() {
            handout.clear();
            for (int i = 0; i < 9; i++) {
                handout.add(deck.handOut());
            }
        }

        public void chooseCard(int i){
            Card card = handout.get(i);
            chosen.add(0, card);
            while (chosen.size() > 5) {
                Card deletedCard = chosen.remove(chosen.size() - 1);
                handout.add(deletedCard);
            }
        }


        void createCards () {
            int cardX = 0;
            int cardY = 100;
            for (int i = 0; i < handout.size(); i++) {
                Card c = handout.get(i);
                c.x = cardStartX + cardX;
                //c.y = cardY;
                cardX += c.cardWidth + Gdx.graphics.getWidth() / 128;
                c.create();
                c.render();
            }
        }


        @Override
        public boolean keyDown ( int keycode){
            if (playerSwitch) actor = actor2;
            float x = actor.getX();
            float y = actor.getY();
            Tile current = grid.getTileWfloats(y, x);
            actor.setPreviousTile(current);

            int moveDist = PXSIZE;

            if (keycode == Input.Keys.RIGHT) {
                actor.turnRight();
            }
            if (keycode == Input.Keys.LEFT) {
                actor.turnLeft();
            }

            if (keycode == Input.Keys.UP) {
                actor.Forward(1, moveDist, grid);
                actor.setPreviousTile(actor.getTile());
            }
            if (keycode == Input.Keys.DOWN) {
                actor.Forward(1, moveDist * (-1), grid);
                actor.setPreviousTile(actor.getTile());
            }

            if (keycode == Input.Keys.D) {
                actor.takeDamage(0.1);
            }

            if (keycode == Input.Keys.S) {
                actor2.takeDamage(0.1);
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
                        actor.backward(1, moveDist, grid);

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

                System.out.println(actor + " has no cards left in chosen");
                if (!playerSwitch) playerSwitch = true;
                else if (playerSwitch) playerSwitch = false;
                System.out.println(actor + " to choose cards.");
                keyDown(Input.Keys.ALT_LEFT);
                //}
            }

            if (keycode == Input.Keys.ALT_LEFT) {
                handOut();

            }

            if (keycode == Input.Keys.E){
                actor.explosions.add(new Explosion(actor.getX(),actor.getY()));
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
        public boolean keyUp ( int keycode){
            if (chosen.size() >= 5) System.out.println("You can't choose more cards");

            else if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
                chooseCard(keycode - 8);
                System.out.println("You chose: " + getType(handout.get(keycode - 8)) + " | Num :" + (keycode - 8));
            }
            return false;
        }


        @Override
        public boolean keyTyped ( char character){
            return false;
        }

        @Override
        public boolean touchDown ( int screenX, int screenY, int pointer, int button){
            return false;
        }

        @Override
        public boolean touchUp ( int screenX, int screenY, int pointer, int button){
            return false;
        }

        @Override
        public boolean touchDragged ( int screenX, int screenY, int pointer){
            return false;
        }

        @Override
        public boolean mouseMoved ( int screenX, int screenY){
            return false;
        }

        @Override
        public boolean scrolled ( int amount){
            return false;
        }

        @Override
        public void show () {

        }

        @Override
        public void render () {

        }

        @Override
        public void hide () {

        }

        public enum Dir {
            NORTH,
            EAST,
            WEST,
            SOUTH
        }
    }
