package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprEvaluatorEnumeration;

import java.util.ArrayList;
import java.util.Collection;

public class EnumEvalUnion implements EnumEval {

    private final EventBean[] events;
    private final ExprEvaluatorEnumeration evaluator;

    public EnumEvalUnion(int numStreams, ExprEvaluatorEnumeration evaluator) {
        this.events = new EventBean[numStreams];
        this.evaluator = evaluator;
    }

    public EventBean[] getEventsPrototype() {
        return events;
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        if (target == null) {
            return null;
        }

        Collection<EventBean> set = evaluator.evaluateGetROCollection(events, isNewData, context);
        if (set == null && set.isEmpty()) {
            return target;
        }

        ArrayList<Object> result = new ArrayList<Object>();
        result.addAll(target);
        result.addAll(set);

        return result;
    }
}
