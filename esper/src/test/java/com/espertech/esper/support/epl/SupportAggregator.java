package com.espertech.esper.support.epl;

import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.core.MethodResolutionService;

public class SupportAggregator implements AggregationMethod
{
    private int sum;

    public void clear()
    {
        
    }

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
