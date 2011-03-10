package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnumEvalToMap extends EnumEvalBase implements EnumEval {

    private ExprEvaluator secondExpression;

    public EnumEvalToMap(ExprEvaluator innerExpression, int streamCountIncoming, ExprEvaluator secondExpression) {
        super(innerExpression, streamCountIncoming);
        this.secondExpression = secondExpression;
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        Map map = new HashMap();

        for (EventBean next : target) {
            eventsLambda[streamNumLambda] = next;

            Object key = innerExpression.evaluate(eventsLambda, isNewData, context);
            Object value = secondExpression.evaluate(eventsLambda, isNewData, context);
            map.put(key, value);
        }

        return map;
    }
}
