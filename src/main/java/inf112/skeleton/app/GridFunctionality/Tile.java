package inf112.skeleton.app.GridFunctionality;

import inf112.skeleton.app.Objects.BlueConveyorBelt;
import inf112.skeleton.app.Objects.IObject;
import inf112.skeleton.app.Objects.RedConveyorBelt;

import java.util.ArrayList;

/***
 * A tile is a representation of a space that can:
 *  - Hold an item
 *  - Keep track of an actor's position
 *  - Represent a certain placement with [x, y] coordinates
 *  - Abbrieviate adjecent tiles
 *  - List certain objects on a specific tile
 */

public class Tile implements ITile, Comparable<Tile>{

    public int x;
    public int y;
    public int xCoordFrom;
    public int xCoordTo;
    public int yCoordFrom;
    public int yCoordTo;
    public boolean isConveyor;
    ArrayList<IObject> ObjOnTile;


    //Constructor for the tile:
    public Tile(int x, int y, int pxSize){
        this.x =x;
        this.y = y;
        xCoordFrom = x*pxSize;
        xCoordTo = (x+1) * pxSize;
        yCoordFrom = y*pxSize;
        yCoordTo=(y+1)*pxSize;
        ObjOnTile = new ArrayList<>();
    }

    public int getX(){ return this.x;}
    public int getY(){ return this.y;}

    public ArrayList<IObject> getObjOnTile() {
        return ObjOnTile;
    }

    public boolean equals(Object o){
        if (!(o instanceof Tile))
            return false;
        if (o == this)
            return true;

        Tile tile = (Tile) o;

        if (tile.x == this.x && tile.y == this.y)
            return true;

        return false;
    }

    public boolean isConveyor() {
        return isConveyor;
    }

    public void addObjOnTile(IObject obj) {
        ObjOnTile.add(obj);
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y +"]";
    }

    @Override
    public int compareTo(Tile o) {
        return 0;
    }
}
