/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.agg;

import net.esper.collection.RefCountedSet;
import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;

/**
 * AggregationMethod for use on top of another aggregator that handles unique value aggregation (versus all-value aggregation)
 * for the underlying aggregator.
 */
public class DistinctValueAggregator implements AggregationMethod
{
    private final AggregationMethod inner;
    private final Class childType;
    private final RefCountedSet<Object> valueSet;

    /**
     * Ctor.
     * @param inner is the aggregator function computing aggregation values 
     */
    public DistinctValueAggregator(AggregationMethod inner, Class childType)
    {
        this.inner = inner;
        this.childType = childType;
        this.valueSet = new RefCountedSet<Object>();
    }

    public void enter(Object value)
    {
        // if value not already encountered, enter into aggregate
        if (valueSet.add(value))
        {
            inner.enter(value);
        }
    }

    public void leave(Object value)
    {
        // if last reference to the value is removed, remove from aggregate
        if (valueSet.remove(value))
        {
            inner.leave(value);
        }
    }

    public Object getValue()
    {
        return inner.getValue();
    }

    public Class getValueType()
    {
        return inner.getValueType();
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        AggregationMethod innerCopy = inner.newAggregator(methodResolutionService);
        return methodResolutionService.makeDistinctAggregator(innerCopy, childType);
    }
}
