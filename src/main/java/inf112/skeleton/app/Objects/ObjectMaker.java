package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
import inf112.skeleton.app.GridFunctionality.Tile;
import inf112.skeleton.app.Map.Map;
import inf112.skeleton.app.Objects.Actor.MyActor;

import java.util.ArrayList;
import java.util.List;

public class ObjectMaker {
    public Map map;
    public GridOfTiles grid;
    public MyActor actor;
    public MyActor actor2;
    public List<IObject> flags;
    public static List<IObject> blueTeleports;
    public static List<IObject> yellowTeleports;
    public static List<IObject> blueConveyors;
    public static List<IObject> redConveyors;
    public List<IObject> holes;
    public List<IObject> wrenches;

    public ObjectMaker(Map map, GridOfTiles grid) {
        this.map = map;
        this.grid = grid;

        createActor();
        createActor2();
        createFlags();
        createBlueTeleports();
        createYellowTeleports();
        createHoles();
        createBlueConveyors();
        createRedConveyors();
    }

    private void createFlags() {
        flags = new ArrayList<>();
        MapLayer layer = map.getMapLayer("Flags");
        int i = 0;
        for (MapObject flag : layer.getObjects()) {
            RectangleMapObject flagRect = (RectangleMapObject) flag;
            Flag addThisFlagToMap = new Flag(flagRect, grid);
            flags.add(addThisFlagToMap);
            System.out.println("Flag placed at: " + addThisFlagToMap.flagTile);
            i++;
        }
    }

    private void createBlueTeleports() {
        blueTeleports = new ArrayList<>();
        MapLayer layer = map.getMapLayer("BlueTeleport");
        int i = 0;
        for (MapObject blueTeleport : layer.getObjects()) {
            RectangleMapObject blueTelRect = (RectangleMapObject) blueTeleport;
            BlueTeleport addThisTeleportToMap = new BlueTeleport(blueTelRect, grid);
            blueTeleports.add(addThisTeleportToMap);
            System.out.println("Blue Teleport placed at: " + addThisTeleportToMap.bTeleportTileFrom);
            i++;
        }
    }

    private void createYellowTeleports() {
        yellowTeleports = new ArrayList<>();
        MapLayer layer = map.getMapLayer("YellowTeleport");
        int i = 0;
        for (MapObject yellowTeleport : layer.getObjects()) {
            RectangleMapObject yellowTelRect = (RectangleMapObject) yellowTeleport;
            YellowTeleport addThisTeleportToMap = new YellowTeleport(yellowTelRect, grid);
            yellowTeleports.add(addThisTeleportToMap);
            System.out.println("Yellow Teleport placed at: " + addThisTeleportToMap.yTeleportTileFrom);
            i++;
        }
    }

    private void createHoles() {
        holes = new ArrayList<>();
        MapLayer layer = map.getMapLayer("Holes");
        int i = 0;
        for (MapObject hole : layer.getObjects()) {
            RectangleMapObject holeRect = (RectangleMapObject) hole;
            Hole addThisHoleToMap = new Hole(holeRect, grid);
            holes.add(addThisHoleToMap);
            System.out.println("Hole found at: " + addThisHoleToMap.hole);
            i++;
        }
    }

    private void createBlueConveyors() {
        blueConveyors = new ArrayList<>();
        MapLayer layer = map.getMapLayer("BlueConv");
        int i = 0;
        for (MapObject blueConveyor : layer.getObjects()) {
            RectangleMapObject blueConvRect = (RectangleMapObject) blueConveyor;
            BlueConveyorBelt addThisConveyorToMap = new BlueConveyorBelt(blueConvRect, grid);
            blueConveyors.add(addThisConveyorToMap);
            i++;
        }
    }

    private void createRedConveyors() {
        redConveyors = new ArrayList<>();
        MapLayer layer = map.getMapLayer("RedConv");
        int i = 0;
        for (MapObject redConveyor : layer.getObjects()) {
            RectangleMapObject redConvRect = (RectangleMapObject) redConveyor;
            RedConveyorBelt addThisConveyorToMap = new RedConveyorBelt(redConvRect, grid);
            redConveyors.add(addThisConveyorToMap);
            i++;
        }
    }

    private void createActor() {
        MyGame.Dir startDir = MyGame.Dir.NORTH;
        actor = new MyActor(new Texture("robbie.png"), startDir);
    }

    private void createActor2() {
        MyGame.Dir startDir = MyGame.Dir.EAST;
        actor2 = new MyActor((new Texture("robbie.png")), startDir);
    }

    public int getConveyorLandedon(MyActor actor, GridOfTiles grid, String color) {
        List<IObject> search;
        if (color.equals("red")) search = redConveyors;
        return 0;
    }
}
