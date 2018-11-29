package hicks.combat;

import hicks.combat.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BehaviorLogic
{
    static Point p1BaseCenter;
    static Point p2BaseCenter;

    public static void updateState()
    {
        // copy list to try to avoid concurrentModificationExceptions
        List<Unit> unitsToProcess = new ArrayList<>(GameState.getUnits());

        p1BaseCenter = getBaseCenter(0);
        p2BaseCenter = getBaseCenter(1);

        for (Unit unit : unitsToProcess)
        {
            if (unit instanceof Archer || unit instanceof Berserker || unit instanceof Footman || unit instanceof Knight)
            {
                BigDecimal timeSinceCreation = GameLogic.getElapsedTime(unit.getCreatedOn());
                if (timeSinceCreation.compareTo(new BigDecimal("30")) > 0)
                {
                    CombatLogic.processDeath(unit);
                    continue;
                }
            }
            if (unit instanceof Peasant)
            {
                BigDecimal timeSinceCreation = GameLogic.getElapsedTime(unit.getCreatedOn());
                if (timeSinceCreation.compareTo(new BigDecimal("10")) > 0)
                {
                    CombatLogic.processDeath(unit);
                    continue;
                }
            }

            if (!unit.isAlive()) continue;

            chooseBehavior(unit);
//            unit.update();
        }
    }

    private static Point getBaseCenter(int team)
    {
        int xSum = 0;
        int ySum = 0;
        int points = 0;
        for (Unit unit : new ArrayList<>(GameState.getUnits()))
            if (unit instanceof Barracks && unit.getTeam() == team)
            {
                xSum += unit.getLocation().getX();
                ySum += unit.getLocation().getY();
                points++;
            }
        if (points == 0) return null;
        return new Point(xSum / points, ySum / points);
    }

    private static void chooseBehavior(Unit unit)
    {
        if (!(unit instanceof Builder))
        {
            if (unit.getTarget() == null)
                performIdleBehavior(unit);
            if (unit.getTarget() != null)
                performHostileBehavior(unit);
        }
        if (unit instanceof Builder)
            performBuildBehavior(unit);
    }

    private static void performBuildBehavior(Unit unit)
    {
        if (unit instanceof Barracks)
        {
            Barracks barracks = (Barracks) unit;
            if (barracks.isReadyToBuild())
            {
                // create and add to map, a new footman... it should be in an available spot next to the barracks
                Random gen = new Random();
                int typeToBuild = gen.nextInt(5);
                Unit barracksCreation;

                switch (typeToBuild)
                {
                    case 0: barracksCreation = new Knight(barracks.getTeam());
                        break;
                    case 1: barracksCreation = new Peasant(barracks.getTeam());
                        break;
                    default: barracksCreation = new Footman(barracks.getTeam());
                }

                barracksCreation.setLocation(GameState.getGameMap().getAvailableAdjacentLocation(barracks.getLocation()));
                GameState.addUnit(barracksCreation);
                barracks.setTimeOfLastBuild(GameLogic.now());

                if (Init.heavyLogging) Log.logInfo(barracks + " has created a footman " + barracksCreation);

                int unitsOnTeam1 = GameLogic.getUnitsOnTeam(GameState.getUnits(), 0);
                int unitsOnTeam2 = GameLogic.getUnitsOnTeam(GameState.getUnits(), 1);
                int winningTeam = unitsOnTeam1 > unitsOnTeam2 ? 0 : 1;

                if (barracks.getTeam() == winningTeam)
                    barracks.setBuildSpeed(new BigDecimal(2.5));
                else
                    barracks.setBuildSpeed(new BigDecimal(2.5));
            }
        }

        if (unit instanceof Peasant)
        {
            Peasant peasant = (Peasant) unit;
            if (GameLogic.isClearOfBarracks(unit))
            {
                Unit unitCreated = new Barracks(peasant.getTeam());

                unitCreated.setLocation(GameState.getGameMap().getAvailableAdjacentLocation(peasant.getLocation()));
                GameState.addUnit(unitCreated);
                peasant.setTimeOfLastBuild(GameLogic.now());
                GameState.removeUnit(peasant);

                if (Init.heavyLogging) Log.logInfo(peasant + " has created a Barracks " + unitCreated);
            }
            else
                performIdleBehavior(unit);
        }
    }

    // hostile behavior is approach target, then hit target
    private static void performHostileBehavior(Unit unit)
    {
        if (UnitLogic.isTargetInRange(unit) && unit.isReadyToAttack())
            CombatLogic.performAttack(unit);
        else
            UnitLogic.moveTowardCoordinate(unit, unit.getTarget().getLocation(), true);
    }

    // idle behavior is random wandering until we find an enemy
    private static void performIdleBehavior(Unit unit)
    {
        Unit closestVisibleEnemy = UnitLogic.getClosestVisibleEnemy(unit, GameState.getUnits());

        if (closestVisibleEnemy == null)
        {
            if (unit.getDestination() == null)
            {
                Point newDestination = unit.getTeam() == 0 ? p2BaseCenter : p1BaseCenter;
                if (newDestination != null && newDestination.equals(unit.getLocation()))
                {
                    List<Unit> units = GameState.getUnits();
                    for (Unit innerUnit : units)
                        if (innerUnit.getTeam() != unit.getTeam() && unit instanceof Barracks)
                        {
                            newDestination = innerUnit.getLocation();
                            break;
                        }
                }

                if (newDestination == null || unit instanceof Builder) newDestination = GameState.getGameMap().getRandomAvailablePoint();


                unit.setDestination(newDestination);
            }

            UnitLogic.moveTowardCoordinate(unit, unit.getDestination(), false);
        }
        else
        {
            unit.setDestination(null);
            unit.setTarget(closestVisibleEnemy);
            if (unit.getTarget() != null) Log.logInfo(unit + " has targeted " + unit.getTarget() + "!");
        }
    }
}
