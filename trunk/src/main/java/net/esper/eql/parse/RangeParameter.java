package net.esper.eql.parse;

import java.util.Set;
import java.util.HashSet;

/**
 * Represents a range of numbers as a parameter.
 */
public class RangeParameter implements NumberSetParameter
{
    private int low;
    private int high;

    /**
     * Ctor.
     * @param low - start of range
     * @param high - end of range
     */
    public RangeParameter(int low, int high)
    {
        this.low = low;
        this.high = high;
    }

    /**
     * Returns start of range.
     * @return start of range
     */
    public int getLow()
    {
        return low;
    }

    /**
     * Returns end of range.
     * @return end of range
     */
    public int getHigh()
    {
        return high;
    }

    public boolean isWildcard(int min, int max)
    {
        if ((min <= low) && (max >= high))
        {
            return true;
        }
        return false;
    }

    public Set<Integer> getValuesInRange(int min, int max)
    {
        Set<Integer> values = new HashSet<Integer>();
        
        int start = (min > low) ? min : low;
        int end = (max > high) ? high : max;

        while (start <= end)
        {
            values.add(start);
            start++;
        }

        return values;
    }
}