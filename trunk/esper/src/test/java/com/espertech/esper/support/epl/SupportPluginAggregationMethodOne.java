package com.espertech.esper.support.epl;

import com.espertech.esper.epl.agg.AggregationSupport;

import java.io.Serializable;

public class SupportPluginAggregationMethodOne extends AggregationSupport implements Serializable
{
    private int count;

    public void clear()
    {
        count = 0;    
    }

    public void validate(Class childNodeType)
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
