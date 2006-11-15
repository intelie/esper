package net.esper.eql.parse;

import java.util.Set;
import java.util.HashSet;

/**
 * Represents a wildcard as a parameter.
 */
public class WildcardParameter implements NumberSetParameter
{
    public boolean isWildcard(int min, int max)
    {
        return true;
    }

    public Set<Integer> getValuesInRange(int min, int max)
    {
        Set<Integer> result = new HashSet<Integer>();
        for (int i = min; i <= max; i++)
        {
            result.add(i);
        }
        return result;
    }
}
