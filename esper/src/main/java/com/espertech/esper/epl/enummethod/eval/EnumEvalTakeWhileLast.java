package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalTakeWhileLast extends EnumEvalBase implements EnumEval {

    public EnumEvalTakeWhileLast(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        if (target.isEmpty()) {
            return target;
        }

        if (target.size() == 1) {
            EventBean item = target.iterator().next();
            eventsLambda[streamNumLambda] = item;

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                return Collections.emptyList();
            }
            return Collections.singletonList(item);
        }
        
        int size = target.size();
        EventBean[] all = new EventBean[size];
        int count = 0;
        for (EventBean item : target) {
            all[count++] = item;
        }

        ArrayDeque<Object> result = new ArrayDeque<Object>();

        for (int i = all.length - 1; i >= 0; i--) {
            eventsLambda[streamNumLambda] = all[i];

            Object pass = innerExpression.evaluate(eventsLambda, isNewData, context);
            if (pass == null || (!(Boolean) pass)) {
                break;
            }

            result.addFirst(all[i]);
        }

        return result;
    }
}
