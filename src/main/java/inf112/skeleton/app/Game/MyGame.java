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
import inf112.skeleton.app.Objects.Laser;
import inf112.skeleton.app.Objects.MyLaser;
import inf112.skeleton.app.Objects.ObjectMaker;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class MyGame extends ApplicationAdapter implements InputProcessor, Screen {
    public static GridOfTiles grid;
    public static OrthographicCamera camera;
    private static int amountOfFlags;
    private int PXSIZE = 78;
    private TiledMap tiledMap;
    public MyActor actor;
    private ArrayList<MyActor> actors;
    private ArrayList<HealthBar> healthbars;
    public MyActor currentActor;
    public Map map;
    public Deck deck;
    private Sprite BackBoard;
    private Batch batch;
    private Texture texture;
    private MyLaser renderLaser;
    private Sprite laserTexture;
    public ArrayList<Card> handout = new ArrayList<>(9);
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch sb;
    private RoboRally game;
    private BitmapFont font;
    private float textPositionX;
    private float textPositionY;
    private int cardStartX;
    private String activePlayer;
    private boolean hasSwappedActor;

    public MyGame() {
        this(null);

        ObjectMaker objectMaker = new ObjectMaker(null, null);
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
    }

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
        actors = new ArrayList<>();
        healthbars = new ArrayList<>();
        if(PlayOptions.getPlayers()==1){
            MyActor actor = objectMaker.createActorCPU();
            actor.create();
            actors.add(actor);
        }
        for(int i=0;i<PlayOptions.getPlayers();i++){
            MyGame.Dir startDir = MyGame.Dir.NORTH;
            MyActor actor = new MyActor("blaTanks1.png", startDir, false, "Player One", 0);

            if(i == 0){
                actor = objectMaker.createActorBlue();
            } else if(i == 1){
                actor = objectMaker.createActorRed();
            }else if(i == 2){
                actor = objectMaker.createActorBlue2();
            }else if(i == 3){
                actor = objectMaker.createActorRed2();
            }else if(i == 4){
                actor = objectMaker.createActorBlue3();
            }else if(i == 5){
                actor = objectMaker.createActorRed3();
            }else if(i == 6){
                actor = objectMaker.createActorBlue4();
            }else if(i == 7){
                actor = objectMaker.createActorRed4();
            }
            actor.create();
            actors.add(actor);
        }
        for(int i=0;i<PlayOptions.getCPUPlayers();i++) {
            MyActor actor = objectMaker.createActorCPU();
            actor.setName("CPU " + (i+1));
            actor.create();
            actors.add(actor);
        }

        for(int i=0;i<actors.size();i++){
            grid.getTileWfloats(0, 0).addObjOnTile(actors.get(i));
        }

        for(int i = 0; i<actors.size();i++){
            HealthBar healthBar = new HealthBar(actors.get(i),actors.get(i).getName(),i+1);
            healthbars.add(healthBar);
        }

        currentActor = actors.get(0);
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
        if(currentActor.isDead){
            grid.getTileWfloats(currentActor.y,currentActor.x).removeObject(currentActor);
        }
        checkWinner();

        for(HealthBar health : healthbars){
            health.render();
        }

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

        isDead();


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

    private void checkWinner() {
        for(MyActor act : actors){
            if(act.gameOver){
                GameOverScreen gameOverScreen = new GameOverScreen(game, act.getName());
                game.setScreen(gameOverScreen);
            }
            if(nextAlive() == currentActor){
                GameOverScreen gameOverScreen = new GameOverScreen(game, currentActor.getName());
                game.setScreen(gameOverScreen);
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
        if (keycode == Input.Keys.V) {
            changeActor();
        }

            if (keycode == Input.Keys.S) {
                //actor2.takeDamage(0.1);
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

        return false;
    }

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

    private void isDead(){
        for(MyActor actor : actors){
            if(actor.getHealth()<=0){
                actor.isDead = true;
            }
        }
    }

    private void changeActor() {
        currentActor = nextAlive();
        activePlayer = currentActor.getName() + " you're up!";

    }
    private MyActor nextAlive(){
        int index = 0;
        int newIndex = 0;
        for(int i=0;i<actors.size();i++) {
            if (actors.get(i) == currentActor) {
                index = i;
            }
        }
        for(int i=1;i<actors.size();i++) {
            newIndex = (index + i) % actors.size();

            if (!actors.get(newIndex).isDead) {
                return actors.get(newIndex);
            }
        }
        return currentActor;
    }

    public enum Dir {
        NORTH,
        EAST,
        WEST,
        SOUTH
    }
}
