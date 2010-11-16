package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class NamedWindowOnMergeActionUpd implements NamedWindowOnMergeAction {
    private final NamedWindowUpdateHelper updateHelper;
    private final ExprEvaluator optionalFilter;

    public NamedWindowOnMergeActionUpd(NamedWindowUpdateHelper updateHelper, ExprEvaluator optionalFilter) {
        this.updateHelper = updateHelper;
        this.optionalFilter = optionalFilter;
    }

    public boolean isApplies(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        if (optionalFilter == null) {
            return true;
        }
        Object result = optionalFilter.evaluate(eventsPerStream, true, context);
        return result != null && (Boolean) result;
    }

    public void apply(EventBean matchingEvent, EventBean[] eventsPerStream, OneEventCollection newData, OneEventCollection oldData, ExprEvaluatorContext exprEvaluatorContext) {
        EventBean copy = updateHelper.update(matchingEvent, eventsPerStream, exprEvaluatorContext);
        newData.add(copy);
        oldData.add(matchingEvent);
    }
}
