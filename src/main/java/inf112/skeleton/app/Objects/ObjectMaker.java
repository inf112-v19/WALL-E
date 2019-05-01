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
    static ArrayList<Tile> tilesWithFlags;
    static List<IObject> blueTeleports;
    static List<IObject> yellowTeleports;
    private static List<IObject> redConveyors;
    private Map map;
    public GridOfTiles grid;
    public MyActor actor;
    public MyActor actor2;
    public List<IObject> flags;
    private ArrayList<Object> turners;

    public ObjectMaker(Map map, GridOfTiles grid) {
        this.map = map;
        this.grid = grid;

        createActor();
        createActor2();
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
        createTurners();
    }

    private void createTurners() {
        turners = new ArrayList<>();
        MapLayer layer = map.getMapLayer("SnurreDings");
        for (MapObject turner : layer.getObjects()) {
            RectangleMapObject turnRect = (RectangleMapObject) turner;
            Turner addThisTurnerToMap = new Turner(turnRect, grid);
            turners.add(addThisTurnerToMap);
            System.out.println("Turner placed at: "+addThisTurnerToMap.turnerTile);
        }
    }

    private void createFlags() {
        flags = new ArrayList<>();
        tilesWithFlags = new ArrayList<>(flags.size());
        MapLayer layer = map.getMapLayer("Flags");
        for (MapObject flag : layer.getObjects()) {
            RectangleMapObject flagRect = (RectangleMapObject) flag;
            Flag addThisFlagToMap = new Flag(flagRect, grid);
            flags.add(addThisFlagToMap);
            tilesWithFlags.add(addThisFlagToMap.flagTile);
            System.out.println("Flag placed at: " + addThisFlagToMap.flagTile);
        }
    }

    private void createBlueTeleports() {
        blueTeleports = new ArrayList<>();
        MapLayer layer = map.getMapLayer("BlueTeleport");
        for (MapObject blueTeleport : layer.getObjects()) {
            RectangleMapObject blueTelRect = (RectangleMapObject) blueTeleport;
            BlueTeleport addThisTeleportToMap = new BlueTeleport(blueTelRect, grid);
            blueTeleports.add(addThisTeleportToMap);
            System.out.println("Blue Teleport placed at: " + addThisTeleportToMap.bTeleportTileFrom);
        }
    }

    private void createYellowTeleports() {
        yellowTeleports = new ArrayList<>();
        MapLayer layer = map.getMapLayer("YellowTeleport");
        for (MapObject yellowTeleport : layer.getObjects()) {
            RectangleMapObject yellowTelRect = (RectangleMapObject) yellowTeleport;
            YellowTeleport addThisTeleportToMap = new YellowTeleport(yellowTelRect, grid);
            yellowTeleports.add(addThisTeleportToMap);
            System.out.println("Yellow Teleport placed at: " + addThisTeleportToMap.yTeleportTileFrom);
        }
    }

    private void createHoles() {
        List<IObject> holes = new ArrayList<>();
        MapLayer layer = map.getMapLayer("Holes");
        for (MapObject hole : layer.getObjects()) {
            RectangleMapObject holeRect = (RectangleMapObject) hole;
            Hole addThisHoleToMap = new Hole(holeRect, grid);
            holes.add(addThisHoleToMap);
            System.out.println("Hole found at: " + addThisHoleToMap.hole);
        }
    }

    private void createBlueConveyors() {
        List<IObject> blueConveyors = new ArrayList<>();
        MapLayer layer = map.getMapLayer("BlueConv");
        for (MapObject blueConveyor : layer.getObjects()) {
            RectangleMapObject blueConvRect = (RectangleMapObject) blueConveyor;
            BlueConveyorBelt addThisConveyorToMap = new BlueConveyorBelt(blueConvRect, grid, 1);
            blueConveyors.add(addThisConveyorToMap);
        }
    }

    private void createRedConveyors() {
        redConveyors = new ArrayList<>();
        MapLayer layer = map.getMapLayer("RedConv");
        for (MapObject redConveyor : layer.getObjects()) {
            RectangleMapObject redConvRect = (RectangleMapObject) redConveyor;
            RedConveyorBelt addThisConveyorToMap = new RedConveyorBelt(redConvRect, grid, 1);
            redConveyors.add(addThisConveyorToMap);
        }
    }

    private void createWrenchesSingle() {
        List<IObject> singleWrenches = new ArrayList<>();
        MapLayer layer = map.getMapLayer("WrenchesSingle");
        for (MapObject wrench : layer.getObjects()) {
            RectangleMapObject wrenchRect = (RectangleMapObject) wrench;
            WrenchesSingle addThisWrenchToMap = new WrenchesSingle(wrenchRect, grid);
            singleWrenches.add(addThisWrenchToMap);
            System.out.println("Single wrench at: " + addThisWrenchToMap.wrenchTile);
        }
    }

    private void createWrenchesDouble() {
        List<IObject> doubleWrenches = new ArrayList<>();
        MapLayer layer = map.getMapLayer("WrenchesDouble");
        for (MapObject wrench : layer.getObjects()) {
            RectangleMapObject wrenchRect = (RectangleMapObject) wrench;
            WrenchesDouble addThisWrenchToMap = new WrenchesDouble(wrenchRect, grid);
            doubleWrenches.add(addThisWrenchToMap);
            System.out.println("Double wrench at: " + addThisWrenchToMap.wrenchTile);
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
}
