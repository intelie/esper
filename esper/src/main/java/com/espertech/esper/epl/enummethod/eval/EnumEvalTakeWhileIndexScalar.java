package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalTakeWhileIndexScalar extends EnumEvalBaseScalarIndex implements EnumEval {

    public EnumEvalTakeWhileIndexScalar(ExprEvaluator innerExpression, int streamNumLambda, MapEventType evalEventType, String evalPropertyName, MapEventType indexEventType, String indexPropertyName) {
        super(innerExpression, streamNumLambda, evalEventType, evalPropertyName, indexEventType, indexPropertyName);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return target;
        }

        if (target.size() == 1) {
            Object item = target.iterator().next();

            evalEvent.getProperties().put(evalPropertyName, item);
            eventsLambda[streamNumLambda] = evalEvent;

            indexEvent.getProperties().put(indexPropertyName, 0);
            eventsLambda[streamNumLambda + 1] = indexEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                return Collections.emptyList();
            }
            return Collections.singletonList(item);
        }

        ArrayDeque<Object> result = new ArrayDeque<Object>();

        int count = -1;
        for (Object next : target) {

            count++;

            evalEvent.getProperties().put(evalPropertyName, next);
            eventsLambda[streamNumLambda] = evalEvent;

            indexEvent.getProperties().put(indexPropertyName, count);
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
