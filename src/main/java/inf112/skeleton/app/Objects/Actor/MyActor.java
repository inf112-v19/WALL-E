package inf112.skeleton.app.Objects.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.CardFunctionality.Card;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Collision;
import inf112.skeleton.app.Animations.Explosion;
import inf112.skeleton.app.Objects.IObject;

import java.util.ArrayList;

import static inf112.skeleton.app.CardFunctionality.Card.getType;

public class MyActor implements IObject, IActor {
    public boolean gameOver;
    public boolean isDead;
    public boolean isCPU;
    MyGame.Dir currentDir;
    Tile backupTile;
    Tile previousTile;
    Sprite actorSprite;
    public float x;
    public float y;
    public ArrayList<Card> chosen;
    String textureFile;
    float health;
    public Tile currentTile;
    public ArrayList<Tile> tilesVisited = new ArrayList<>(11*11);
    public ArrayList<Explosion> explosions;
    private String name;
    public int actorIndex;
    public boolean choseFiveCards;

    public MyActor(String textureFile, MyGame.Dir startDir, boolean isCPU, String name, int actorIndex){
        this.gameOver = false;
        this.choseFiveCards = false;
        this.currentDir = startDir;
        this.textureFile = textureFile;
        this.backupTile = null;
        this.isCPU = isCPU;
        this.name = name;
        this.actorIndex = actorIndex;
    }

    public void create() {
        this.actorSprite = new Sprite(new Texture(textureFile));
        this.actorSprite.setSize(150, 150);
        this.actorSprite.setOrigin((float) 150 / 2, (float) 150 / 2);
        this.backupTile = null;
        this.health = 1;
        this.previousTile = null;
        explosions = new ArrayList<>();
        chosen = new ArrayList<>(5);
        name = "";
    }

    public void Forward(int steps, int moveDist, GridOfTiles grid){
        for (int i = 0; i < steps; i++) {
            moveForward(moveDist, grid);
            currentTile = grid.getTileWfloats(this.y, this.x);
            CollisionCheck(grid);
        }
    }

    private void CollisionCheck(GridOfTiles grid) {
        Collision collision = new Collision(grid, this);
        collision.collisionCheck();
    }

    private void moveForward(int moveDist, GridOfTiles grid) {
        switch (currentDir){
            case NORTH:
                this.setPosition((int) getY()+moveDist, (int) getX(), grid);
                break;
            case EAST:
                this.setPosition((int) getY(), (int) getX() + moveDist, grid);
                break;
            case SOUTH:
                this.setPosition((int) getY()- moveDist, (int) getX(), grid);
                break;
            case WEST:
                this.setPosition((int) getY(), (int) getX() - moveDist, grid);
                break;
        }
    }


    public void backward(int steps, int moveDist, GridOfTiles grid){
        for (int i = 0; i < steps; i++) {
            moveBackward(moveDist, grid);
        }
        CollisionCheck(grid);
    }

    private void moveBackward(int moveDist, GridOfTiles grid) {
        switch (currentDir){
            case NORTH:
                this.setPosition((int) getY()-moveDist, (int) getX(), grid);
                break;
            case EAST:
                this.setPosition((int) getY(), (int) getX() - moveDist, grid);
                break;
            case SOUTH:
                this.setPosition((int) getY()+ moveDist, (int) getX(), grid);
                break;
            case WEST:
                this.setPosition((int) getY(), (int) getX() + moveDist, grid);
                break;
        }
    }

    public void turnRight(){
        if (actorSprite != null)
            actorSprite.rotate(-90);

        switch (currentDir){
            case NORTH:
                currentDir = MyGame.Dir.EAST;
                break;
            case EAST:
                currentDir = MyGame.Dir.SOUTH;
                break;
            case SOUTH:
                currentDir = MyGame.Dir.WEST;
                break;
            case WEST:
                currentDir = MyGame.Dir.NORTH;
                break;
        }
    }

    public void turnLeft(){
        if (actorSprite != null)
            actorSprite.rotate(90);

        switch (currentDir){
            case NORTH:
                currentDir = MyGame.Dir.WEST;
                break;
            case WEST:
                currentDir = MyGame.Dir.SOUTH;
                break;
            case SOUTH:
                currentDir = MyGame.Dir.EAST;
                break;
            case EAST:
                currentDir = MyGame.Dir.NORTH;
                break;
        }
    }

