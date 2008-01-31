package com.espertech.esper.eql.agg;

import com.espertech.esper.eql.agg.AggregationMethod;
import com.espertech.esper.eql.core.MethodResolutionService;

/**
 * Average always generates double-types numbers.
 */
public class AvgAggregator implements AggregationMethod
{
    private double sum;
    private long numDataPoints;

    public void clear()
    {
        sum = 0;
        numDataPoints = 0;
    }

    public void enter(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints++;
        sum += ((Number) object).doubleValue();
    }

    public void leave(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints--;
        sum -= ((Number) object).doubleValue();
    }

    public Object getValue()
    {
        if (numDataPoints == 0)
        {
            return null;
        }
        return sum / numDataPoints;
    }

    public Class getValueType()
    {
        return Double.class;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeAvgAggregator();
    }
}
