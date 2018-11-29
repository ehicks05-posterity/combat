package hicks.combat.state;

import hicks.combat.CombatLogic;
import hicks.combat.UnitLogic;
import hicks.combat.entities.Unit;

public class Hostile implements State
{
    private static Hostile instance = null;

    protected Hostile()
    {

    }

    public static Hostile getInstance()
    {
        if (instance == null)
        {
            instance = new Hostile();
        }
        return instance;
    }

    public String toString()
    {
        return this.getClass().getSimpleName();
    }

    public void enter(Unit unit)
    {
    }

    public void execute(Unit unit)
    {
        if (unit.getTarget() == null)
        {
//            unit.changeState(new Idle());
            return;
        }

        if (!unit.getTarget().isAlive())
        {
            unit.setTarget(null);
//            unit.changeState(new Idle());
            return;
        }

        if (UnitLogic.isTargetInRange(unit) && unit.isReadyToAttack())
            CombatLogic.performAttack(unit);
        else
            UnitLogic.moveTowardCoordinate(unit, unit.getTarget().getLocation(), true);
    }

    public void exit(Unit unit)
    {

    }
}
