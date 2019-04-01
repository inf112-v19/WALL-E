package inf112.skeleton.app.Objects;

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
    public static ArrayList<Tile> tilesWithFlags;
    public static List<IObject> blueTeleports;
    public static List<IObject> yellowTeleports;
    public static List<IObject> blueConveyors;
    public static List<IObject> redConveyors;
    public Map map;
    public GridOfTiles grid;
    public MyActor actor;
    public MyActor actor2;
    public List<IObject> flags;
    public List<IObject> holes;
    public List<IObject> singleWrenches;
    public List<IObject> doubleWrenches;

    public ObjectMaker(Map map, GridOfTiles grid) {
        this.map = map;
        this.grid = grid;

        createActor();
        createActor2();
    }

    public static ArrayList<Tile> getTilesWithFlags() {
        return tilesWithFlags;
    }

    public void create() {
        createFlags();
        createBlueTeleports();
        createYellowTeleports();
        createHoles();
        createBlueConveyors();
        createRedConveyors();
        createWrenchesSingle();
        createWrenchesDouble();
    }

    private void createFlags() {
        flags = new ArrayList<>();
        tilesWithFlags = new ArrayList<>(flags.size());
        MapLayer layer = map.getMapLayer("Flags");
        int i = 0;
        for (MapObject flag : layer.getObjects()) {
            RectangleMapObject flagRect = (RectangleMapObject) flag;
            Flag addThisFlagToMap = new Flag(flagRect, grid);
            flags.add(addThisFlagToMap);
            tilesWithFlags.add(addThisFlagToMap.flagTile);
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
            BlueConveyorBelt addThisConveyorToMap = new BlueConveyorBelt(blueConvRect, grid, 1);
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
            RedConveyorBelt addThisConveyorToMap = new RedConveyorBelt(redConvRect, grid, 1);
            redConveyors.add(addThisConveyorToMap);
            i++;
        }
    }

    private void createWrenchesSingle() {
        singleWrenches = new ArrayList<>();
        MapLayer layer = map.getMapLayer("WrenchesSingle");
        int i = 0;
        for (MapObject wrench : layer.getObjects()) {
            RectangleMapObject wrenchRect = (RectangleMapObject) wrench;
            WrenchesSingle addThisWrenchToMap = new WrenchesSingle(wrenchRect, grid);
            singleWrenches.add(addThisWrenchToMap);
            System.out.println("Single wrench at: " + addThisWrenchToMap.wrenchTile);
            i++;
        }
    }

    private void createWrenchesDouble() {
        doubleWrenches = new ArrayList<>();
        MapLayer layer = map.getMapLayer("WrenchesDouble");
        int i = 0;
        for (MapObject wrench : layer.getObjects()) {
            RectangleMapObject wrenchRect = (RectangleMapObject) wrench;
            WrenchesDouble addThisWrenchToMap = new WrenchesDouble(wrenchRect, grid);
            doubleWrenches.add(addThisWrenchToMap);
            System.out.println("Double wrench at: " + addThisWrenchToMap.wrenchTile);
            i++;
        }
    }

    private void createActor() {
        MyGame.Dir startDir = MyGame.Dir.NORTH;
        actor = new MyActor("blaTanks1.png", startDir, false, "Player One", 0);
    }

    private void createActor2() {
        MyGame.Dir startDir = MyGame.Dir.NORTH;
        actor2 = new MyActor("redTanks1.png", startDir, true, "Computer Player", 1);
    }

    public int getConveyorLandedon(MyActor actor, GridOfTiles grid, String color) {
        List<IObject> search;
        if (color.equals("red")) search = redConveyors;
        return 0;
    }
}
