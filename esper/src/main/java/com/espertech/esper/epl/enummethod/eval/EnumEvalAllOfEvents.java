package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalAllOfEvents extends EnumEvalBase implements EnumEval {

    public EnumEvalAllOfEvents(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return true;
        }

        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {
            eventsLambda[streamNumLambda] = next;

            Object result = getInnerExpression().evaluate(eventsLambda, isNewData, context);
            if (result == null || (!(Boolean) result)) {
                return false;
            }
        }

        return true;
    }
}
