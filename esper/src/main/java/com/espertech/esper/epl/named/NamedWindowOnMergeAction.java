package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public interface NamedWindowOnMergeAction {
    public boolean isApplies(EventBean[] eventsPerStream, ExprEvaluatorContext context);
    public void apply(EventBean matchingEvent, EventBean[] eventsPerStream, OneEventCollection newData, OneEventCollection oldData, ExprEvaluatorContext exprEvaluatorContext);
}
