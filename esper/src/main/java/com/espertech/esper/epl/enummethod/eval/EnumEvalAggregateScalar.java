package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.Collection;
import java.util.HashMap;

public class EnumEvalAggregateScalar extends EnumEvalAggregateBase implements EnumEval {

    private MapEventBean evalEvent;
    private String evalPropertyName;

    public EnumEvalAggregateScalar(ExprEvaluator initialization, ExprEvaluator innerExpression, int streamNumLambda, MapEventType resultEventType, String resultPropertyName, MapEventType evalEventType, String evalPropertyName) {
        super(initialization, innerExpression, streamNumLambda, resultEventType, resultPropertyName);
        this.evalEvent = new MapEventBean(new HashMap<String, Object>(), evalEventType);
        this.evalPropertyName = evalPropertyName;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Object initializationValue = initialization.evaluate(eventsLambda, isNewData, context);

        if (target.isEmpty()) {
            return initializationValue;
        }

        for (Object next : target) {

            this.resultEvent.getProperties().put(resultPropertyName, initializationValue);
            this.evalEvent.getProperties().put(evalPropertyName, next);
            eventsLambda[streamNumLambda] = this.resultEvent;
            eventsLambda[streamNumLambda + 1] = evalEvent;

            initializationValue = innerExpression.evaluate(eventsLambda, isNewData, context);
        }

        return initializationValue;
    }
}
