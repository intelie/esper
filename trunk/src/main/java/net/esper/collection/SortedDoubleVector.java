package net.esper.collection;

import java.util.Vector;

/**
 * Sorted, reference-counting set based on a TreeMap implementation that stores keys and a reference counter for
 * each unique key value. Each time the same key is added, the reference counter increases.
 * Each time a key is removed, the reference counter decreases.
 */
public class SortedDoubleVector
{
    private Vector<Double> values;

    /**
     * Constructor.
     */
    public SortedDoubleVector()
    {
        values = new Vector<Double>();
    }

    /**
     * Returns the number of items in the collection.
     * @return size
     */
    public int size()
    {
        return values.size();
    }

    /**
     * Returns the value at a given index.
     * @param index for which to return value for
     * @return value at index
     */
    public double getValue(int index)
    {
        return values.get(index);
    }

    /**
     * Add a value to the collection.
     * @param value is the double-type value to add
     */
    public void add(double value)
    {
        int index = findInsertIndex(value);

        if (index == -1)
        {
            values.add(value);
        }
        else
        {
            values.add(index, value);
        }
    }

    /**
     * Remove a value from the collection.
     * @param value to remove
     * @throws IllegalStateException if the value has not been added
     */
    public void remove(double value)
    {
        int index = findInsertIndex(value);
        if ((index == -1) || (values.get(index) != value))
        {
            throw new IllegalStateException("Value not found in collection");
        }
        values.removeElementAt(index);
        return;
    }

    /**
     * Returns underlying vector, for testing purposes only.
     * @return vector with double values
     */
    protected Vector<Double> getValues()
    {
        return values;
    }

    /**
     * Returns the index into which to insert to.
     * Proptected access level for convenient testing.
     * @param value to find insert index
     * @return position to insert the value to, or -1 to indicate to add to the end.
     */
    protected int findInsertIndex(double value)
    {
        if (values.size() > 2)
        {
            int startIndex = values.size() / 2;
            double startValue = values.get(startIndex);
            int insertAt = -1;

            if (value < startValue)
            {
                // find in lower half
                insertAt = findInsertIndex(0, startIndex - 1, value);
            }
            else if (value > startValue)
            {
                // find in upper half
                insertAt = findInsertIndex(startIndex + 1, values.size() - 1, value);
            }
            else
            {
                // we hit the value
                insertAt = startIndex;
            }

            if (insertAt == values.size())
            {
                return -1;
            }
            return insertAt;
        }

        if (values.size() == 2)
        {
            if (value > values.get(1))
            {
                return -1;
            }
            else if (value <= values.get(0))
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }

        if (values.size() == 1)
        {
            if (value > values.get(0))
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }

        return -1;
    }

    private int findInsertIndex(int lowerBound, int upperBound, double value)
    {
        if (upperBound == lowerBound)
        {
            double valueLowerBound = values.get(lowerBound);
            if (value <= valueLowerBound)
            {
                return lowerBound;
            }
            else
            {
                return lowerBound + 1;
            }
        }

        if (upperBound - lowerBound == 1)
        {
            double valueLowerBound = values.get(lowerBound);
            if (value <= valueLowerBound)
            {
                return lowerBound;
            }

            double valueUpperBound = values.get(upperBound);
            if (value > valueUpperBound)
            {
                return upperBound + 1;
            }

            return upperBound;
        }

        int nextMiddle = lowerBound + (upperBound - lowerBound) / 2;
        double valueAtMiddle = values.get(nextMiddle);

        if (value < valueAtMiddle)
        {
            // find in lower half
            return findInsertIndex(lowerBound, nextMiddle - 1, value);
        }
        else if (value > valueAtMiddle)
        {
            // find in upper half
            return findInsertIndex(nextMiddle, upperBound, value);
        }
        else
        {
            return nextMiddle;
        }
    }
}
