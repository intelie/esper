/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.agg;

/**
 * A row in aggregation state, with aging information.
 */
public class AggregationMethodRowAged
{
    private long refcount;
    private long lastUpdateTime;
    private final AggregationMethod[] methods;
    private final AggregationAccess[] accesses;

    /**
     * Ctor.
     * @param lastUpdateTime time of creation
     * @param refcount number of items in state
     * @param methods aggregations
     * @param accesses for first/last/window type access
     */
    public AggregationMethodRowAged(long refcount, long lastUpdateTime, AggregationMethod[] methods, AggregationAccess[] accesses)
    {
        this.refcount = refcount;
        this.lastUpdateTime = lastUpdateTime;
        this.methods = methods;
        this.accesses = accesses;
    }

    /**
     * Returns number of data points.
     * @return data points
     */
    public long getRefcount()
    {
        return refcount;
    }

    /**
     * Returns last upd time.
     * @return time
     */
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

    /**
     * Set last update time.
     * @param lastUpdateTime time
     */
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

    /**
     * Returns the accesses for first/last/window aggregation functions.
     * @return accesses
     */
    public AggregationAccess[] getAccesses()
    {
        return accesses;
    }
}