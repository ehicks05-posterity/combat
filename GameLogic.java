package hicks.combat;

import hicks.combat.entities.Barracks;
import hicks.combat.entities.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GameLogic
{
    public static BigDecimal now()
    {
        return new BigDecimal(System.nanoTime());
    }

    public static BigDecimal getElapsedTime(BigDecimal startTime)
    {
        return GameLogic.now().subtract(startTime).divide(new BigDecimal("1000000000"));
    }

    public static List<Integer> teamsLeft(List<Unit> units)
    {
        List<Integer> teamsAlive = new ArrayList<>();
        for (Unit unit : units)
            if (unit.isAlive() && !teamsAlive.contains(unit.getTeam()))
                teamsAlive.add(unit.getTeam());

        return teamsAlive;
    }

    public static int getUnitsOnTeam(List<Unit> units, int team)
    {
        int result = 0;
        for (Unit unit : units)
            if (unit.getTeam() == team) result++;

        return result;
    }

    public static boolean isClearOfBarracks(Unit unit)
    {
        boolean isClearOfBarracks = true;
        List<Unit> barracks = new ArrayList<>(getListOfBarracks());
        for (Unit barrack : barracks)
        {
            if (unit.getLocation().getDistance(barrack.getLocation()) < 100) isClearOfBarracks = false;
        }
        return isClearOfBarracks;
    }

    private static List<Unit> getListOfBarracks()
    {
        List<Unit> units = GameState.getUnits();
        List<Unit> barracks = new ArrayList<>();
        for (Unit unit : units)
        {
            if (unit instanceof Barracks) barracks.add(unit);
        }

        return barracks;
    }

    public static void placeUnitsRandomlyOnEachHalfOfMap(GameMap map, List<Unit> unitsToPlace)
    {
        for (Unit unit : unitsToPlace)
        {
            boolean unitPlaced = false;
            while (!unitPlaced)
            {
                Point point = null;
                if (unit.getTeam() == 0) point = map.getRandomPoint(0, map.getWidth() / 2, 0, map.getHeight());
                if (unit.getTeam() == 1) point = map.getRandomPoint(map.getWidth() / 2, map.getWidth() / 2, 0, map.getHeight());
                if (!map.isPositionOccupied(point))
                {
                    unit.setLocation(point);
                    GameState.addUnit(unit);
                    unitPlaced = true;
                }
            }
        }
    }
}
