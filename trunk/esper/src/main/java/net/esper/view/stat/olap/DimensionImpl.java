package net.esper.view.stat.olap;

import net.esper.util.MetaDefItem;


/**
 * Implements the dimension interface. Hold the data required for serving up dimension data.
 */
public final class DimensionImpl implements Dimension, MetaDefItem
{
    private final String[] propertyNames;
    private DimensionMember[] members;

    /**
     * Constructor.
     * @param propertyNames is the names of the event properties making up the dimension
     */
    public DimensionImpl(String[] propertyNames)
    {
        this.propertyNames = propertyNames;
    }

    /**
     * Set the members of the dimension.
     * @param members is an array of members of dimension
     */
    final void setMembers(DimensionMember[] members)
    {
        this.members = members;
    }

    public final String[] getPropertyNames()
    {
        return propertyNames;
    }

    public final DimensionMember[] getMembers()
    {
        return members;
    }
}
