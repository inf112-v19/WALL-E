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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import inf112.skeleton.app.Animations.Explosion;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.CardFunctionality.Deck;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.HUD.HealthBar;
import inf112.skeleton.app.Map.Map;
import inf112.skeleton.app.Map.MapRenderer;
import inf112.skeleton.app.Objects.Actor.MyActor;
import inf112.skeleton.app.Objects.IObject;
import inf112.skeleton.app.Objects.MyLaser;
import inf112.skeleton.app.Objects.ObjectMaker;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class MyGame extends Multiplayer implements InputProcessor, Screen {
    public static GridOfTiles grid;
    public static OrthographicCamera camera;
    private static int amountOfFlags;
    private int PXSIZE = 78;
    private TiledMap tiledMap;
    public MyActor actor;
    private ArrayList<MyActor> actors;
    public MyActor currentActor;
    public Map map;
    public Deck deck;
    private Sprite BackBoard;
    private Batch batch;
    private Texture texture;
    private MyLaser renderLaser;
    private Sprite laserTexture;
    private MyActor actor2;
    public ArrayList<Card> handout = new ArrayList<>(9);
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch sb;
    private RoboRally game;
    private BitmapFont font;
    private float textPositionX;
    private float textPositionY;
    private int cardStartX;
    private HealthBar healthBar;
    private HealthBar healthBar2;
    private String activePlayer;
    private boolean hasSwappedActor;

    public MyGame() {
        this(null);

        ObjectMaker objectMaker = new ObjectMaker(null, null);
        actor = objectMaker.actor;
        actor2 = objectMaker.actor2;
    }

    boolean isHost;
    boolean joined = false;
    String host;

    @Override
    public void create() {
        //hurray
        map = new Map(MapRenderer.whatMapToCreateString());
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap());
        this.PXSIZE = getTileSize();
        camera = new MyCam(map.getTiledMap());
        tiledMap = MapRenderer.whatMapToCreate();

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new MyCam(tiledMap);
        camera.translate(-900, -1300);
        int HEIGHT = Gdx.graphics.getHeight();
        int WIDTH = Gdx.graphics.getWidth();

        grid = initGrid();
        Gdx.input.setInputProcessor(this);
        sb = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("robbie.png"));
        Sprite backBoard = new Sprite(texture);
        backBoard.setSize(300, 150);
        backBoard.setPosition(-140, 700);


        //Text
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        double x = WIDTH - (WIDTH * 0.93);
        double y = HEIGHT - (HEIGHT * 0.04);
        textPositionX = (float) x;
        textPositionY = (float) y;


        cardStartX = WIDTH / 6;
        ObjectMaker objectMaker;
        objectMaker = new ObjectMaker(map, grid);
        objectMaker.create();
        amountOfFlags = objectMaker.flags.size();
        actor = objectMaker.actor;
        actor2 = objectMaker.actor2;
        actor.create();
        actor2.create();
        actor.setName("Player One");
        actor2.setName("Computer");
        actors = new ArrayList<>();
        actors.add(actor);
        actors.add(actor2);
        grid.getTileWfloats(0, 0).addObjOnTile(actor);
        grid.getTileWfloats(0, 0).addObjOnTile(actor2);

        healthBar = new HealthBar(actor, actor.getName(), 1);
        healthBar2 = new HealthBar(actor2, actor2.getName(), 2);

        currentActor = actor;
        activePlayer = currentActor.getName() + ", you're up!";

        //Laser
        renderLaser = new MyLaser(grid, currentActor,  currentActor.getTile(), 0, 3);
        laserTexture = renderLaser.getSprite();
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

            if(actor.getHealth()<=0){
                actor.isDead = true;
                GameOverScreen gameOverScreen = new GameOverScreen(game, actor2.getName());
                game.setScreen(gameOverScreen);
            } else if(actor2.getHealth()<=0){
                actor2.isDead=true;
                GameOverScreen gameOverScreen = new GameOverScreen(game, actor.getName());
                game.setScreen(gameOverScreen);
            }else if (actor.gameOver){
                GameOverScreen gameOverScreen = new GameOverScreen(game, actor.getName());
                game.setScreen(gameOverScreen);
            }else if(actor2.gameOver){
                GameOverScreen gameOverScreen = new GameOverScreen(game, actor2.getName());
                game.setScreen(gameOverScreen);
            }


            // Health-bar
            healthBar.render();
            healthBar2.render();

            Sprites();

      
        createCards();

        batch.begin();
        font.getData().setScale(2);
        font.draw(batch, activePlayer, textPositionX, textPositionY);
        batch.end();

        //Explosion
        for (Explosion explosion : currentActor.explosions) {
            sb.begin();
            explosion.render(sb);
            sb.end();
        }

        ArrayList<Explosion> explosionsToRemove = new ArrayList<>();
        for (Explosion explosion : currentActor.explosions) {
            explosion.update(v);
            if (explosion.remove)
                explosionsToRemove.add(explosion);
        }
        currentActor.explosions.removeAll(explosionsToRemove);


        if (currentActor.isCPU) {
            goToCPUActions(currentActor);
        } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            //kort 1
            if (currentActor.chosen.size() >= 5)
                System.out.println(currentActor.getName() + " can't choose more cards");
            else {
                if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(0).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(0).getY() + handout.get(0).getHeight() && Gdx.input.getX() > handout.get(0).getX() && Gdx.input.getX() < handout.get(0).getX() + handout.get(0).getWidth()) {
                    if (!handout.get(0).isChosen) {
                        chooseCard(0);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(0)) + " | Num :" + (1));
                        handout.get(0).isChosen = true;
                    } else if (handout.get(0).isChosen) {
                        deselectCard(0);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(0)) + " | Num :" + (1));
                        handout.get(0).isChosen = false;
                    }
                }
                //kort 2
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(1).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(1).getY() + handout.get(1).getHeight() && Gdx.input.getX() > handout.get(1).getX() && Gdx.input.getX() < handout.get(1).getX() + handout.get(1).getWidth()) {
                    if (!handout.get(1).isChosen) {
                        chooseCard(1);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(1)) + " | Num :" + (2));
                        handout.get(1).isChosen = true;
                    } else if (handout.get(1).isChosen) {
                        deselectCard(1);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(1)) + " | Num :" + (2));
                        handout.get(1).isChosen = false;
                    }
                }
                //kort 3
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(2).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(2).getY() + handout.get(2).getHeight() && Gdx.input.getX() > handout.get(2).getX() && Gdx.input.getX() < handout.get(2).getX() + handout.get(2).getWidth()) {
                    if (!handout.get(2).isChosen) {
                        chooseCard(2);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(2)) + " | Num :" + (3));
                        handout.get(2).isChosen = true;
                    } else if (handout.get(2).isChosen) {
                        deselectCard(2);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(2)) + " | Num :" + (3));
                        handout.get(2).isChosen = false;
                    }
                }
                //kort 4
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(3).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(3).getY() + handout.get(3).getHeight() && Gdx.input.getX() > handout.get(3).getX() && Gdx.input.getX() < handout.get(3).getX() + handout.get(3).getWidth()) {
                    if (!handout.get(3).isChosen) {
                        chooseCard(3);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(3)) + " | Num :" + (4));
                        handout.get(3).isChosen = true;
                    } else if (handout.get(3).isChosen) {
                        deselectCard(3);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(3)) + " | Num :" + (4));
                        handout.get(3).isChosen = false;
                    }
                }
                //kort 5
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(4).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(4).getY() + handout.get(4).getHeight() && Gdx.input.getX() > handout.get(4).getX() && Gdx.input.getX() < handout.get(4).getX() + handout.get(4).getWidth()) {
                    if (!handout.get(4).isChosen) {
                        chooseCard(4);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(4)) + " | Num :" + (5));
                        handout.get(4).isChosen = true;
                    } else if (handout.get(4).isChosen) {
                        deselectCard(4);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(4)) + " | Num :" + (5));
                        handout.get(4).isChosen = false;
                    }
                }
                //kort 6
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(5).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(5).getY() + handout.get(5).getHeight() && Gdx.input.getX() > handout.get(5).getX() && Gdx.input.getX() < handout.get(5).getX() + handout.get(5).getWidth()) {
                    if (!handout.get(5).isChosen) {
                        chooseCard(5);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(5)) + " | Num :" + (6));
                        handout.get(5).isChosen = true;
                    } else if (handout.get(5).isChosen) {
                        deselectCard(5);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(5)) + " | Num :" + (6));
                        handout.get(5).isChosen = false;
                    }
                }
                //kort 7
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(6).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(6).getY() + handout.get(6).getHeight() && Gdx.input.getX() > handout.get(6).getX() && Gdx.input.getX() < handout.get(6).getX() + handout.get(6).getWidth()) {
                    if (!handout.get(6).isChosen) {
                        chooseCard(6);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(6)) + " | Num :" + (7));
                        handout.get(6).isChosen = true;
                    } else if (handout.get(6).isChosen) {
                        deselectCard(6);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(6)) + " | Num :" + (7));
                        handout.get(6).isChosen = false;
                    }
                }
                //kort 8
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(7).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(7).getY() + handout.get(7).getHeight() && Gdx.input.getX() > handout.get(7).getX() && Gdx.input.getX() < handout.get(7).getX() + handout.get(7).getWidth()) {
                    if (!handout.get(7).isChosen) {
                        chooseCard(7);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(7)) + " | Num :" + (8));
                        handout.get(7).isChosen = true;
                    } else if (handout.get(7).isChosen) {
                        deselectCard(7);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(7)) + " | Num :" + (8));
                        handout.get(7).isChosen = false;
                    }
                }
                //kort 9
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(8).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(8).getY() + handout.get(8).getHeight() && Gdx.input.getX() > handout.get(8).getX() && Gdx.input.getX() < handout.get(8).getX() + handout.get(8).getWidth()) {
                    if (!handout.get(8).isChosen) {
                        chooseCard(8);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(8)) + " | Num :" + (9));
                        handout.get(8).isChosen = true;
                    } else if (handout.get(8).isChosen) {
                        deselectCard(8);
                        System.out.println(currentActor.getName() + " deselected: " + getType(handout.get(8)) + " | Num :" + (9));
                        handout.get(8).isChosen = false;
                    }
                }
            }

        }
    }

    private void goToCPUActions(MyActor CPU) {
        int max = 8;
        int min = 0;
        int range = max - min + 1;
        int[] choicesForCPU = new int[4];
        for (int choice : choicesForCPU) {
            choice = (int) (Math.random() * range) + min;
            Card addForCPU = handout.get(choice);
            CPU.chosen.add(addForCPU);
            System.out.println(CPU.getName() + " chose: " + getType(handout.get(8)) + " | Num :" + choice);
            keyDown(Input.Keys.ENTER);
        }
    }

    public void shootLaserWithActor(){
        MyLaser laser = new MyLaser(grid, currentActor, currentActor.getTile(), 0, 3);
        laser.shootLaser();
        sb.begin();
        for (Sprite sprite : laser.renderArray) {
            System.out.println("Should render: " );
            sb.draw(sprite, sprite.getX(), sprite.getY());
        }
        sb.end();
    }

    /*private GridOfTiles initGrid () {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
            int HeightNTiles = layer.getHeight();
            int WidthNTiles = layer.getWidth();
            return new GridOfTiles(HeightNTiles, WidthNTiles, PXSIZE);
        }*/
    private int getTileSize() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
        return (int) layer.getTileWidth();
    }

    public void handOut() {
        if (handout.isEmpty())
            for (int i = 0; i < 9; i++)
                handout.add(deck.handOut());

        else
            lessHpLessCards();
    }

    public void lessHpLessCards() {
        float actorHp = currentActor.getHealth();
        float hpStep = (float) 0.75;
        for (int i=8; i>=0; i--){
            if (actorHp<hpStep){
                handout.set(i, currentActor.getFromLastHandout(i));
            }
            else {
                handout.set(i, deck.handOut());
            }

            hpStep-=0.25;
        }
    }

    public void lessHpLockCards() {
        int cardIndex = 8;
        float actorHp = currentActor.getHealth();
        float hpStep = (float) 0.75;

        while (hpStep>0) {
            if (actorHp < hpStep && !handout.get(cardIndex).isChosen) {
                chooseCard(cardIndex);
                handout.get(cardIndex).isChosen = true;
            }

            hpStep -= 0.25;
            cardIndex--;
        }
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

    public void chooseCard(int i) {
        Card card = handout.get(i);
        currentActor.chosen.add(0, card);
        while (currentActor.chosen.size() > 5) {
            Card deletedCard = currentActor.chosen.remove(currentActor.chosen.size() - 1);
            handout.add(deletedCard);
        }
    }

    private void deselectCard(int i) {
        Card card = handout.get(i);
        currentActor.chosen.remove(card);
    }

    private void createCards() {
        int cardX = 0;
        int cardY = 100;
        for (Card c : handout) {
            if (c.isChosen) {
                c.setY(cardY + Gdx.graphics.getHeight() / 20);
            } else {
                c.setY(cardY);
            }
            c.setX(cardStartX + cardX);
            cardX += c.cardWidth + Gdx.graphics.getWidth() / 128;
            c.create();
            c.render();
        }
    }

    ArrayList<String> clients = new ArrayList<>();

    @Override
    public boolean keyUp(int keycode) {
        if (currentActor.chosen.size() >= 5) System.out.println("You can't choose more cards");

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

    @Override
    public void show() {

    }

    @Override
    public void render() {

    }

    @Override
    public void hide() {

    }

    private void changeActor() {
        if (currentActor.actorIndex >= actors.size() - 1) {
            currentActor = actors.get(0);
            activePlayer = currentActor.getName() + ", you're up!";
        } else {
            currentActor = actors.get(currentActor.actorIndex + 1);
            activePlayer = currentActor.getName() + ", you're up!";
        }
    }

    public enum Dir {
        NORTH,
        EAST,
        WEST,
        SOUTH
    }

    int port = 9021;

    MyGame(RoboRally game) {
        this(game, false, null);
    }

    MyGame(RoboRally game, String host) {
        this(game, true, host);
    }

    MyGame(RoboRally game, boolean multiplayer, String host) {
        this.game = game;
        this.isHost = (host == null);

        /*
         * Add hp
         * add dmg
         * add sprites for hearts
         */

        if (isHost) {
            deck = new Deck();
            handOut();

            if (multiplayer) hostGame();
        } else {
            joinGame(host);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        float x = currentActor.getX();
        float y = currentActor.getY();
        Tile current = new Tile(0, 0, 0);
        try {
            current = grid.getTileWfloats(y, x);
            currentActor.setPreviousTile(current);
        } catch (NullPointerException ignored) {

        }

        int moveDist = PXSIZE;

        if (keycode == Input.Keys.RIGHT) {
            currentActor.turnRight();
        }
        if (keycode == Input.Keys.LEFT) {
            currentActor.turnLeft();
        }

        if (keycode == Input.Keys.UP) {
            currentActor.Forward(1, moveDist, grid);
            if (grid != null)
                currentActor.setPreviousTile(currentActor.getTile());
        }
        if (keycode == Input.Keys.DOWN) {
            currentActor.Forward(1, moveDist * (-1), grid);
            if (grid != null)
                currentActor.setPreviousTile(currentActor.getTile());
        }

        if (keycode == Input.Keys.D) {
            currentActor.takeDamage(0.1);
        }

            if (keycode == Input.Keys.S) {
                actor2.takeDamage(0.1);
            }
            if (keycode==Input.Keys.L){
                shootLaserWithActor();
            }

        if (keycode == Input.Keys.ENTER) {
            if (currentActor.chosen.size() == 5) {
                while (currentActor.chosen.size() > 0) {
                    Card action = currentActor.chosen.get(currentActor.chosen.size() - 1);
                    currentActor.chosen.remove(currentActor.chosen.size() - 1);
                    String type = getType(action);

                    assert type != null;
                    switch (type) {
                        case "Move":
                            System.out.println(currentActor.getName() + " should move " + currentActor.getDir() + " by: " + action.getMoves());
                            for (int i = 0; i < action.getMoves(); i++) {

                                currentActor.Forward(1, moveDist, grid);
                            }

                            break;
                        case "Backup":
                            System.out.println(currentActor.getName() + " should move backwards by: " + action.getMoves());
                            currentActor.backward(1, moveDist, grid);

                            break;
                        case "Turn":
                            if (action.getTurn() == Card.Turn.LEFT) {
                                currentActor.turnLeft();
                            } else if (action.getTurn() == Card.Turn.RIGHT) {
                                currentActor.turnRight();
                            } else if (action.getTurn() == Card.Turn.UTURN) {
                                currentActor.uTurn();
                            }
                            System.out.println("It was a turn card. " + currentActor.getName() + " turned " + action.getTurn());
                            break;
                    }
                }

                System.out.println(currentActor.getName() + " has no cards left in chosen");

                lessHpLockCards();
                currentActor.setLastHandout(handout);
                changeActor();
            }
            System.out.println(currentActor.getName() + " to choose cards.");
            keyDown(Input.Keys.ALT_LEFT);
        }

        if (keycode == Input.Keys.ALT_LEFT) {
            handOut();
        }

        if (keycode == Input.Keys.E) {
            currentActor.explosions.add(new Explosion(currentActor.getX(), currentActor.getY()));
        }


        if (Gdx.input.isTouched()) {
            Gdx.app.exit();
        }

        if (keycode == Input.Keys.ESCAPE) {
            game.changeScreen(game.MENU);
        }

        if (keycode == Input.Keys.B) {
            currentActor.setBackupTile(current);
            System.out.println("Backup set to: " + current);
        }

        if (keycode == Input.Keys.O) {
            GameOverScreen gameOverScreen = new GameOverScreen(game, currentActor.getName());
            game.setScreen(gameOverScreen);
        }

        if (joined) pushState();

        return false;
    }

    void hostGame() {
        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )
        ArrayList<String> addresses = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface ni : Collections.list(interfaces)) {
                for (InetAddress address : Collections.list(ni.getInetAddresses())) {
                    if (address instanceof Inet4Address) {
                        addresses.add(address.getHostAddress());
                        System.out.println(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        listen();

        joined = true;
        isHost = true;
    }

    private void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocketHints serverSocketHint = new ServerSocketHints();
                serverSocketHint.acceptTimeout = 0;
                ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, port, serverSocketHint);

                while (true) {
                    System.out.println("listening on network");

                    Socket socket = serverSocket.accept(null);

                    URI uri = null;
                    try {
                        uri = new URI("tcp:/" + socket.getRemoteAddress());
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    assert uri != null;
                    String host = uri.getHost();

                    System.out.println("Host: " + host);

                    DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    ByteArrayOutputStream bis = new ByteArrayOutputStream();

                    int count;
                    byte[] buffer = new byte[8192]; // or 4096, or more
                    while (true) {
                        try {
                            if (!((count = in.read(buffer)) > 0)) break;
                            bis.write(buffer, 0, count);
                            System.out.println("read incoming message");
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    MultiplayerCommand cmd = (MultiplayerCommand) convertBytesToObject(bis.toByteArray());
                    if (cmd == null) continue;
                    System.out.println(cmd);
                    switch (cmd.action) {
                        case STATE:
                            MultiplayerState state = (MultiplayerState) convertBytesToObject(cmd.params);
                            if (state == null) break;
                            readState(state);
                            if (isHost)
                                pushState(state);
                            break;
                        case JOIN:
                            System.out.println("Player is joining: " + host);
                            clients.add(host);
                            break;
                        default:
                            System.out.println("Unknown action");
                    }
                }
            }
        }).start();
    }

    void joinGame(String ipAddress) {
        if (joined || isHost) return;

        System.out.println("Joining game");

        host = ipAddress;
        sendCommand(new MultiplayerCommand(MultiplayerCommand.Action.JOIN));
        joined = true;
        System.out.println("joined");
    }

    private Object convertBytesToObject(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
            ObjectInput in = new ObjectInputStream(bis);
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendCommand(MultiplayerCommand cmd) {
        if (!isHost && host == null) return;

        byte[] msgBytes;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out;
            out = new ObjectOutputStream(bos);
            out.writeObject(cmd);
            out.flush();
            msgBytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (isHost) {
            System.out.println("Sending " + msgBytes.length + " bytes");
            for (String address : clients) {
                if (address.equals("127.0.0.1")) continue;
                sendBytesToAddress(msgBytes, address);
            }
        } else {
            sendBytesToAddress(msgBytes, host);
        }
    }

    private void sendBytesToAddress(byte[] data, String address) {
        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = 3000;
        Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, address, port, socketHints);
        try {
            // write our entered message to the stream
            socket.getOutputStream().write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readState(MultiplayerState state) {
        actor = state.actor;
        actors = state.actors;
        currentActor = state.currentActor;
        deck = state.deck;
        actor2 = state.actor2;
        handout = state.handout;
        cardStartX = state.cardStartX;
    }

    private void pushState() {
        MultiplayerState state = new MultiplayerState(actor, actors, currentActor, deck, actor2, handout, cardStartX);
        pushState(state);
    }

    private void pushState(MultiplayerState state) {
        MultiplayerCommand cmd = new MultiplayerCommand();
        cmd.action = MultiplayerCommand.Action.STATE;
        cmd.params = state.toBytes();
        sendCommand(cmd);
    }
}
