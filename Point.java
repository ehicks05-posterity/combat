package hicks.combat;

import java.text.DecimalFormat;

public class Point
{
    private double m_x;
    private double m_y;

    public Point(double x, double y)
    {
        m_x = x;
        m_y = y;
    }

    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#,###.##");
        return df.format(getX()) + ", " + df.format(getY());
    }

    public boolean equals(Object object)
    {
        if (object instanceof Point)
        {
            Point point = (Point) object;
            if (this.getX() == point.getX() && this.getY() == point.getY())
                return true;
        }

        return false;
    }

    public static double getDistance(Point a, Point b)
    {
        double xDifference = Math.abs(a.getX() - b.getX());
        double yDifference = Math.abs(a.getY() - b.getY());

        return Math.sqrt(Math.pow(xDifference, 2.0) + Math.pow(yDifference, 2.0));
    }

    public double getDistance(Point b)
    {
        return getDistance(this, b);
    }

    public double getDeltaX(Point b)
    {
        return m_x - b.getX();
    }

    public double getDeltaY(Point b)
    {
        return m_y - b.getY();
    }

    // ----------------------------

    public double getX()
    {
        return m_x;
    }

    public double getY()
    {
        return m_y;
    }
}
