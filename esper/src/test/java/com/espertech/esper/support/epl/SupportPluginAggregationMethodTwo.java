package com.espertech.esper.support.epl;

import com.espertech.esper.epl.agg.AggregationSupport;

import java.io.Serializable;

public class SupportPluginAggregationMethodTwo extends AggregationSupport implements Serializable
{
    public void validate(Class childNodeType)
    {
        throw new IllegalArgumentException("Invalid node type: " + childNodeType.getName());
    }

    public void clear()
    {
        //To change body of implemented methods use File | Settings | File Templates.
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
