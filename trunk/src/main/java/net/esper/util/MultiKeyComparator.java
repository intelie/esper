package net.esper.util;

import java.util.Comparator;

import net.esper.collection.MultiKeyUntyped;

/**
 * A comparator on multikeys. The multikeys must contain the same
 * number of values.
 */
public final class MultiKeyComparator implements Comparator<MultiKeyUntyped>
{
    private final Boolean[] isDescendingValues;

    /**
     * Ctor.
     * @param isDescendingValues - each value is true if the corresponding (same index)
     *        entry in the multi-keys is to be sorted in descending order. The multikeys
     *        to be compared must have the same number of values as this array.
     */
    public MultiKeyComparator(Boolean[] isDescendingValues)
    {
        this.isDescendingValues = isDescendingValues;
    }

    public final int compare(MultiKeyUntyped firstValues, MultiKeyUntyped secondValues)
    {
        if(firstValues.size() != isDescendingValues.length || secondValues.size() != isDescendingValues.length)
        {
            throw new IllegalArgumentException("Incompatible size MultiKey sizes for comparison");
        }

        for (int i = 0; i < firstValues.size(); i++)
        {
            Object valueOne = firstValues.get(i);
            Object valueTwo = secondValues.get(i);
            boolean isDescending = isDescendingValues[i];

            int comparisonResult = compareValues(valueOne, valueTwo, isDescending);
            if (comparisonResult != 0)
            {
                return comparisonResult;
            }
        }

        // Make the comparator compatible with equals
        if(!firstValues.equals(secondValues))
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

    private static int compareValues(Object valueOne, Object valueTwo, boolean isDescending)
    {
        if (isDescending)
        {
            Object temp = valueOne;
            valueOne = valueTwo;
            valueTwo = temp;
        }

        if (valueOne == null || valueTwo == null)
        {
            // A null value is considered equal to another null
            // value and smaller than any nonnull value
            if (valueOne == null && valueTwo == null)
            {
                return 0;
            }
            if (valueOne == null)
            {
                return -1;
            }
            return 1;
        }

        Comparable comparable1;
        if (valueOne instanceof Comparable)
        {
            comparable1 = (Comparable) valueOne;
        }
        else
        {
            throw new ClassCastException("Cannot sort objects of type " + valueOne.getClass());
        }

        return comparable1.compareTo(valueTwo);
    }
}
