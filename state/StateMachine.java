package hicks.combat.state;

import hicks.combat.entities.Unit;

public class StateMachine
{
    private Unit owner;
    private State currentState;
    private State previousState;
    private State globalState;

    public StateMachine(Unit unit)
    {
        owner = unit;
    }

    public void update()
    {
        if (globalState != null) globalState.execute(owner);
        if (currentState != null) currentState.execute(owner);
    }

    public void changeState(State newState)
    {
        previousState = currentState;
        currentState.exit(owner);
        currentState = newState;
        currentState.enter(owner);
    }

    public void revertToPreviousState()
    {
        changeState(previousState);
    }

    public boolean isInState(State input)
    {
        return false;
    }

    public State getCurrentState()
    {
        return currentState;
    }

    public void setCurrentState(State currentState)
    {
        this.currentState = currentState;
    }

    public State getPreviousState()
    {
        return previousState;
    }

    public void setPreviousState(State previousState)
    {
        this.previousState = previousState;
    }

    public State getGlobalState()
    {
        return globalState;
    }

    public void setGlobalState(State globalState)
    {
        this.globalState = globalState;
    }
}
