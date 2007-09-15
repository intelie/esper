package net.esper.view.stat.olap;

import net.esper.util.MetaDefItem;


/**
 * Serves up Cube dimension member information - the members dimension and its key values.
 */
public final class DimensionMemberImpl implements DimensionMember, MetaDefItem
{
    private Dimension dimension;
    private final Object[] values;

    /**
     * Constructor.
     * @param values is a set of values identifying the member
     */
    public DimensionMemberImpl(Object[] values)
    {
        this.values = values;
    }

    /**
     * Set the dimension this member belongs to.
     * @param dimension the member belongs to
     */
    public final void setDimension(Dimension dimension)
    {
        this.dimension = dimension;
    }

    public final Dimension getDimension()
    {
        return dimension;
    }

    public final Object[] getValues()
    {
        return values;
    }
}
