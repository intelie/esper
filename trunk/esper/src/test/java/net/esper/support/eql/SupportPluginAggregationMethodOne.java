package net.esper.support.eql;

import net.esper.eql.agg.AggregationSupport;

public class SupportPluginAggregationMethodOne extends AggregationSupport
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
