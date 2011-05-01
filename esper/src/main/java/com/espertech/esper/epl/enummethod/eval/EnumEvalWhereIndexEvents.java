package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

public class EnumEvalWhereIndexEvents extends EnumEvalBaseIndex implements EnumEval {

    public EnumEvalWhereIndexEvents(ExprEvaluator innerExpression, int streamNumLambda, MapEventType indexEventType, String indexPropertyName) {
        super(innerExpression, streamNumLambda, indexEventType, indexPropertyName);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return target;
        }

        Collection<EventBean> beans = (Collection<EventBean>) target;
        ArrayDeque<Object> result = new ArrayDeque<Object>();

        int count = -1;
        for (EventBean next : beans) {

            count++;

            indexEvent.getProperties().put(indexPropertyName, count);
            eventsLambda[streamNumLambda] = next;
            eventsLambda[streamNumLambda + 1] = indexEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                continue;
            }

            result.add(next);
        }

        return result;
    }
}
