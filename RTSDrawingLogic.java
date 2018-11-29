package hicks.combat;

import hicks.combat.entities.*;

import javax.swing.*;
import java.awt.*;

public class RTSDrawingLogic
{
    private static final Image PEASANT = new ImageIcon("ass\\peasant.gif").getImage();

    public static void drawUnits(Graphics2D g2d)
    {
        for (Unit unit : GameCanvas.units)
        {
            int x = (int) unit.getLocation().getX();
            int y = (int) unit.getLocation().getY();

            drawVisionCircle(g2d, unit);
            drawAttackRangeCircle(g2d, unit);

            if (unit.getTeam() == 0)
            {
                g2d.setColor(Color.RED);
                if (unit instanceof Barracks) g2d.setColor(Color.PINK);
                if (unit instanceof Berserker) g2d.setColor(new Color(150, 0, 0));
            }
            if (unit.getTeam() == 1)
            {
                g2d.setColor(Color.GREEN);
                if (unit instanceof Barracks) g2d.setColor(Color.YELLOW);
                if (unit instanceof Berserker) g2d.setColor(new Color(0, 120, 0));
            }

            int size = 3;
            if (unit instanceof Knight || unit instanceof Berserker) size = 5;
            if (unit instanceof Barracks) size = 10;
            if (unit instanceof Peasant) size = 12;

            if (unit instanceof Peasant)
                g2d.drawImage(PEASANT, (x - size/2) - GameCanvas.viewPortX, (y - size/2) - GameCanvas.viewPortY, size, size, null);
            else
                g2d.fillRect(x - GameCanvas.viewPortX, y - GameCanvas.viewPortY, size, size);

            if (GameCanvas.selectedUnits.contains(unit)) drawHealthBar(g2d, unit, x, y);
        }
    }

    private static void drawHealthBar(Graphics2D g2d, Unit unit, int x, int y)
    {
        double currentHpPercent = (unit.getCurrentHp() * 100) / unit.getMaxHp();
        int hpBoxes = (int) (currentHpPercent / 10);

        if (unit.getTeam() == 0)
            g2d.setColor(Color.RED);
        if (unit.getTeam() == 1)
            g2d.setColor(Color.GREEN);
        for (int i = 0; i < hpBoxes; i++)
            g2d.drawRect((x - 8 + (i * 2)) - GameCanvas.viewPortX, (y - 4) - GameCanvas.viewPortY, 2, 2);
    }

    private static void drawVisionCircle(Graphics2D g2d, Unit unit)
    {
        int x = (int) unit.getLocation().getX();
        int y = (int) unit.getLocation().getY();

        g2d.setColor(Color.getHSBColor(.12f, .12f, .12f));

        int size = unit.getSightRadius();

        g2d.drawOval((x - size) - GameCanvas.viewPortX, (y - size) - GameCanvas.viewPortY, size * 2, size * 2);
    }

    private static void drawAttackRangeCircle(Graphics2D g2d, Unit unit)
    {
        int x = (int) unit.getLocation().getX();
        int y = (int) unit.getLocation().getY();

        g2d.setColor(Color.getHSBColor(.52f, .52f, .52f));

        int size = unit.getAttackRange();

        g2d.drawOval((x - size) - GameCanvas.viewPortX, (y - size) - GameCanvas.viewPortY, size * 2, size * 2);
    }
}
