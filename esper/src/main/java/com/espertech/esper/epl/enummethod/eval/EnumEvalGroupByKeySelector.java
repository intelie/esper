package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.*;

public class EnumEvalGroupByKeySelector extends EnumEvalBase implements EnumEval {

    public EnumEvalGroupByKeySelector(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Map<Object, Collection> result = new LinkedHashMap<Object, Collection>();

        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {
            eventsLambda[streamNumLambda] = next;

            Object key = innerExpression.evaluate(eventsLambda, isNewData, context);

            Collection value = result.get(key);
            if (value == null) {
                value = new ArrayList();
                result.put(key, value);
            }
            value.add(next.getUnderlying());
        }

        return result;
    }
}
