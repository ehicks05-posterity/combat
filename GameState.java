package hicks.combat;

import hicks.combat.entities.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GameState
{
    private static List<Unit> units = new ArrayList<>();
    private static GameMap gameMap;
    private static BigDecimal startTime;
    private static int teamChosen;

    public static void addUnit(Unit unit)
    {
        units.add(unit);
    }

    public static void removeUnit(Unit unit)
    {
        units.remove(unit);
    }

    // ------------------------

    public static List<Unit> getUnits()
    {
        return units;
    }

    public static GameMap getGameMap()
    {
        return gameMap;
    }

    public static void setGameMap(GameMap gameMap)
    {
        GameState.gameMap = gameMap;
    }

    public static BigDecimal getStartTime()
    {
        return startTime;
    }

    public static void setStartTime(BigDecimal startTime)
    {
        GameState.startTime = startTime;
    }

    public static int getTeamChosen()
    {
        return teamChosen;
    }

    public static void setTeamChosen(int teamChosen)
    {
        GameState.teamChosen = teamChosen;
    }
}
