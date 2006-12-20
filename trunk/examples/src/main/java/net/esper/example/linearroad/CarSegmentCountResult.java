package net.esper.example.linearroad;

public class CarSegmentCountResult
{
    private int expressway;
    private int direction;
    private int segment;
    private double avgSpeed;
    private long size;

    public CarSegmentCountResult(int expressway, int direction, int segment, double avgSpeed, long size)
    {
        this.expressway = expressway;
        this.direction = direction;
        this.segment = segment;
        this.avgSpeed = avgSpeed;
        this.size = size;
    }

    public int getExpressway()
    {
        return expressway;
    }

    public void setExpressway(int expressway)
    {
        this.expressway = expressway;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public int getSegment()
    {
        return segment;
    }

    public void setSegment(int segment)
    {
        this.segment = segment;
    }

    public double getAvgSpeed()
    {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed)
    {
        this.avgSpeed = avgSpeed;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String toString()
    {
        return "expressway=" + expressway +
                " direction=" + direction +
                " segment=" + segment +
                " avgSpeed=" + avgSpeed +
                " size=" + size;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof CarSegmentCountResult))
        {
            return false;
        }
        CarSegmentCountResult otherResult = (CarSegmentCountResult) other;
        if ((otherResult.expressway != this.expressway) ||
            (otherResult.direction != this.direction) ||
            (otherResult.segment != this.segment) ||
            (otherResult.size != this.size))
        {
            return false;
        }
        if (Double.isNaN(otherResult.avgSpeed) && Double.isNaN(this.avgSpeed))
        {
            return true;
        }
        if (otherResult.avgSpeed != this.avgSpeed)
        {
            return false;
        }
        
        return true;
    }
}
