package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventType;

import java.util.Collection;

public class EnumEvalCountOfSelectorScalar extends EnumEvalBaseScalar implements EnumEval {

    public EnumEvalCountOfSelectorScalar(ExprEvaluator innerExpression, int streamCountIncoming, MapEventType type, String propertyName) {
        super(innerExpression, streamCountIncoming, type, propertyName);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        int count = 0;

        for (Object next : target) {
            evalEvent.getProperties().put(evalPropertyName, next);
            eventsLambda[streamNumLambda] = evalEvent;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                continue;
            }
            count++;
        }

        return count;
    }
}
