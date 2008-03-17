package com.espertech.esper.view.stat.olap;

import com.espertech.esper.view.stat.olap.Dimension;

/**
 * DimensionMember models instances of dimensional members. A member is a single or multiple object derived from the
 * event property or properties of the dimension.
 */
public interface DimensionMember
{
    /**
     * Returns the dimension the member belongs to.
     * @return dimension that this member is a value of
     */
    public Dimension getDimension();

    /**
     * Returns member values.
     * @return array of member values
     */
    public Object[] getValues();
}
