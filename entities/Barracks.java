package hicks.combat.entities;

import hicks.combat.GameLogic;

import java.math.BigDecimal;

public class Barracks extends Unit implements Builder
{
    private BigDecimal m_buildSpeed;
    private BigDecimal m_timeOfLastBuild;

    public Barracks(int team)
    {
        setTeam(team);
        setSizeRadius(150);
        setSightRadius(60);

        setCurrentHp(100);
        setMaxHp(100);
        setArmor(10);

        setBuildSpeed(new BigDecimal(2.5));
        setTimeOfLastBuild(GameLogic.now());
//        changeState(Build.getInstance());
    }

    public boolean isReadyToBuild()
    {
        return GameLogic.getElapsedTime(getTimeOfLastBuild()).compareTo(getBuildSpeed()) > 0;
    }

    public BigDecimal getBuildSpeed()
    {
        return m_buildSpeed;
    }

    public void setBuildSpeed(BigDecimal m_buildSpeed)
    {
        this.m_buildSpeed = m_buildSpeed;
    }

    public BigDecimal getTimeOfLastBuild()
    {
        return m_timeOfLastBuild;
    }

    public void setTimeOfLastBuild(BigDecimal m_timeOfLastBuild)
    {
        this.m_timeOfLastBuild = m_timeOfLastBuild;
    }
}
