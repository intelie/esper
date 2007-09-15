package net.esper.eql.agg;

import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;

/**
 * Counts all datapoints including null values.
 */
public class CountAggregator implements AggregationMethod
{
    private long numDataPoints;

    public void enter(Object object)
    {
        numDataPoints++;
    }

    public void leave(Object object)
    {
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
        return methodResolutionService.makeCountAggregator(false);
    }
}
