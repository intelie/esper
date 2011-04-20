package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalTakeWhileLastScalar extends EnumEvalBaseScalar implements EnumEval {

    public EnumEvalTakeWhileLastScalar(ExprEvaluator innerExpression, int streamCountIncoming, MapEventType type, String propertyName) {
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

        int size = target.size();
        Object[] all = new Object[size];
        int count = 0;
        for (Object item : target) {
            all[count++] = item;
        }

        ArrayDeque<Object> result = new ArrayDeque<Object>();

        for (int i = all.length - 1; i >= 0; i--) {
            evalEvent.getProperties().put(evalPropertyName, all[i]);
            eventsLambda[streamNumLambda] = evalEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                break;
            }

            result.addFirst(all[i]);
        }

        return result;
    }
}
