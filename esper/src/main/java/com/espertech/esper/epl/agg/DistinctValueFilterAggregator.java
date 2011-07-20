/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.collection.RefCountedSet;
import com.espertech.esper.epl.core.MethodResolutionService;
import sun.jdbc.odbc.ee.ObjectPool;

import java.lang.reflect.Array;

/**
 * AggregationMethod for use on top of another aggregator that handles unique value aggregation (versus all-value aggregation)
 * for the underlying aggregator.
 */
public class DistinctValueFilterAggregator implements AggregationMethod
{
    private final AggregationMethod inner;
    private final Class childType;
    private final RefCountedSet<Object> valueSet;

    /**
     * Ctor.
     * @param inner is the aggregator function computing aggregation values
     * @param childType is the return type of the inner expression to aggregate, if any
     */
    public DistinctValueFilterAggregator(AggregationMethod inner, Class childType)
    {
        this.inner = inner;
        this.childType = childType;
        this.valueSet = new RefCountedSet<Object>();
    }

    public void clear()
    {
        valueSet.clear();
    }

    public void enter(Object value)
    {
        Object[] values = (Object[]) value;
        if (!checkPass(values)) {
            return;
        }

        // if value not already encountered, enter into aggregate
        if (valueSet.add(values[0]))
        {
            inner.enter(value);
        }
    }

    public void leave(Object value)
    {
        Object[] values = (Object[]) value;
        if (!checkPass(values)) {
            return;
        }

        // if last reference to the value is removed, remove from aggregate
        if (valueSet.remove(values[0]))
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
        return methodResolutionService.makeDistinctAggregator(innerCopy, childType, true);
    }

    private boolean checkPass(Object[] object) {
        Boolean first = (Boolean) object[1];
        if (first != null) {
            return first;
        }
        return false;
    }
}
