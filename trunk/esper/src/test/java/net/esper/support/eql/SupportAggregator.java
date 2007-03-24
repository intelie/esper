package net.esper.support.eql;

import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;

public class SupportAggregator implements AggregationMethod
{
    private int sum;

    public void enter(Object value)
    {
        if (value != null)
        {
            sum += (Integer) value;
        }
    }

    public void leave(Object value)
    {
        if (value != null)
        {
            sum -= (Integer) value;
        }
    }

    public Object getValue()
    {
        return sum;
    }

    public Class getValueType()
    {
        return Integer.class;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return new SupportAggregator();
    }
}
