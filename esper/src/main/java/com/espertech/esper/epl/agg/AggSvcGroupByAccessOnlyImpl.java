package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.HashMap;
import java.util.Map;

public class AggSvcGroupByAccessOnlyImpl implements AggregationService, AggregationResultFuture
{
    private final MethodResolutionService methodResolutionService;
    private final Map<MultiKeyUntyped, AggregationAccess[]> accessMap;
    private final AggregationAccessorSlotPair[] accessors;
    private final int[] streams;

    private AggregationAccess[] currentAccess;

    public AggSvcGroupByAccessOnlyImpl(MethodResolutionService methodResolutionService,
                                                   AggregationAccessorSlotPair[] accessors,
                                                   int[] streams)
    {
        this.methodResolutionService = methodResolutionService;
        this.accessMap = new HashMap<MultiKeyUntyped, AggregationAccess[]>();
        this.accessors = accessors;
        this.streams = streams;
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped groupKey, ExprEvaluatorContext exprEvaluatorContext)
    {
        AggregationAccess[] row = getAssertRow(groupKey);
        for (AggregationAccess access : row) {
            access.applyEnter(eventsPerStream);
        }
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped groupKey, ExprEvaluatorContext exprEvaluatorContext)
    {
        AggregationAccess[] row = getAssertRow(groupKey);
        for (AggregationAccess access : row) {
            access.applyLeave(eventsPerStream);
        }
    }

    public void setCurrentAccess(MultiKeyUntyped groupKey)
    {
        currentAccess = getAssertRow(groupKey);
    }

    public Object getValue(int column)
    {
        AggregationAccessorSlotPair pair = accessors[column];
        return pair.getAccessor().getValue(currentAccess[pair.getSlot()]);
    }

    public void clearResults()
    {
        // TODO - test
    }

    private AggregationAccess[] getAssertRow(MultiKeyUntyped groupKey) {
        AggregationAccess[] row = accessMap.get(groupKey);
        if (row != null) {
            return row;
        }

        row = AggregationAccessUtil.getNewAccesses(streams, methodResolutionService, groupKey);
        accessMap.put(groupKey, row);
        return row;
    }
}
