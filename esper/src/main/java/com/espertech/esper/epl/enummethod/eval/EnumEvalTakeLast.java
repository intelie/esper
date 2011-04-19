package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalTakeLast implements EnumEval {

    private ExprEvaluator sizeEval;
    private EventBean[] events;

    public EnumEvalTakeLast(ExprEvaluator sizeEval, int numStreams) {
        this.sizeEval = sizeEval;
        this.events = new EventBean[numStreams];
    }

    public EventBean[] getEventsPrototype() {
        return events;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        Object sizeObj = sizeEval.evaluate(events, isNewData, context);
        if (sizeObj == null) {
            return null;
        }

        if (target.isEmpty()) {
            return target;
        }

        int size = ((Number) sizeObj).intValue();
        if (size <= 0) {
            return Collections.emptyList();
        }

        if (target.size() < size) {
            return target;
        }

        if (size == 1) {
            Object last = null;
            for (Object next : target) {
                last = next;
            }
            return Collections.singletonList(last);
        }

        ArrayList<Object> result = new ArrayList<Object>();
        for (Object next : target) {
            result.add(next);
            if (result.size() > size) {
                result.remove(0);
            }
        }
        return result;
    }
}
