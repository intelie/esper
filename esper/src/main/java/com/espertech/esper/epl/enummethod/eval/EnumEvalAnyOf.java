package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalAnyOf extends EnumEvalBase implements EnumEval {

    public EnumEvalAnyOf(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        for (EventBean next : target) {
            eventsLambda[streamNumLambda] = next;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass != null && ((Boolean) pass)) {
                return true;
            }
        }

        return false;
    }
}
