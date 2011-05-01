package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Arrays;

public abstract class SortedAccessStrategyRangeBase  {
    protected ExprEvaluator start;
    protected boolean includeStart;
    protected ExprEvaluator end;
    protected boolean includeEnd;

    private final boolean isNWOnTrigger;
    private final EventBean[] events;
    private final int lookupStream;

    protected SortedAccessStrategyRangeBase(boolean isNWOnTrigger, int lookupStream, int numStreams, ExprEvaluator start, boolean includeStart, ExprEvaluator end, boolean includeEnd) {
        this.start = start;
        this.includeStart = includeStart;
        this.end = end;
        this.includeEnd = includeEnd;
        this.isNWOnTrigger = isNWOnTrigger;

        this.lookupStream = lookupStream;
        if (lookupStream != -1) {
            events = new EventBean[lookupStream + 1];
        }
        else {
            events = new EventBean[numStreams + 1];
        }
    }

    public Object evaluateLookupStart(EventBean event, ExprEvaluatorContext context) {
        events[lookupStream] = event;
        return start.evaluate(events, true, context);
    }

    public Object evaluateLookupEnd(EventBean event, ExprEvaluatorContext context) {
        events[lookupStream] = event;
        return end.evaluate(events, true, context);
    }

    public Object evaluatePerStreamStart(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        if (isNWOnTrigger) {
            return start.evaluate(eventsPerStream, true, context);
        }
        else {
            System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);
            return start.evaluate(events, true, context);
        }
    }

    public Object evaluatePerStreamEnd(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        if (isNWOnTrigger) {
            return end.evaluate(eventsPerStream, true, context);
        }
        else {
            System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);
            return end.evaluate(events, true, context);
        }
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " start=" + start.getClass().getSimpleName() +
                ", includeStart=" + includeStart +
                ", end=" + end.getClass().getSimpleName() +
                ", includeEnd=" + includeEnd;
    }
}
