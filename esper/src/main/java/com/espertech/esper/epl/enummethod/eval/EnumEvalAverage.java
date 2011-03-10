package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalAverage extends EnumEvalBase implements EnumEval {

    public EnumEvalAverage(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        double sum = 0d;
        int count = 0;

        for (EventBean next : target) {
            eventsLambda[streamNumLambda] = next;

            Number num = (Number) innerExpression.evaluate(eventsLambda, isNewData, context);
            if (num == null) {
                continue;
            }
            count++;
            sum += num.doubleValue();
        }

        if (count == 0) {
            return null;
        }
        return sum / count;
    }
}
