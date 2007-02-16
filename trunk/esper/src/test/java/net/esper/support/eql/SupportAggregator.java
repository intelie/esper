package net.esper.support.eql;

import net.esper.eql.core.Aggregator;

public class SupportAggregator implements Aggregator
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

    public Aggregator newAggregator()
    {
        return new SupportAggregator();
    }
}
