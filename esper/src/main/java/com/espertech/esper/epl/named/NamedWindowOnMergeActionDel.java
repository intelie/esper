package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class NamedWindowOnMergeActionDel implements NamedWindowOnMergeAction {
    private final ExprEvaluator optionalFilter;

    public NamedWindowOnMergeActionDel(ExprEvaluator optionalFilter) {
        this.optionalFilter = optionalFilter;
    }

    public boolean isApplies(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        if (optionalFilter == null) {
            return true;
        }
        Object result = optionalFilter.evaluate(eventsPerStream, true, context);
        return result != null && (Boolean) result;
    }

    @Override
    public void apply(EventBean matchingEvent, EventBean[] eventsPerStream, OneEventCollection newData, OneEventCollection oldData, ExprEvaluatorContext exprEvaluatorContext) {
        oldData.add(matchingEvent);
    }
}
