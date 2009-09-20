package com.espertech.esper.epl.agg;

/**
 * A row in aggregation state, with aging information.
 */
public class AggregationMethodRowAged
{
    private long refcount;
    private long lastUpdateTime;
    private AggregationMethod[] methods;

    /**
     * Ctor.
     * @param lastUpdateTime time of creation
     * @param refcount number of items in state
     * @param methods aggregations
     */
    public AggregationMethodRowAged(long refcount, long lastUpdateTime, AggregationMethod[] methods)
    {
        this.refcount = refcount;
        this.lastUpdateTime = lastUpdateTime;
        this.methods = methods;
    }

    /**
     * Returns number of data points.
     * @return data points
     */
    public long getRefcount()
    {
        return refcount;
    }

    public long getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    /**
     * Returns aggregation state.
     * @return state
     */
    public AggregationMethod[] getMethods()
    {
        return methods;
    }

    public void setLastUpdateTime(long lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * Increase number of data points by one.
     */
    public void increaseRefcount()
    {
        refcount++;
    }

    /**
     * Decrease number of data points by one.
     */
    public void decreaseRefcount()
    {
        refcount--;
    }

}