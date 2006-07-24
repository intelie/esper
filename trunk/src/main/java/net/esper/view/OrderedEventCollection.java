package net.esper.view;

import net.esper.view.EventCollection;

import java.util.Iterator;

/**
 * Interface that marks a an ordered event collection.
 */
public interface OrderedEventCollection extends EventCollection
{
    /**
     * Gets the data at an index.
     * @param index to get data at
     * @return data at the index
     */
    public Object get(long index);

    /**
     * Returns the lower bound of valid index values.
     * @return lower bound
     */
    public long getFirstIndex();

    /**
     * Returns the upper bound of valid index values.
     * @return upper bound
     */
    public long getLastIndex();

    /**
     * Returns the size of the collection.
     * @return number of elements
     */
    public int getSize();

    /**
     * Creates an iterator for a range of ordered data.
     * @param startIndex is the lower bound
     * @param endIndex is the upper bound
     * @return Iterator for range
     */
    public Iterator iterator(long startIndex, long endIndex);

}
