package inf112.skeleton.app.GridFunctionality;

import inf112.skeleton.app.Objects.IObject;

import java.util.ArrayList;

public class GridOfTiles {
    public int pxSize;
    public int Nrow;
    public int Ncol;
    public int GridSize;
    Tile[][] grid;

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

    public Tile getTile(int y, int x) {
        if (x >= Ncol || y >= Nrow) {
            throw new IllegalArgumentException("Out of bounds");
        }
        return grid[(Nrow - 1) - y][x];
    }

    public ArrayList<IObject> getAll() {
        ArrayList<IObject> list = new ArrayList<>();

        for (int i = 0; i < Nrow; i++) {
            for (int j = 0; j < Ncol; j++) {

                list.addAll(grid[i][j].getObjOnTile());
            }
        }
        return list;
    }
}
