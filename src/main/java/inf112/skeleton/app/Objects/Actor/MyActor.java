package inf112.skeleton.app.Objects.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Objects.Collision;
import inf112.skeleton.app.Objects.IObject;

public class MyActor implements IObject, IActor {
    MyGame.Dir currentDir;
    Tile backupTile;
    Sprite actorSprite;
    float x;
    float y;

    public MyActor(Texture texture, MyGame.Dir startDir){
        this.currentDir = startDir;
        this.actorSprite = new Sprite(texture);
        this.actorSprite.setSize(150, 150);
        this.backupTile = null;
    }

    public void Forward(int steps, int moveDist, GridOfTiles grid){
        for (int i = 0; i < steps; i++) {
            moveForward(moveDist, grid);
        }
        CollisionCheck(grid);
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

    public void turnRight(){
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
            System.out.println(grid.getTileWfloats(this.getX(), this.getY()));
        }
    }

    public void setPosition(int y, int x, GridOfTiles grid) {
        if(checkOutOfBounds(y, x, grid)){
            death(grid);
            return;
        }
        Tile current = grid.getTileWfloats(getX(), getY());
        setX(x);
        setY(y);

        current.getObjOnTile().remove(this);
        grid.getTileWfloats(x, y).addObjOnTile(this);

    }

    private void death(GridOfTiles grid) {
        if(backupTile != null){
            backToBackup(grid);
            deleteBackup();
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
        if(TileY >= grid.Nrow || TileX >= grid.Ncol) return true;

        return false;
    }


    public float getY() {
        return this.y;
    }

    public float getX() {
        return this.x;
    }

    @Override
    public String getName(String name) {
        return null;
    }

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
    public boolean alive() {
        return false;
    }


    public MyGame.Dir getDir() {
        return currentDir;
    }
}