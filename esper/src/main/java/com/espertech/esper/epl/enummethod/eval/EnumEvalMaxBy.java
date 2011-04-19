package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalMaxBy extends EnumEvalBase implements EnumEval {

    public EnumEvalMaxBy(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Comparable maxKey = null;
        EventBean result = null;

        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {
            eventsLambda[streamNumLambda] = next;

            Object comparable = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (comparable == null) {
                continue;
            }

            if (maxKey == null) {
                maxKey = (Comparable) comparable;
                result = next;
            }
            else {
                if (maxKey.compareTo(comparable) < 0) {
                    maxKey = (Comparable) comparable;
                    result = next;
                }
            }
        }

        return result;  // unpack of EventBean to underlying performed at another stage 
    }
}
