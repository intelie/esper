package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class EnumEvalSelectFrom extends EnumEvalBase implements EnumEval {

    public EnumEvalSelectFrom(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {

        if (target.isEmpty()) {
            return target;
        }

        Deque queue = new ArrayDeque();
        for (EventBean next : target) {
            eventsLambda[streamNumLambda] = next;

            Object item = innerExpression.evaluate(eventsLambda, isNewData, context);
            queue.add(item);
        }

        return queue;
    }
}
