package net.esper.eql.parse;

import java.util.Set;
import java.util.HashSet;

/**
 * Parameter supplying a single int value is a set of numbers.
 */
public class IntParameter implements NumberSetParameter
{
    private int intValue;

    /**
     * Ctor.
     * @param intValue - single in value
     */
    public IntParameter(int intValue)
    {
        this.intValue = intValue;
    }

    /**
     * Returns int value.
     * @return int value
     */
    public int getIntValue()
    {
        return intValue;
    }

    public boolean isWildcard(int min, int max)
    {
        if ((intValue == min) && (intValue == max))
        {
            return true;
        }
        return false;
    }

    public Set<Integer> getValuesInRange(int min, int max)
    {
        Set<Integer> values = new HashSet<Integer>();

        if ((intValue >= min) && (intValue <= max))
        {
            values.add(intValue);
        }

        return values;
    }
}
