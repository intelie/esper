package net.esper.eql.agg;

import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;

/**
 * Sum for any number value.
 */
public class NumIntegerSumAggregator implements AggregationMethod
{
    private int sum;
    private long numDataPoints;

    public void enter(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints++;
        Number number = (Number) object;
        sum += number.intValue();
    }

    public void leave(Object object)
    {
        if (object == null)
        {
            return;
        }
        numDataPoints--;
        Number number = (Number) object;
        sum -= number.intValue();
    }

    public Object getValue()
    {
        if (numDataPoints == 0)
        {
            return null;
        }
        return sum;
    }

    public Class getValueType()
    {
        return Integer.class;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeSumAggregator(null);
    }
}
