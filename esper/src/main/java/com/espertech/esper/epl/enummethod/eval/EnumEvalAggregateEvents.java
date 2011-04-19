package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventType;

import java.util.Collection;

public class EnumEvalAggregateEvents extends EnumEvalAggregateBase implements EnumEval {

    public EnumEvalAggregateEvents(ExprEvaluator initialization, ExprEvaluator innerExpression, int streamNumLambda, MapEventType resultEventType, String resultPropertyName) {
        super(initialization, innerExpression, streamNumLambda, resultEventType, resultPropertyName);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Object initializationValue = initialization.evaluate(eventsLambda, isNewData, context);

        if (target.isEmpty()) {
            return initializationValue;
        }

        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {

            this.resultEvent.getProperties().put(resultPropertyName, initializationValue);
            eventsLambda[streamNumLambda + 1] = next;
            eventsLambda[streamNumLambda] = this.resultEvent;

            initializationValue = innerExpression.evaluate(eventsLambda, isNewData, context);
        }

        return initializationValue;
    }
}
