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
    private final LinkedList<Pair<FilterParamIndexBase, Object>> indizes;

    /**
     * Constructor.
     */
    public IndexTreePath()
    {
        indizes = new LinkedList<Pair<FilterParamIndexBase, Object>>();
    }

    /**
     * Add an index to end of the list representing a path through indexes.
     * @param index to add
     * @param filteredForValue is the value the index filters
     */
    public final void add(FilterParamIndexBase index, Object filteredForValue)
    {
        indizes.add(new Pair<FilterParamIndexBase, Object>(index, filteredForValue));
    }

    /**
     * Remove and return first index.
     * @return first index
     */
    public final Pair<FilterParamIndexBase, Object> removeFirst()
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

