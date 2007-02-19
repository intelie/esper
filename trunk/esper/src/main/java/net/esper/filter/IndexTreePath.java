package net.esper.filter;

import net.esper.collection.Pair;

import java.util.LinkedList;
import java.util.Arrays;

/**
 * Encapsulates the information required by {@link IndexTreeBuilder} to maintain the filter parameter tree structure
 * when filters are added and removed from the tree.
 */
public class IndexTreePath
{
    private final LinkedList<Pair<FilterParamIndex, Object>> indizes;

    /**
     * Constructor.
     */
    public IndexTreePath()
    {
        indizes = new LinkedList<Pair<FilterParamIndex, Object>>();
    }

    /**
     * Add an index to end of the list representing a path through indexes.
     * @param index to add
     * @param filteredForValue is the value the index filters
     */
    public final void add(FilterParamIndex index, Object filteredForValue)
    {
        indizes.add(new Pair<FilterParamIndex, Object>(index, filteredForValue));
    }

    /**
     * Remove and return first index.
     * @return first index
     */
    public final Pair<FilterParamIndex, Object> removeFirst()
    {
        if (!indizes.isEmpty())
        {
            return indizes.removeFirst();
        }
        else
        {
            return null;
        }
    }

    public final String toString()
    {
        return Arrays.toString(indizes.toArray());
    }
}