    public void uTurn(){
        if (actorSprite != null)
            actorSprite.rotate(180);

        switch (currentDir){
            case NORTH:
                currentDir = MyGame.Dir.SOUTH;
                break;
            case EAST:
                currentDir = MyGame.Dir.WEST;
                break;
            case SOUTH:
                currentDir = MyGame.Dir.NORTH;
                break;
            case WEST:
                currentDir = MyGame.Dir.EAST;
                break;
        }
    }

    public Tile getBackupTile(){
        return this.backupTile;
    }

    public void setBackupTile(Tile backupTile){
        this.backupTile = backupTile;
        System.out.println("New backup location: " + backupTile);
    }

    public void deleteBackup(){
        this.backupTile = null;
    }

    public void backToBackup(GridOfTiles grid){
        int pxSize = grid.pxSize;
        if (this.backupTile != null){
            setPosition(backupTile.y*pxSize, backupTile.x*pxSize, grid);
            System.out.println(this.name + " to backup: " + grid.getTileWfloats(this.getY(), this.getX()) + ", Actor no longer has a backup");
        }
    }

    public void setPosition(int y, int x, GridOfTiles grid) {
        if (grid != null && checkOutOfBounds(y, x, grid)) {
            death(grid);
            return;
        }
        setX(x);
        setY(y);

        if (grid != null) {
            Tile current = grid.getTileWfloats(getY(), getX());
            current.getObjOnTile().remove(this);
            grid.getTileWfloats(y, x).addObjOnTile(this);
        }

    }

    private void death(GridOfTiles grid) {
        if(backupTile != null){
            explosions.add(new Explosion(getX(),getY()));
            //System.out.println("Explosion added to "+ grid.getTileWfloats(getX(),getY()));
            chosen.clear();
            takeDamage(0.1);
            backToBackup(grid);
            deleteBackup();
        } else{
            explosions.add(new Explosion(getX(),getY()));
            //System.out.println("Explosion added to "+ grid.getTileWfloats(getX(),getY()));
            chosen.clear();
            takeDamage(0.1);
            System.out.println(this.name + " took damage! Out of bounds.");
            this.setBackupTile(grid.getTileWfloats(0, 0));
            this.backToBackup(grid);
        }

    }

    public void setX(float x) {
       this.x = x;
    }


    public void setY(float y){
        this.y = y;
    }

    public boolean checkOutOfBounds(int y, int x, GridOfTiles grid){
        if (y<0||x<0) return true;
        int TileY = y/grid.pxSize;
        int TileX = x/grid.pxSize;
        return TileY >= grid.Nrow || TileX >= grid.Ncol;

    }

    public void takeDamage(double i){
        this.health -= i;
    }

    public float getHealth(){
        return health;
    }

    public Tile getPreviousTile() {
        return this.previousTile;
    }

    public void setPreviousTile(Tile tile){
        previousTile = tile;
    }

    public float getY() {
        return this.y;
    }

    public float getX() {
        return this.x;
    }

    //public String getName(){return this.name;}

    public String getName() {
        return this.name;
    }

    public void setName(String name){ this.name = name;}

    @Override
    public Boolean isCPU() {
        return null;
    }

    @Override
    public Sprite getSprite() {
        actorSprite.setX(this.x);
        actorSprite.setY(this.y);
        return this.actorSprite;
    }

    @Override
    public Tile getTile() {
        float x = this.getX();
        float y = this.getY();
        return MyGame.grid.getTileWfloats(y, x);
    }

    @Override
    public boolean alive() {
        return false;
    }


    public MyGame.Dir getDir() {
        return currentDir;
    }

    public void restoreHealth(double v) {
        if(health<1) this.health += v;
        if(health>1) this.health = 1;
    }

    public void moveToTile(Tile destination, GridOfTiles grid){
       int moveDist = grid.pxSize;
       float moveX =  moveDist * (destination.x - this.x);
       float moveY =  moveDist * (destination.y - this.y);
       this.setPosition((int)(this.y + moveY), (int)(this.x + moveX), grid);

    }

    public void setDir(MyGame.Dir conveyorDirection) {
        currentDir = conveyorDirection;
    }

    public void moveInDirection(int toMove, MyGame.Dir conveyorDirection, GridOfTiles grid) {
        currentDir = conveyorDirection;
        Forward(1, toMove, grid);
    }

    public void visitedTile(Tile flagTile) {
        tilesVisited.add(flagTile);
    }

}
