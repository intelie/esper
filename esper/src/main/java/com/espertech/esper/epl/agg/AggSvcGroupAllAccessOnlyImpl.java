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

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

/**
 * Aggregation service for use when only first/last/window aggregation functions are used an none other.
 */
public class AggSvcGroupAllAccessOnlyImpl implements AggregationService, AggregationResultFuture
{
    private final AggregationAccessorSlotPair[] accessors;
    private final AggregationAccess[] accesses;

    /**
     * Ctor.
     * @param methodResolutionService factory service for implementations
     * @param accessors accessor definitions
     * @param streams streams in join
     * @param isJoin true for join, false for single-stream
     */
    public AggSvcGroupAllAccessOnlyImpl(MethodResolutionService methodResolutionService,
                                                   AggregationAccessorSlotPair[] accessors,
                                                   int[] streams,
                                                   boolean isJoin)
    {
        this.accessors = accessors;
        accesses = AggregationAccessUtil.getNewAccesses(isJoin, streams, methodResolutionService, null);
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped groupKey, ExprEvaluatorContext exprEvaluatorContext)
    {
        for (AggregationAccess access : accesses) {
            access.applyEnter(eventsPerStream);
        }
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped groupKey, ExprEvaluatorContext exprEvaluatorContext)
    {
        for (AggregationAccess access : accesses) {
            access.applyLeave(eventsPerStream);
        }
    }

    public void setCurrentAccess(MultiKeyUntyped groupKey)
    {
        // no implementation required
    }

    public Object getValue(int column)
    {
        AggregationAccessorSlotPair pair = accessors[column];
        return pair.getAccessor().getValue(accesses[pair.getSlot()]);
    }

    public EventBean getEventBean(int column) {
        AggregationAccessorSlotPair pair = accessors[column];
        return pair.getAccessor().getEventBean(accesses[pair.getSlot()]);
    }

    public Collection<EventBean> getCollection(int column)
    {
        AggregationAccessorSlotPair pair = accessors[column];
        return pair.getAccessor().getCollectionReadOnly(accesses[pair.getSlot()]);
    }

    public void clearResults()
    {
        for (AggregationAccess access : accesses) {
            access.clear();
        }
    }
}