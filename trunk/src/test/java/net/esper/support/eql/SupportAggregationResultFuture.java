package net.esper.support.eql;

import net.esper.eql.expression.AggregationResultFuture;

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
}
