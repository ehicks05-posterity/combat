package hicks.combat.entities;

import hicks.combat.GameLogic;
import hicks.combat.NameLogic;
import hicks.combat.Point;

import java.math.BigDecimal;

public class Unit
{
    private static int      m_seq = 0;
    private int             m_objectId;
    private int             m_team;
    private String          m_name;
    private int             m_sizeRadius;
    private int             m_sightRadius;
    private int             m_moveSpeed;
    private BigDecimal      m_timeOfLastMove;
    private boolean         m_moving;
    private Point           m_location;
    private Point           m_destination;
    private int             m_currentHp;
    private int             m_maxHp;
    private int             m_armor;
    private int             m_minDamage;
    private int             m_maxDamage;
    private int             m_attackRange;
    private BigDecimal      m_attackSpeed;
    private BigDecimal      m_timeOfLastAttack;
    private BigDecimal      m_createdOn;
    private Unit            m_target;
    private int             m_kills;

    public Unit()
    {
        setObjectId(m_seq++);
        setName(NameLogic.generateName(this));
        setCreatedOn(GameLogic.now());
    }

    public String toString()
    {
        return m_name + " (T" + m_team + ",ID:" + m_objectId + ")";
    }

    public boolean isAlive()
    {
        return m_currentHp > 0;
    }

    public int getObjectId()
    {
        return m_objectId;
    }

    public void setObjectId(int objectId)
    {
        m_objectId = objectId;
    }

    public boolean isReadyToAttack()
    {
        return GameLogic.getElapsedTime(m_timeOfLastAttack).compareTo(m_attackSpeed) > 0;
    }

    public int getSizeRadius()
    {
        return m_sizeRadius;
    }

    public void setSizeRadius(int sizeRadius)
    {
        m_sizeRadius = sizeRadius;
    }

    public int getTeam()
    {
        return m_team;
    }

    public void setTeam(int team)
    {
        m_team = team;
    }

    public String getName()
    {
        return m_name;
    }

    public void setName(String name)
    {
        m_name = name;
    }

    public int getSightRadius()
    {
        return m_sightRadius;
    }

    public void setSightRadius(int sightRadius)
    {
        m_sightRadius = sightRadius;
    }

    public int getMoveSpeed()
    {
        return m_moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed)
    {
        m_moveSpeed = moveSpeed;
    }

    public BigDecimal getTimeOfLastMove()
    {
        return m_timeOfLastMove;
    }

    public void setTimeOfLastMove(BigDecimal timeOfLastMove)
    {
        m_timeOfLastMove = timeOfLastMove;
    }

    public boolean isMoving()
    {
        return m_moving;
    }

    public void setMoving(boolean moving)
    {
        m_moving = moving;
    }

    public Point getLocation()
    {
        return m_location;
    }

    public void setLocation(Point location)
    {
        m_location = location;
    }

    public Point getDestination()
    {
        return m_destination;
    }

    public void setDestination(Point destination)
    {
        m_destination = destination;
    }

    public int getCurrentHp()
    {
        return m_currentHp;
    }

    public void setCurrentHp(int currentHp)
    {
        m_currentHp = currentHp;
    }

    public int getMaxHp()
    {
        return m_maxHp;
    }

    public void setMaxHp(int maxHp)
    {
        m_maxHp = maxHp;
    }

    public int getArmor()
    {
        return m_armor;
    }

    public void setArmor(int armor)
    {
        m_armor = armor;
    }

    public void setMinDamage(int minDamage)
    {
        m_minDamage = minDamage;
    }

    public void setMaxDamage(int maxDamage)
    {
        m_maxDamage = maxDamage;
    }

    public int getAttackRange()
    {
        return m_attackRange;
    }

    public void setAttackRange(int attackRange)
    {
        m_attackRange = attackRange;
    }

    public BigDecimal getAttackSpeed()
    {
        return m_attackSpeed;
    }

    public void setAttackSpeed(BigDecimal attackSpeed)
    {
        m_attackSpeed = attackSpeed;
    }

    public BigDecimal getTimeOfLastAttack()
    {
        return m_timeOfLastAttack;
    }

    public void setTimeOfLastAttack(BigDecimal timeOfLastAttack)
    {
        m_timeOfLastAttack = timeOfLastAttack;
    }

    public BigDecimal getCreatedOn()
    {
        return m_createdOn;
    }

    public void setCreatedOn(BigDecimal createdOn)
    {
        m_createdOn = createdOn;
    }

    public Unit getTarget()
    {
        return m_target;
    }

    public void setTarget(Unit target)
    {
        m_target = target;
    }

    public int getKills()
    {
        return m_kills;
    }

    public void setKills(int kills)
    {
        m_kills = kills;
    }

    public int getMinDamage()
    {
        return m_minDamage;
    }

    public int getMaxDamage()
    {
        return m_maxDamage;
    }
}
