package net.esper.eql.parse;

import java.util.Set;

/**
 * Interface to generate a set of integers from parameters that include ranges, lists and frequencies.
 */
public interface NumberSetParameter
{
    /**
     * Returns true if all values between and including min and max are supplied by the parameter.
     * @param min - lower end of range
     * @param max - upper end of range
     * @return true if parameter specifies all int values between min and max, false if not
     */
    public boolean isWildcard(int min, int max);

    /**
     * Return a set of int values representing the value of the parameter for the given range.
     * @param min - lower end of range
     * @param max - upper end of range
     * @return set of integer
     */
    public Set<Integer> getValuesInRange(int min, int max);
}
