package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;

import java.util.Collection;

/**
 * Accessor for first/last/window access aggregation functions.
 */
public interface AggregationAccessor
{
    /**
     * Returns the value for a first/last/window access aggregation function.
     * @param access access
     * @return value
     */
    public Object getValue(AggregationAccess access);

    public Collection<EventBean> getCollectionReadOnly(AggregationAccess access);
}
