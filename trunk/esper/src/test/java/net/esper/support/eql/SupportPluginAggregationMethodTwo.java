package net.esper.support.eql;

import net.esper.eql.agg.AggregationSupport;

public class SupportPluginAggregationMethodTwo extends AggregationSupport
{
    public void validate(Class childNodeType)
    {
        throw new IllegalArgumentException("Invalid node type: " + childNodeType.getName());
    }

    public void enter(Object value)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void leave(Object value)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getValue()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Class getValueType()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
