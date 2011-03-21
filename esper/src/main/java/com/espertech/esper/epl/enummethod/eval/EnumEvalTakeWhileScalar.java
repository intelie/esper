package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalTakeWhileScalar extends EnumEvalBaseScalar implements EnumEval {

    public EnumEvalTakeWhileScalar(ExprEvaluator innerExpression, int streamCountIncoming, MapEventType type, String propertyName) {
        super(innerExpression, streamCountIncoming, type, propertyName);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return target;
        }

        if (target.size() == 1) {
            Object item = target.iterator().next();
            evalEvent.getProperties().put(evalPropertyName, item);
            eventsLambda[streamNumLambda] = evalEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                return Collections.emptyList();
            }
            return Collections.singletonList(item);
        }

        ArrayDeque<Object> result = new ArrayDeque<Object>();

        for (Object next : target) {

            evalEvent.getProperties().put(evalPropertyName, next);
            eventsLambda[streamNumLambda] = evalEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                break;
            }

            result.add(next);
        }

        return result;
    }
}
