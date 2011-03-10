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

public class EnumEvalTakeWhileLastIndex implements EnumEval {

    private ExprEvaluator innerExpression;
    private int streamNumLambda;
    private MapEventBean indexEvent;
    private String indexPropertyName;
    private EventBean[] eventsLambda;

    public EnumEvalTakeWhileLastIndex(ExprEvaluator innerExpression, int streamNumLambda, MapEventType indexEventType, String indexPropertyName) {
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

        int size = target.size();
        EventBean[] all = new EventBean[size];
        int count = 0;
        for (EventBean item : target) {
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
