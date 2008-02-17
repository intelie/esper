package com.espertech.esper.view.stat.olap;

import com.espertech.esper.util.MetaDefItem;


/**
 * Hold the measure information which consists of a double value.
 */
public final class CellImpl implements Cell, MetaDefItem
{
    private final double value;

    /**
     * Constructor.
     * @param value is the measure value
     */
    public CellImpl(double value)
    {
        this.value = value;
    }

    public final double getValue()
    {
        return value;
    }
}
