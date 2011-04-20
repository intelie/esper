package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalTakeWhileLastIndexEvents extends EnumEvalBaseIndex implements EnumEval {

    public EnumEvalTakeWhileLastIndexEvents(ExprEvaluator innerExpression, int streamNumLambda, MapEventType indexEventType, String indexPropertyName) {
        super(innerExpression, streamNumLambda, indexEventType, indexPropertyName);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return target;
        }

        Collection<EventBean> beans = (Collection<EventBean>) target;
        if (target.size() == 1) {
            EventBean item = beans.iterator().next();
            indexEvent.getProperties().put(indexPropertyName, 0);
            eventsLambda[streamNumLambda] = item;
            eventsLambda[streamNumLambda + 1] = indexEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                return Collections.emptyList();
            }
            return Collections.singletonList(item);
        }

        int size = target.size();
        EventBean[] all = new EventBean[size];
        int count = 0;
        for (EventBean item : beans) {
            all[count++] = item;
        }

        ArrayDeque<Object> result = new ArrayDeque<Object>();
        int index = 0;
        for (int i = all.length - 1; i >= 0; i--) {

            indexEvent.getProperties().put(indexPropertyName, index++);
            eventsLambda[streamNumLambda] = all[i];
            eventsLambda[streamNumLambda + 1] = indexEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                break;
            }
            result.addFirst(all[i]);
        }

        return result;
    }
}
