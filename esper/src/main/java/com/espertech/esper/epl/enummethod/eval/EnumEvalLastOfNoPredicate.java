package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalLastOfNoPredicate extends EnumEvalBase implements EnumEval {

    public EnumEvalLastOfNoPredicate(int streamCountIncoming) {
        super(streamCountIncoming);
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {

        Object result = null;
        for (EventBean next : target) {
            result = next;
        }
        return result;
    }
}
