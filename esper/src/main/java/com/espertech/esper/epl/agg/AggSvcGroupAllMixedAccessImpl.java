/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

/**
 * Implementation for handling aggregation without any grouping (no group-by).
 */
public class AggSvcGroupAllMixedAccessImpl extends AggregationServiceBase
{
    private final AggregationAccessorSlotPair[] accessors;
    private AggregationAccess[] accesses;

    /**
     * Ctor.
     * @param evaluators - evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
     * @param aggregators - collect the aggregation state that evaluators evaluate to
     */
    public AggSvcGroupAllMixedAccessImpl(ExprEvaluator evaluators[],
                                         AggregationMethod aggregators[],
                                         MethodResolutionService methodResolutionService,
                                         AggregationAccessorSlotPair[] accessors,
                                         int[] streams)
    {
        super(evaluators, aggregators);

        this.accessors = accessors;
        accesses = AggregationAccessUtil.getNewAccesses(streams, methodResolutionService, null);
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow, ExprEvaluatorContext exprEvaluatorContext)
    {
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream, true, exprEvaluatorContext);
            aggregators[j].enter(columnResult);
        }

        for (AggregationAccess access : accesses) {
            access.applyEnter(eventsPerStream);
        }
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow, ExprEvaluatorContext exprEvaluatorContext)
    {
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream, false, exprEvaluatorContext);
            aggregators[j].leave(columnResult);
        }

        for (AggregationAccess access : accesses) {
            access.applyLeave(eventsPerStream);
        }        
    }

    public void setCurrentAccess(MultiKeyUntyped groupKey)
    {
        // no action needed - this implementation does not group and the current row is the single group
    }

    public Object getValue(int column)
    {
        if (column < aggregators.length) {
            return aggregators[column].getValue();
        }
        else {
            AggregationAccessorSlotPair pair = accessors[column - aggregators.length];
            return pair.getAccessor().getValue(accesses[pair.getSlot()]);
        }
    }

    public void clearResults()
    {
        //TODO
        for (AggregationMethod aggregator : aggregators)
        {
            aggregator.clear();
        }
    }
}