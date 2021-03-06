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
import inf112.skeleton.app.Objects.MyLaser;
import inf112.skeleton.app.Objects.ObjectMaker;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class MyGame extends ApplicationAdapter implements InputProcessor, Screen {
    public static GridOfTiles grid;
    public static OrthographicCamera camera;
    private int PXSIZE = 78;
    private TiledMap tiledMap;
    public MyActor actor;
    private ArrayList<MyActor> actors;
    private ArrayList<HealthBar> healthbars;
    private MyActor currentActor;
    private Map map;
    private Deck deck;
    private Batch batch;
    public ArrayList<Card> handout = new ArrayList<>(9);
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch sb;
    private RoboRally game;
    private BitmapFont font;
    private float textPositionX;
    private float textPositionY;
    private int cardStartX;
    private String activePlayer;
    private String playerInstructionsCtrl;
    private String getPlayerInstructionsEnter;
    private float instructionX;
    private float instructionX2;
    private float instructionY;
    private int phaseNumber = 4;

    public MyGame() {
        this(null);
        ObjectMaker objectMaker = new ObjectMaker(null, null);
    }

    MyGame(RoboRally game) {
        this.game = game;
        deck = new Deck();
        handOut();
    }

    @Override
    public void create() {
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
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        double textX = WIDTH - (WIDTH * 0.93);
        double textY = HEIGHT - (HEIGHT * 0.04);
        textPositionX = (float) textX;
        textPositionY = (float) textY;
        double instX = WIDTH - (WIDTH * 0.97);
        double instX2 = WIDTH - (WIDTH * 0.2);
        double instY = HEIGHT - (HEIGHT * 0.9);
        instructionX2 = (float) instX2;
        instructionX = (float) instX;
        instructionY = (float) instY;
        playerInstructionsCtrl = "Press CTRL to deselect card";
        getPlayerInstructionsEnter = "Press ENTER to confirm selection!";


        cardStartX = WIDTH / 6;
        ObjectMaker objectMaker;
        objectMaker = new ObjectMaker(map, grid);
        objectMaker.create();
        actors = new ArrayList<>();
        healthbars = new ArrayList<>();
        for(int i=0;i<PlayOptions.getPlayers();i++){
            MyActor actor = objectMaker.createActorBlue();

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
        if(PlayOptions.getPlayers()==1 && PlayOptions.getCPUPlayers() == 0){
            MyActor actor = objectMaker.createActorCPU();
            actor.setName("CPU " + 1);
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
        font.getData().setScale(1);
        font.draw(batch, playerInstructionsCtrl,instructionX,instructionY);
        font.draw(batch, getPlayerInstructionsEnter,instructionX2,instructionY);
        batch.end();

        for (Explosion explosion : previousActor().explosions) {
            sb.begin();
            explosion.render(sb);
            sb.end();
        }

        ArrayList<Explosion> explosionsToRemove = new ArrayList<>();
        for (Explosion explosion : previousActor().explosions) {
            explosion.update(v);
            if (explosion.remove)
                explosionsToRemove.add(explosion);
        }
        previousActor().explosions.removeAll(explosionsToRemove);

        isDead();


        if (currentActor.isCPU) {
            goToCPUActions(currentActor);
        } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (currentActor.chosen.size() >= 5)
                System.out.println(currentActor.getName() + " can't choose more cards");
            else {
                if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(0).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(0).getY() + handout.get(0).getHeight() && Gdx.input.getX() > handout.get(0).getX() && Gdx.input.getX() < handout.get(0).getX() + handout.get(0).getWidth()) {
                    if (!handout.get(0).isChosen) {
                        chooseCard(0);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(0)) + " | Num :" + (1));
                        handout.get(0).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(1).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(1).getY() + handout.get(1).getHeight() && Gdx.input.getX() > handout.get(1).getX() && Gdx.input.getX() < handout.get(1).getX() + handout.get(1).getWidth()) {
                    if (!handout.get(1).isChosen) {
                        chooseCard(1);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(1)) + " | Num :" + (2));
                        handout.get(1).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(2).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(2).getY() + handout.get(2).getHeight() && Gdx.input.getX() > handout.get(2).getX() && Gdx.input.getX() < handout.get(2).getX() + handout.get(2).getWidth()) {
                    if (!handout.get(2).isChosen) {
                        chooseCard(2);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(2)) + " | Num :" + (3));
                        handout.get(2).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(3).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(3).getY() + handout.get(3).getHeight() && Gdx.input.getX() > handout.get(3).getX() && Gdx.input.getX() < handout.get(3).getX() + handout.get(3).getWidth()) {
                    if (!handout.get(3).isChosen) {
                        chooseCard(3);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(3)) + " | Num :" + (4));
                        handout.get(3).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(4).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(4).getY() + handout.get(4).getHeight() && Gdx.input.getX() > handout.get(4).getX() && Gdx.input.getX() < handout.get(4).getX() + handout.get(4).getWidth()) {
                    if (!handout.get(4).isChosen) {
                        chooseCard(4);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(4)) + " | Num :" + (5));
                        handout.get(4).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(5).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(5).getY() + handout.get(5).getHeight() && Gdx.input.getX() > handout.get(5).getX() && Gdx.input.getX() < handout.get(5).getX() + handout.get(5).getWidth()) {
                    if (!handout.get(5).isChosen) {
                        chooseCard(5);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(5)) + " | Num :" + (6));
                        handout.get(5).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(6).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(6).getY() + handout.get(6).getHeight() && Gdx.input.getX() > handout.get(6).getX() && Gdx.input.getX() < handout.get(6).getX() + handout.get(6).getWidth()) {
                    if (!handout.get(6).isChosen) {
                        chooseCard(6);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(6)) + " | Num :" + (7));
                        handout.get(6).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(7).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(7).getY() + handout.get(7).getHeight() && Gdx.input.getX() > handout.get(7).getX() && Gdx.input.getX() < handout.get(7).getX() + handout.get(7).getWidth()) {
                    if (!handout.get(7).isChosen) {
                        chooseCard(7);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(7)) + " | Num :" + (8));
                        handout.get(7).isChosen = true;
                    }
                }
                else if (Gdx.graphics.getHeight() - Gdx.input.getY() > handout.get(8).getY() && Gdx.graphics.getHeight() - Gdx.input.getY() < handout.get(8).getY() + handout.get(8).getHeight() && Gdx.input.getX() > handout.get(8).getX() && Gdx.input.getX() < handout.get(8).getX() + handout.get(8).getWidth()) {
                    if (!handout.get(8).isChosen) {
                        chooseCard(8);
                        System.out.println(currentActor.getName() + " chose: " + getType(handout.get(8)) + " | Num :" + (9));
                        handout.get(8).isChosen = true;
                    }
                }
            }
        }
    }

    public void removeCardFromChosenAndHandout(Card card, MyActor actor) {
        actor.chosen.remove(card);
        handout.remove(card);
        card.dispose();
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
        for (int i = 0; i<5; i++){
            Card addForCPU = handout.get(i);
            CPU.chosen.add(addForCPU);
            System.out.println(CPU.getName() + " chose: " + getType(handout.get(i)) + " | Num :" + i);
        }
        keyDown(Input.Keys.ENTER);
    }

    private void shootLaserWithActor(MyActor actor){
        MyLaser laser = new MyLaser(grid, actor, actor.getTile(), 0, 3);
        laser.shootLaser();
    }

    private int getTileSize() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getMapLayer(0);
        return (int) layer.getTileWidth();
    }

    private void handOut() {
        if (handout.isEmpty() || currentActor.isCPU) {
            handout.clear();
            for (int i = 0; i < 9; i++)
                handout.add(deck.handOut());
        } else {
            handout.clear();
            lessHpLessCards();
        }
    }

    private void lessHpLessCards() {
        int i = 8;
        float actorHp = currentActor.getHealth();
        float hpStep = (float) 1.0;

        while (i>=0){
            if (actorHp>=hpStep || i<5)
                handout.add(deck.handOut());

            hpStep-=0.10;
            i--;
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

    private void chooseCard(int i) {
        Card card = handout.get(i);
        currentActor.chosen.add(0, card);
        while (currentActor.chosen.size() > 5) {
            Card deletedCard = currentActor.chosen.remove(currentActor.chosen.size() - 1);
            handout.add(deletedCard);
        }
    }

    private void deselectAll() {
        for(Card card : handout){
            if(!card.isLocked) {
                card.isChosen = false;
                currentActor.chosen.remove(card);
            }
        }
    }
    private void deselectCard(int i){
        Card card = handout.get(i);
        card.isChosen= false;
        card.isLocked = false;
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
            currentActor.powerDown();
            for (int i=0; i<handout.size(); i++)
                deselectCard(i);

            System.out.println(currentActor.getName() + " powered down.");
            changeActor();
        }

        if (keycode == Input.Keys.ENTER) {
            if(currentActor.chosen.size()==5) {
                changeActor();
                handOut();
            } if(currentActor == actors.get(0) && currentActor.chosen.size()==5){
                for(int i=0;i<5;i++){
                    keyDown(Input.Keys.P);
                }
            }
        }

        if (keycode == Input.Keys.ALT_LEFT) {
            handOut();
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

        if(keycode == Input.Keys.CONTROL_LEFT){
            deselectAll();
        }

        if(keycode == Input.Keys.P){
            Phase phase = new Phase(this, actors, currentActor);
            switch (phaseNumber) {
                case 4:
                    phase.playPhase(4);
                    setPhase(3);
                    break;
                case 3:
                    phase.playPhase(3);
                    setPhase(2);
                    break;
                case 2:
                    phase.playPhase(2);
                    setPhase(1);
                    break;
                case 1:
                    phase.playPhase(1);
                    setPhase(0);
                    break;
                case 0:
                    phase.playPhase(0);
                    setPhase(4);
                    System.out.println("Ready for next phase");
                    for (MyActor a : actors) {
                        System.out.println("ALL PLAYERS SHOOT");
                        shootLaserWithActor(a);
                        a.chosen = new ArrayList<>(4);
                    }
                    break;
            }
        }

        return false;
    }

    private void setPhase(int i) {
        this.phaseNumber = i;
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


    public int getPXSIZE() {
        return PXSIZE;
    }

    public GridOfTiles getGrid() {
        return grid;

    }
  
    private MyActor previousActor(){
        for(int i=0;i<actors.size(); i++){
            if(actors.get(i)==currentActor){
                if(i==0){
                    return actors.get(actors.size()-1);
                }
                return actors.get(i-1);
            }
        }
        return null;
    }

    public enum Dir {
        NORTH,
        EAST,
        WEST,
        SOUTH
    }
}
