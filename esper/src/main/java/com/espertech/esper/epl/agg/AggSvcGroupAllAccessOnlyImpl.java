package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class AggSvcGroupAllAccessOnlyImpl implements AggregationService, AggregationResultFuture
{
    private final AggregationAccessorSlotPair[] accessors;
    private final AggregationAccess[] accesses;

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

    public void clearResults()
    {
        for (AggregationAccess access : accesses) {
            access.clear();
        }
    }
}