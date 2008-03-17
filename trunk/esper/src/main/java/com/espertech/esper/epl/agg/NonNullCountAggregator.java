package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.core.MethodResolutionService;

/**
 * Count all non-null values.
 */
public class NonNullCountAggregator implements AggregationMethod
{
    private long numDataPoints;

    public void clear()
    {
        numDataPoints = 0;
    }

    public void enter(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints++;
    }

    public void leave(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints--;
    }

    public Object getValue()
    {
        return numDataPoints;
    }

    public Class getValueType()
    {
        return Long.class;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeCountAggregator(true);
    }
}
