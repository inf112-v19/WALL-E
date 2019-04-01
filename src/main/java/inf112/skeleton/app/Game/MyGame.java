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
import inf112.skeleton.app.HUD.HealthBar;
import inf112.skeleton.app.Map.Map;
import inf112.skeleton.app.Objects.Actor.MyActor;
import inf112.skeleton.app.Animations.Explosion;
import inf112.skeleton.app.Objects.IObject;
import inf112.skeleton.app.Objects.Laser;
import inf112.skeleton.app.Objects.ObjectMaker;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class MyGame extends ApplicationAdapter implements InputProcessor, Screen {
    public int PXSIZE = 78;
    public TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    public MyActor actor;
    public MyActor actor2;
    public MyActor currentActor;
    public static GridOfTiles grid;
    public Map map;
    public Deck deck;
    private Sprite BackBoard;
    private Batch batch;
    private Texture texture;
    private Texture laserTexture;
    public ArrayList<Card> handout = new ArrayList<>(9);
    public ArrayList<Laser> lasers;
    public static int amountOfFlags;
    private BitmapFont font;
    private float textPositionX;
    private float textPositionY;
    private int cardStartX;
    RoboRally game;
    private ObjectMaker objectMaker;
    private int HEIGHT;
    private int WIDTH;
    private HealthBar healthBar;
    private HealthBar healthBar2;
    private String activePlayer;

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
        //amountOfFlags = objectMaker.flags.size();
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

        this.grid = initGrid();
        Gdx.input.setInputProcessor(this);
        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("robbie.png"));
        BackBoard = new Sprite(texture);
        BackBoard.setSize(300, 150);
        BackBoard.setPosition(-140, 700);

        //Text
        activePlayer = "Player 1, you're up!";

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
        actor.setName("Player 1");
        actor2.setName("Player 2");
        grid.getTileWfloats(0, 0).addObjOnTile(actor);
        grid.getTileWfloats(0, 0).addObjOnTile(actor2);

        healthBar = new HealthBar(actor,actor.getName(),1);
        healthBar2 = new HealthBar(actor2,actor2.getName(),2);

        //chosen = new ArrayList<>();
        currentActor = actor;
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
            Laser(v);
            //drawHUD();

            createCards();

            batch.begin();
            font.getData().setScale(2);
            font.draw(batch, activePlayer,textPositionX,textPositionY);
            batch.end();

            //Explosion

            for(Explosion explosion : currentActor.explosions){
                sb.begin();
                explosion.render(sb);
                sb.end();
            }

            ArrayList<Explosion> explosionsToRemove = new ArrayList<>();
            for (Explosion explosion :currentActor.explosions) {
                explosion.update(v);
                if (explosion.remove)
                    explosionsToRemove.add(explosion);
            }
            currentActor.explosions.removeAll(explosionsToRemove);



            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                //kort 1
                if (currentActor.chosen.size() >= 5) System.out.println("You can't choose more cards");
                else {
                    if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(0).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(0).getY() + handout.get(0).getHeight() && Gdx.input.getX() > handout.get(0).getX() && Gdx.input.getX() < handout.get(0).getX() + handout.get(0).getWidth()) {
                        if (!handout.get(0).isChosen) {
                            chooseCard(0);
                            System.out.println("You chose: " + getType(handout.get(0)) + " | Num :" + (1));
                            handout.get(0).isChosen = true;
                        }else if(handout.get(0).isChosen){
                            deselectCard(0);
                            System.out.println("You deselected: " + getType(handout.get(0)) + " | Num :" + (1));
                            handout.get(0).isChosen = false;
                        }
                    }
                    //kort 2
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(1).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(1).getY() + handout.get(1).getHeight() && Gdx.input.getX() > handout.get(1).getX() && Gdx.input.getX() < handout.get(1).getX() + handout.get(1).getWidth()) {
                        if (!handout.get(1).isChosen) {
                            chooseCard(1);
                            System.out.println("You chose: " + getType(handout.get(1)) + " | Num :" + (2));
                            handout.get(1).isChosen = true;
                        }else if(handout.get(1).isChosen){
                            deselectCard(1);
                            System.out.println("You deselected: " + getType(handout.get(1)) + " | Num :" + (2));
                            handout.get(1).isChosen = false;
                        }
                    }
                    //kort 3
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(2).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(2).getY() + handout.get(2).getHeight() && Gdx.input.getX() > handout.get(2).getX() && Gdx.input.getX() < handout.get(2).getX() + handout.get(2).getWidth()) {
                        if (!handout.get(2).isChosen) {
                            chooseCard(2);
                            System.out.println("You chose: " + getType(handout.get(2)) + " | Num :" + (3));
                            handout.get(2).isChosen = true;
                        }else if(handout.get(2).isChosen){
                            deselectCard(2);
                            System.out.println("You deselected: " + getType(handout.get(2)) + " | Num :" + (3));
                            handout.get(2).isChosen = false;
                        }
                    }
                    //kort 4
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(3).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(3).getY() + handout.get(3).getHeight() && Gdx.input.getX() > handout.get(3).getX() && Gdx.input.getX() < handout.get(3).getX() + handout.get(3).getWidth()) {
                        if (!handout.get(3).isChosen) {
                            chooseCard(3);
                            System.out.println("You chose: " + getType(handout.get(3)) + " | Num :" + (4));
                            handout.get(3).isChosen = true;
                        }else if(handout.get(3).isChosen){
                            deselectCard(3);
                            System.out.println("You deselected: " + getType(handout.get(3)) + " | Num :" + (4));
                            handout.get(3).isChosen = false;
                        }
                    }
                    //kort 5
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(4).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(4).getY() + handout.get(4).getHeight() && Gdx.input.getX() > handout.get(4).getX() && Gdx.input.getX() < handout.get(4).getX() + handout.get(4).getWidth()) {
                        if (!handout.get(4).isChosen) {
                            chooseCard(4);
                            System.out.println("You chose: " + getType(handout.get(4)) + " | Num :" + (5));
                            handout.get(4).isChosen = true;
                        }else if(handout.get(4).isChosen){
                            deselectCard(4);
                            System.out.println("You deselected: " + getType(handout.get(4)) + " | Num :" + (5));
                            handout.get(4).isChosen = false;
                        }
                    }
                    //kort 6
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(5).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(5).getY() + handout.get(5).getHeight() && Gdx.input.getX() > handout.get(5).getX() && Gdx.input.getX() < handout.get(5).getX() + handout.get(5).getWidth()) {
                        if (!handout.get(5).isChosen) {
                            chooseCard(5);
                            System.out.println("You chose: " + getType(handout.get(5)) + " | Num :" + (6));
                            handout.get(5).isChosen = true;
                        }else if(handout.get(5).isChosen){
                            deselectCard(5);
                            System.out.println("You deselected: " + getType(handout.get(5)) + " | Num :" + (6));
                            handout.get(5).isChosen = false;
                        }
                    }
                    //kort 7
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(6).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(6).getY() + handout.get(6).getHeight() && Gdx.input.getX() > handout.get(6).getX() && Gdx.input.getX() < handout.get(6).getX() + handout.get(6).getWidth()) {
                        if (!handout.get(6).isChosen) {
                            chooseCard(6);
                            System.out.println("You chose: " + getType(handout.get(6)) + " | Num :" + (7));
                            handout.get(6).isChosen = true;
                        }else if(handout.get(6).isChosen){
                            deselectCard(6);
                            System.out.println("You deselected: " + getType(handout.get(6)) + " | Num :" + (7));
                            handout.get(6).isChosen = false;
                        }
                    }
                    //kort 8
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(7).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(7).getY() + handout.get(7).getHeight() && Gdx.input.getX() > handout.get(7).getX() && Gdx.input.getX() < handout.get(7).getX() + handout.get(7).getWidth()) {
                        if (!handout.get(7).isChosen) {
                            chooseCard(7);
                            System.out.println("You chose: " + getType(handout.get(7)) + " | Num :" + (8));
                            handout.get(7).isChosen = true;
                        }else if(handout.get(7).isChosen){
                            deselectCard(7);
                            System.out.println("You deselected: " + getType(handout.get(7)) + " | Num :" + (8));
                            handout.get(7).isChosen = false;
                        }
                    }
                    //kort 9
                    else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(8).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(8).getY() + handout.get(8).getHeight() && Gdx.input.getX() > handout.get(8).getX() && Gdx.input.getX() < handout.get(8).getX() + handout.get(8).getWidth()) {
                        if (!handout.get(8).isChosen) {
                            chooseCard(8);
                            System.out.println("You chose: " + getType(handout.get(8)) + " | Num :" + (9));
                            handout.get(8).isChosen = true;
                        }else if(handout.get(8).isChosen){
                            deselectCard(8);
                            System.out.println("You deselected: " + getType(handout.get(8)) + " | Num :" + (9));
                            handout.get(8).isChosen = false;
                        }
                    }
                }

            }
        }

        private int getTileSize () {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
            return (int) layer.getTileWidth();
        }

        private void Sprites () {
            sb.begin();
            for (IObject obj : grid.getAll()) {
                if (obj.getSprite() != null) obj.getSprite().draw(sb);
            }
            sb.end();
        }

    private void Laser(float delta) {
        float actorX;
        float actorY;
        lasers = new ArrayList<>();

        MyGame.Dir dir = currentActor.getDir();
        switch(dir){
            case NORTH:
                actorX = currentActor.getX()-currentActor.getSprite().getWidth()-180;
                actorY = currentActor.getY()+currentActor.getSprite().getHeight();
                lasers.add(new Laser(actorX , actorY));
                break;

            case EAST:
                actorX = (currentActor.getX()+currentActor.getSprite().getWidth()-400);
                actorY = currentActor.getY()+currentActor.getSprite().getHeight()/2;
                lasers.add(new Laser(actorX, actorY));
                break;

            case WEST:
                actorX = currentActor.getX()+currentActor.getSprite().getWidth()+200;
                actorY = (currentActor.getY()+currentActor.getSprite().getHeight()/2);
                lasers.add(new Laser(actorX, actorY));
                break;

            case SOUTH:
                actorX = currentActor.getX()-currentActor.getSprite().getWidth()-180;
                actorY = (currentActor.getY()+currentActor.getSprite().getHeight()-180);
                lasers.add(new Laser(actorX, actorY));
                break;
        }

        ArrayList<Laser> lasersToRemove = new ArrayList<>();
        for(Laser laser : lasers) {
            laser.update(delta);
            if (laser.remove) {
                lasersToRemove.add(laser);
            }
            lasers.removeAll(lasersToRemove);
        }
        sb.begin();
        for(Laser laser : lasers) {
            laser.render(sb);
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
            currentActor.chosen.add(0, card);
            while (currentActor.chosen.size() > 5) {
                Card deletedCard = currentActor.chosen.remove(currentActor.chosen.size() - 1);
                handout.add(deletedCard);
            }
        }

        void deselectCard(int i){
            Card card = handout.get(i);
            currentActor.chosen.remove(card);
        }


        void createCards () {
            int cardX = 0;
            int cardY = 100;
            for (int i = 0; i < handout.size(); i++) {
                Card c = handout.get(i);
                if(c.isChosen){
                    c.setY(cardY+Gdx.graphics.getHeight() / 20);
                } else {
                    c.setY(cardY);
                }
                c.setX(cardStartX + cardX);
                cardX += c.cardWidth + Gdx.graphics.getWidth() / 128;
                c.create();
                c.render();
            }
        }

    public static int getAmountOfFlags() {
        return amountOfFlags;
    }


        @Override
        public boolean keyDown ( int keycode){
            float x = currentActor.getX();
            float y = currentActor.getY();
            Tile current = new Tile(0, 0, 0);
            try {
                current = grid.getTileWfloats(y, x);
                currentActor.setPreviousTile(current);
            } catch (NullPointerException e) {

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
                actor.takeDamage(0.1);
            }

            if (keycode == Input.Keys.S) {
                actor2.takeDamage(0.1);
            }

            if (keycode == Input.Keys.ENTER) {
                if(currentActor.chosen.size()==5){
                while (currentActor.chosen.size() > 0) {
                    Card action = currentActor.chosen.get(currentActor.chosen.size() - 1);
                    currentActor.chosen.remove(currentActor.chosen.size() - 1);
                    String type = getType(action);

                    if (type == "Move") {
                        System.out.println("Actor should move " + currentActor.getDir() + " by: " + action.getMoves());
                        currentActor.Forward(1 * action.getMoves(), moveDist, grid);

                    } else if (type.equals("Backup")) {
                        System.out.println("Actor should move backwards by: " + action.getMoves());
                        currentActor.backward(1, moveDist, grid);

                    } else if (type == "Turn") {
                        if (action.getTurn() == Card.Turn.LEFT) {
                            currentActor.turnLeft();
                        } else if (action.getTurn() == Card.Turn.RIGHT) {
                            currentActor.turnRight();
                        } else if (action.getTurn() == Card.Turn.UTURN) {
                            currentActor.uTurn();
                        }
                        System.out.println("It was a turn card. Actor turned " + action.getTurn());
                    }
                }

                System.out.println(currentActor + " has no cards left in chosen");
                changeActor();
                System.out.println(currentActor + " to choose cards.");
                keyDown(Input.Keys.ALT_LEFT);
                }
            }

            if (keycode == Input.Keys.ALT_LEFT) {
                handOut();

            }

            if (keycode == Input.Keys.E){
                currentActor.explosions.add(new Explosion(currentActor.getX(),currentActor.getY()));
            }


            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }

            if (keycode == Input.Keys.ESCAPE) {
                Gdx.app.exit();
            }

            if (keycode == Input.Keys.B) {
                currentActor.setBackupTile(current);
                System.out.println("Backup set to: " + current);
            }

            if (keycode == Input.Keys.O) {
                GameOverScreen gameOverScreen = new GameOverScreen(game, "Player 2");
                game.setScreen(gameOverScreen);
            }

            if (keycode == Input.Keys.L) {

            }

            return false;
        }

        @Override
        public boolean keyUp ( int keycode){
            if (currentActor.chosen.size() >= 5) System.out.println("You can't choose more cards");

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

        public void changeActor(){
            if(currentActor==actor){
                currentActor = actor2;
                activePlayer = currentActor.getName()+", you're up!";
            } else if(currentActor==actor2){
                currentActor = actor;
                activePlayer = currentActor.getName()+", you're up!";
            }
        }
    }
