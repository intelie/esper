package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public abstract class SortedAccessStrategyRelOpBase {
    private final ExprEvaluator keyEval;
    private final EventBean[] events;
    private final int lookupStream;
    private final boolean isNWOnTrigger;

    protected SortedAccessStrategyRelOpBase(boolean isNWOnTrigger, int lookupStream, int numStreams, ExprEvaluator keyEval) {
        this.lookupStream = lookupStream;
        this.keyEval = keyEval;
        this.isNWOnTrigger = isNWOnTrigger;
        if (lookupStream != -1) {
            events = new EventBean[lookupStream + 1];
        }
        else {
            events = new EventBean[numStreams + 1];
        }
    }

    public Object evaluateLookup(EventBean event, ExprEvaluatorContext context) {
        events[lookupStream] = event;
        return keyEval.evaluate(events, true, context);
    }

    public Object evaluatePerStream(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        if (isNWOnTrigger) {
            return keyEval.evaluate(eventsPerStream, true, context);
        }
        else {
            System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);
            return keyEval.evaluate(events, true, context);
        }
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " key " + keyEval.getClass().getSimpleName();
    }
}
