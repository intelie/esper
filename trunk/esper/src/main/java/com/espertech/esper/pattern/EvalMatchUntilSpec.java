package com.espertech.esper.pattern;

public class EvalMatchUntilSpec
{
    private final Integer lowerBounds;
    private final Integer upperBounds;
    private final boolean hasBounds;
    private final boolean isTightlyBound;

    public EvalMatchUntilSpec(Integer lowerBounds, Integer upperBounds)
    {
        if ((lowerBounds != null) && (lowerBounds < 0))
        {
            throw new IllegalArgumentException("Lower bounds in match-until cannot be a negative value");
        }
        if ((upperBounds != null) && (upperBounds < 0))
        {
            throw new IllegalArgumentException("Upper bounds in match-until cannot be a negative value");            
        }

        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;

        if ((lowerBounds != null) || (upperBounds != null))
        {
            hasBounds = true;
        }
        else
        {
            hasBounds = false;
        }

        if ((lowerBounds != null) && (upperBounds != null))
        {
            if (lowerBounds < upperBounds)
            {
                int lower = upperBounds;
                upperBounds = lowerBounds;
                lowerBounds = lower;
            }

            if (lowerBounds == upperBounds)
            {
                isTightlyBound = true;
            }
            else
            {
                isTightlyBound = false;
            }
        }
        else
        {
            isTightlyBound = false;
        }
    }

    public boolean isBounded()
    {
        return hasBounds;
    }

    public boolean isTightlyBound()
    {
        return isTightlyBound;
    }

    public Integer getLowerBounds()
    {
        return lowerBounds;
    }

    public Integer getUpperBounds()
    {
        return upperBounds;
    }
}
