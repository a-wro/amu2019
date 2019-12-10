package com.capgemini.tdd.core.data;

public class Point
{

    private Long x;
    private Long y;

    public Point(final Long pX, final Long pY)
    {
        x = pX;
        y = pY;
    }

    public Long getX()
    {
        return x;
    }

    public Long getY()
    {
        return y;
    }

    @Override
    public String toString()
    {
        return "Point{" +
               "x=" + x +
               ", y=" + y +
               '}';
    }

    @Override
    public boolean equals(final Object pO)
    {
        if (this == pO)
            return true;
        if (pO == null || getClass() != pO.getClass())
            return false;

        final Point point = (Point) pO;

        if (x != null ? !x.equals(point.x) : point.x != null)
            return false;
        return y != null ? y.equals(point.y) : point.y == null;
    }

    @Override
    public int hashCode()
    {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}
