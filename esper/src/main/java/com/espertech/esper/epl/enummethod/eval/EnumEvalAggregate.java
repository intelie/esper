package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.Collection;
import java.util.HashMap;

public class EnumEvalAggregate implements EnumEval {

    private ExprEvaluator initialization;
    private ExprEvaluator innerExpression;
    private int streamNumLambda;
    private MapEventBean resultEvent;
    private String resultPropertyName;
    private EventBean[] eventsLambda;

    public EnumEvalAggregate(ExprEvaluator initialization,
                             ExprEvaluator innerExpression, int streamNumLambda,
                             MapEventType resultEventType, String resultPropertyName) {
        this.initialization = initialization;
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.resultEvent = new MapEventBean(new HashMap<String, Object>(), resultEventType);
        this.resultPropertyName = resultPropertyName;
        this.eventsLambda = new EventBean[streamNumLambda + 2];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        Object initializationValue = initialization.evaluate(eventsLambda, isNewData, context);

        for (EventBean next : target) {

            this.resultEvent.getProperties().put(resultPropertyName, initializationValue);
            eventsLambda[streamNumLambda + 1] = next;
            eventsLambda[streamNumLambda] = this.resultEvent;

            initializationValue = innerExpression.evaluate(eventsLambda, isNewData, context);
        }

        return initializationValue;
    }
}
