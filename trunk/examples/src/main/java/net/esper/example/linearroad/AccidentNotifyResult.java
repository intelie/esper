package net.esper.example.linearroad;

public class AccidentNotifyResult
{
    private int expressway;
    private int direction;
    private int segment;

    public AccidentNotifyResult(int expressway, int direction, int segment)
    {
        this.expressway = expressway;
        this.direction = direction;
        this.segment = segment;
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

    public String toString()
    {
        return "expressway=" + expressway +
                " direction=" + direction +
                " segment=" + segment;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof AccidentNotifyResult))
        {
            return false;
        }

        AccidentNotifyResult otherResult = (AccidentNotifyResult) other;

        if ((otherResult.expressway != this.expressway) ||
            (otherResult.direction != this.direction) ||
            (otherResult.segment != this.segment))
        {
            return false;
        }

        return true;
    }
}
