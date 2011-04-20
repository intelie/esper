package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalReverse implements EnumEval {

    private EventBean[] events;

    public EnumEvalReverse(int numStreams) {
        this.events = new EventBean[numStreams];
    }

    public EventBean[] getEventsPrototype() {
        return events;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return target;
        }

        ArrayList<Object> result = new ArrayList<Object>(target.size());
        result.addAll(target);
        Collections.reverse(result);
        return result;
    }
}
