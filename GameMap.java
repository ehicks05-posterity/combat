package hicks.combat;

import hicks.combat.entities.Unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameMap
{
    private int m_width;
    private int m_height;

    public GameMap()
    {
    }

    public Point getRandomPoint()
    {
        return getRandomPoint(0, m_width, 0, m_height);
    }

    public Point getRandomPoint(double minX, double maxX, double minY, double maxY)
    {
        Random random = new Random();
        double x = minX + random.nextDouble() * maxX;
        double y = minY + random.nextDouble() * maxY;

        return new Point(x, y);
    }

    public Point getRandomAvailablePoint()
    {
        Point point = null;
        while (point == null)
        {
            Point possiblePoint = getRandomPoint();
            if (!isPositionOccupied(possiblePoint))
                point = possiblePoint;
        }

        return point;
    }

    public Point getAvailableAdjacentLocation(Point location)
    {
        double originX = location.getX();
        double originY = location.getY();

        List<Point> possibleLocations = new ArrayList<>();
        possibleLocations.add(new Point(originX - 1, originY - 1));
        possibleLocations.add(new Point(originX,     originY - 1));
        possibleLocations.add(new Point(originX + 1, originY - 1));

        possibleLocations.add(new Point(originX - 1, originY));
        possibleLocations.add(new Point(originX + 1, originY));

        possibleLocations.add(new Point(originX - 1, originY + 1));
        possibleLocations.add(new Point(originX,     originY + 1));
        possibleLocations.add(new Point(originX + 1, originY + 1));

        for (Iterator<Point> i = possibleLocations.iterator(); i.hasNext(); )
        {
            Point candidate = i.next();
            if (!isPositionOnMap(candidate) || isPositionOccupied(candidate)) i.remove();
        }

        if (possibleLocations.size() > 0)
            return possibleLocations.get(0);
        else
            return getRandomAvailablePoint();

    }

    public boolean isPositionOccupied(Point testPosition)
    {
        for (Unit unit : GameState.getUnits())
        {
            Point unitLocation = unit.getLocation();
            if (unitLocation != null && testPosition.equals(unitLocation))
                return true;
        }

        return false;
    }

    public boolean isPositionOnMap(Point location)
    {
        double x = location.getX();
        double y = location.getY();
        return x >= 0 && x < m_width && y >= 0 && y < m_height;
    }

    public int getWidth()
    {
        return m_width;
    }

    public void setWidth(int width)
    {
        m_width = width;
    }

    public int getHeight()
    {
        return m_height;
    }

    public void setHeight(int height)
    {
        m_height = height;
    }
}
