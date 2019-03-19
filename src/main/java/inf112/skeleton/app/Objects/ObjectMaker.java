package inf112.skeleton.app.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import inf112.skeleton.app.Game.MyGame;
import inf112.skeleton.app.GridFunctionality.GridOfTiles;
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
    public List<IObject> yellowTeleports;
    public List<IObject> holes;
    public List<IObject> wrenches;

    public ObjectMaker(Map map, GridOfTiles grid){
        this.map = map;
        this.grid = grid;

        createActor();
        createActor2();
        createFlags();
        createBlueTeleports();
    }

    private void createFlags() {
        flags = new ArrayList<>();
        MapLayer layer = map.getMapLayer("Flags");
        int i = 0;
        for (MapObject flag : layer.getObjects()){
            RectangleMapObject flagRect = (RectangleMapObject) flag;
            Flag addThisFlagToMap = new Flag(flagRect, grid);
            flags.add(addThisFlagToMap);
            System.out.println("Flag placed at: " + addThisFlagToMap.flagTile);
            i++;
        }
    }

    private void createBlueTeleports(){
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

    private void createActor() {
        MyGame.Dir startDir = MyGame.Dir.NORTH;
        actor = new MyActor(new Texture("robbie.png"), startDir);
    }

    private void createActor2() {
        MyGame.Dir startDir = MyGame.Dir.EAST;
        actor2 = new MyActor((new Texture("robbie.png")), startDir);
    }
}
