package com.espertech.esper.support.epl;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.agg.AggregationResultFuture;

import java.util.Collection;

public class SupportAggregationResultFuture implements AggregationResultFuture
{
    private Object[] values;

    public SupportAggregationResultFuture(Object[] values)
    {
        this.values = values;
    }

    public Object getValue(int column)
    {
        return values[column];
    }

    public Collection<EventBean> getCollection(int column) {
        return null;
    }
}
