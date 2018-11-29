package hicks.combat.state;

import hicks.combat.GameLogic;
import hicks.combat.GameState;
import hicks.combat.Log;
import hicks.combat.entities.*;

import java.util.Random;

public class Build implements State
{
    private static Build instance = null;

    protected Build()
    {

    }

    public static Build getInstance()
    {
        if (instance == null)
        {
            instance = new Build();
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
        if (unit instanceof Barracks)
        {
            Barracks barracks = (Barracks) unit;
            if (barracks.isReadyToBuild())
            {
                Random gen = new Random();
                int typeToBuild = gen.nextInt(3);
                Unit newUnit = null;

                if (typeToBuild == 0) newUnit = new Knight(barracks.getTeam());
                if (typeToBuild == 1) newUnit = new Peasant(barracks.getTeam());
                if (typeToBuild == 2) newUnit = new Footman(barracks.getTeam());

                if (newUnit == null) newUnit = new Footman(barracks.getTeam());

                newUnit.setLocation(GameState.getGameMap().getAvailableAdjacentLocation(barracks.getLocation()));
//                newUnit.changeState(new Idle());
                GameState.addUnit(newUnit);
                barracks.setTimeOfLastBuild(GameLogic.now());

                Log.logInfo(barracks + " has built a " + newUnit.getClass().getSimpleName() + " " + newUnit);
            }
        }

        if (unit instanceof Peasant)
        {
            Peasant peasant = (Peasant) unit;

            Unit newUnit = new Barracks(peasant.getTeam());
            newUnit.setLocation(GameState.getGameMap().getAvailableAdjacentLocation(peasant.getLocation()));

            GameState.addUnit(newUnit);
            GameState.removeUnit(peasant);

            Log.logInfo(peasant + " has built a " + newUnit);
        }
    }

    public void exit(Unit unit)
    {

    }
}
