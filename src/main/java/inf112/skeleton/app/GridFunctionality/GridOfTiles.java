package inf112.skeleton.app.GridFunctionality;

import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.Objects.IObject;

import java.util.ArrayList;

public class GridOfTiles {
    public int pxSize;
    public int Nrow;
    public int Ncol;
    public int GridSize;
    Tile[][] grid;

    //Constructor
    public GridOfTiles(int Nrow, int Ncol, int pxSize) {
        this.pxSize = pxSize;
        this.Nrow = Nrow;
        this.Ncol = Ncol;
        grid = new Tile[Nrow][Ncol];
        this.GridSize = Nrow * Ncol;

        grid = makeGrid(Nrow, Ncol);
    }

    private Tile[][] makeGrid(int rows, int cols) {
        grid = new Tile[rows][cols];

        int y = 0;
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Tile(j, y, pxSize);
            }
            y++;
        }
        return grid;
    }

    public Tile getTileWfloats(float yCo, float xCo) {
        int xTileCoordinate = (int) xCo / pxSize;
        int yTileCoordinate = (int) yCo / pxSize;

        if ((xCo % pxSize == 0) && (yCo % pxSize == 0)) {
            int x = ((int) xCo / pxSize);
            int y = ((int) yCo / pxSize);
            return getTile(y, x);
        }
        return getTile(yTileCoordinate, xTileCoordinate);
    }

    public Tile getTileWithDirection(Tile on, MyGame.Dir dir) {
        int x = 0;
        int y = 0;
        switch (dir) {
            case NORTH:
                x = 0;
                y = 1;
            case EAST:
                x = 1;
                y = 0;
            case WEST:
                x = -1;
                y = 0;
            case SOUTH:
                x = 0;
                y = -1;
        }
        return getTile(on.y + y, on.x + x);
    }

    public Tile getTile(int y, int x) {
        if (x >= Ncol || y >= Nrow) {
            throw new IllegalArgumentException("Out of bounds");
        }
        Tile retTile = grid[(Nrow - 1) - y][x];
        return retTile;
    }

    public ArrayList<IObject> getAll() {
        ArrayList<IObject> list = new ArrayList<>();

        for (int i = 0; i < Nrow; i++) {
            for (int j = 0; j < Ncol; j++) {

                for (IObject obj : grid[i][j].getObjOnTile()) {
                    list.add(obj);
                }
            }
        }
        return list;
    }
}
