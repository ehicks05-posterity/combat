package hicks.combat.entities;

import hicks.combat.GameLogic;

import java.math.BigDecimal;

public class Berserker extends Unit
{
    public Berserker(int team)
    {
        setTeam(team);
        setSizeRadius(40);
        setSightRadius(40);
        setMoveSpeed(50);
        setTimeOfLastMove(GameLogic.now());

        setCurrentHp(60);
        setMaxHp(60);
        setArmor(1);
        setMinDamage(12);
        setMaxDamage(18);
        setAttackRange(10);

        setAttackSpeed(new BigDecimal(1.5));
        setTimeOfLastAttack(GameLogic.now());
    }
}
