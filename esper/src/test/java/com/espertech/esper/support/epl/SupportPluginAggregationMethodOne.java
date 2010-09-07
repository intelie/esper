package com.espertech.esper.support.epl;

import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.agg.AggregationValidationContext;

import java.io.Serializable;

public class SupportPluginAggregationMethodOne extends AggregationSupport implements Serializable
{
    private int count;

    public void clear()
    {
        count = 0;    
    }

    @Override
    public void validate(AggregationValidationContext validationContext)
    {
    }

    public void enter(Object value)
    {
        count--;
    }

    public void leave(Object value)
    {
        count++;
    }

    public Object getValue()
    {
        return count;
    }

    public Class getValueType()
    {
        return int.class;
    }
}
