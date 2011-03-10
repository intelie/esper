package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class EnumEvalTakeWhileIndex implements EnumEval {

    private ExprEvaluator innerExpression;
    private int streamNumLambda;
    private MapEventBean indexEvent;
    private String indexPropertyName;
    private EventBean[] eventsLambda;

    public EnumEvalTakeWhileIndex(ExprEvaluator innerExpression, int streamNumLambda, MapEventType indexEventType, String indexPropertyName) {
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.indexEvent = new MapEventBean(new HashMap<String, Object>(), indexEventType);
        this.indexPropertyName = indexPropertyName;
        this.eventsLambda = new EventBean[streamNumLambda + 2];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return target;
        }

        if (target.size() == 1) {
            EventBean item = target.iterator().next();
            indexEvent.getProperties().put(indexPropertyName, 0);
            eventsLambda[streamNumLambda] = item;
            eventsLambda[streamNumLambda + 1] = indexEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                return Collections.emptyList();
            }
            return Collections.singletonList(item);
        }

        ArrayDeque<Object> result = new ArrayDeque<Object>();

        int count = -1;
        for (EventBean next : target) {

            count++;

            indexEvent.getProperties().put(indexPropertyName, count);
            eventsLambda[streamNumLambda] = next;
            eventsLambda[streamNumLambda + 1] = indexEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                break;
            }

            result.add(next);
        }

        return result;
    }
}
