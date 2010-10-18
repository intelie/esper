package com.espertech.esper.epl.agg;

public class AggregationRowPair
{
    private final AggregationMethod[] methods;
    private final AggregationAccess[] accesses;

    public AggregationRowPair(AggregationMethod[] methods, AggregationAccess[] accesses)
    {
        this.methods = methods;
        this.accesses = accesses;
    }

    public AggregationMethod[] getMethods()
    {
        return methods;
    }

    public AggregationAccess[] getAccesses()
    {
        return accesses;
    }
}
