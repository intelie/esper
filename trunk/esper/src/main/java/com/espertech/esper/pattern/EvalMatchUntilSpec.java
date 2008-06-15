package com.espertech.esper.pattern;

/**
 * Specification for a range for the pattern-repeat operator.
 */
public class EvalMatchUntilSpec
{
    private final Integer lowerBounds;
    private final Integer upperBounds;
    private final boolean hasBounds;
    private final boolean isTightlyBound;

    /**
     * Ctor.
     * @param lowerBounds is the lower bounds, or null if none supplied
     * @param upperBounds is the upper bounds, or null if none supplied
     */
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
            if (lowerBounds > upperBounds)
            {
                throw new IllegalArgumentException("Lower bounds in match-until cannot be greater then the upper bounds");
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

    /**
     * Returns true if there is any endpoint, either low or high. Returns false for no endpoint.
     * @return true for has endpoint
     */
    public boolean isBounded()
    {
        return hasBounds;
    }

    /**
     * Returns true if there is a tight bounds, that is low and high endpoints are both defined.
     * @return true for tight endpoint.
     */
    public boolean isTightlyBound()
    {
        return isTightlyBound;
    }

    /**
     * Returns the lower endpoint or null if undefined.
     * @return lower endpoint
     */
    public Integer getLowerBounds()
    {
        return lowerBounds;
    }

    /**
     * Returns the high endpoint or null if undefined.
     * @return high endpoint
     */
    public Integer getUpperBounds()
    {
        return upperBounds;
    }
}
