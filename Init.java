package hicks.combat;

import hicks.combat.entities.Barracks;
import hicks.combat.entities.Unit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Init
{
    public static int WORLD_WIDTH = 1024;
    public static int WORLD_HEIGHT = 768;
    public static boolean heavyLogging = false;

    public static void init()
    {
        // delete previous log
        if (new File("log.txt").delete())
            Log.logInfo("Deleting old logs...");

        NameLogic.init();

        GameState.setStartTime(GameLogic.now());
        Log.logInfo("Simulation starting at " + new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(GameState.getStartTime()));

        GameMap map = new GameMap();
        GameState.setGameMap(map);

        map.setWidth(WORLD_WIDTH);
        map.setHeight(WORLD_HEIGHT);

        // create a barracks for each team
        for (int i = 0; i < 1; i++)
        {
            GameState.addUnit(new Barracks(0));
            GameState.addUnit(new Barracks(1));
        }

        List<Unit> unitsToPlace = new ArrayList<>(GameState.getUnits());
        GameLogic.placeUnitsRandomlyOnEachHalfOfMap(map, unitsToPlace);
    }
}
