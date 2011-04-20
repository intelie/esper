package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Collection;
import java.util.Set;

public class SortedAccessStrategyLT extends SortedAccessStrategyRelOpBase implements SortedAccessStrategy {

    public SortedAccessStrategyLT(boolean isNWOnTrigger, int lookupStream, int numStreams, ExprEvaluator keyEval) {
        super(isNWOnTrigger, lookupStream, numStreams, keyEval);
    }

    public Set<EventBean> lookup(EventBean event, PropertySortedEventTable index, ExprEvaluatorContext context) {
        return index.lookupLess(super.evaluateLookup(event, context));
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, PropertySortedEventTable index, ExprEvaluatorContext context) {
        return index.lookupLessThenColl(super.evaluatePerStream(eventsPerStream, context));
    }
}