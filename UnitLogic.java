package hicks.combat;

import hicks.combat.entities.Unit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class UnitLogic
{
    public static void moveTowardCoordinate(Unit unit, Point destination, boolean isDestinationAnEnemyTarget)
    {
        if (!unit.isMoving())
        {
            unit.setTimeOfLastMove(GameLogic.now());
            unit.setMoving(true);
        }

        BigDecimal timeSinceLastMove        = GameLogic.getElapsedTime(unit.getTimeOfLastMove());
        BigDecimal moveSpeed                = new BigDecimal(unit.getMoveSpeed());
        moveSpeed = moveSpeed.multiply(new BigDecimal(".4"));
        BigDecimal potentialDistanceToMove  = moveSpeed.multiply(timeSinceLastMove);
        BigDecimal currentDistance          = new BigDecimal(unit.getLocation().getDistance(destination)).setScale(0, RoundingMode.HALF_UP);

        BigDecimal desiredDistance = BigDecimal.ZERO;
        if (isDestinationAnEnemyTarget)
            desiredDistance = new BigDecimal(unit.getAttackRange());

        // if we are within our desired distance, stop.
        if (currentDistance.compareTo(desiredDistance) <= 0)
        {
            unit.setDestination(null);
            return;
        }

        // move as far as necessary, and no further.
        BigDecimal expectedNewDistance = currentDistance.subtract(potentialDistanceToMove);
        BigDecimal actualDistanceToMove = potentialDistanceToMove;
        if (expectedNewDistance.compareTo(desiredDistance) == -1)
        {
            BigDecimal excessMovement = desiredDistance.subtract(expectedNewDistance);
            actualDistanceToMove = potentialDistanceToMove.subtract(excessMovement);
        }

        // calculate the weighting for x and y terms
        BigDecimal deltaX = new BigDecimal(destination.getDeltaX(unit.getLocation()));
        BigDecimal deltaY = new BigDecimal(destination.getDeltaY(unit.getLocation()));

        BigDecimal factorX = deltaX.divide(currentDistance, 16, RoundingMode.HALF_UP);
        BigDecimal factorY = deltaY.divide(currentDistance, 16, RoundingMode.HALF_UP);

        BigDecimal distanceToMoveX = actualDistanceToMove.multiply(factorX);
        BigDecimal distanceToMoveY = actualDistanceToMove.multiply(factorY);

        double newX = new BigDecimal(unit.getLocation().getX()).add((distanceToMoveX)).doubleValue();
        double newY = new BigDecimal(unit.getLocation().getY()).add((distanceToMoveY)).doubleValue();

        unit.setLocation(new Point(newX, newY));
        unit.setTimeOfLastMove(GameLogic.now());
    }

    public static boolean isTargetInRange(Unit unit)
    {
        Point location = unit.getLocation();
        Point targetLocation = unit.getTarget().getLocation();
        double distance = new BigDecimal(location.getDistance(targetLocation)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return distance <= unit.getAttackRange();
    }

    public static Unit getClosestVisibleEnemy(Unit callingUnit, List<Unit> units)
    {
        Unit closestEnemy = null;
        double smallestDistance = Double.MAX_VALUE;

        for (Unit unit : units)
        {
            if (unit == callingUnit || unit.getTeam() == callingUnit.getTeam())
                continue;

            double distance = callingUnit.getLocation().getDistance(unit.getLocation());
            if (distance <= callingUnit.getSightRadius() && distance < smallestDistance)
            {
                closestEnemy = unit;
                smallestDistance = distance;
            }
        }

        return closestEnemy;
    }

    public static int getAttackDamage(Unit unit)
    {
        Random random       = new Random();
        int damageRange     = (unit.getMaxDamage() - unit.getMinDamage()) + 1;
        int randomPortion   = random.nextInt(damageRange);
        return randomPortion + unit.getMinDamage();
    }
}
