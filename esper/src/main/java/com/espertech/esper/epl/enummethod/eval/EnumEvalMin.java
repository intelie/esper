package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalMin extends EnumEvalBase implements EnumEval {

    public EnumEvalMin(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        Comparable minKey = null;

        for (EventBean next : target) {
            eventsLambda[streamNumLambda] = next;

            Object comparable = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (comparable == null) {
                continue;
            }

            if (minKey == null) {
                minKey = (Comparable) comparable;
            }
            else {
                if (minKey.compareTo(comparable) > 0) {
                    minKey = (Comparable) comparable;
                }
            }
        }

        return minKey;
    }
}
