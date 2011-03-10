package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.*;

public class EnumEvalOrderByAscDesc extends EnumEvalBase implements EnumEval {

    private final boolean descending;

    public EnumEvalOrderByAscDesc(ExprEvaluator innerExpression, int streamCountIncoming, boolean descending) {
        super(innerExpression, streamCountIncoming);
        this.descending = descending;
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {
        TreeMap<Comparable, Object> sort = new TreeMap<Comparable, Object>();
        boolean hasColl = false;

        for (EventBean next : target) {
            eventsLambda[streamNumLambda] = next;

            Comparable comparable = (Comparable) innerExpression.evaluate(eventsLambda, isNewData, context);
            Object entry = sort.get(comparable);

            if (entry == null) {
                sort.put(comparable, next);
                continue;
            }

            if (entry instanceof Collection) {
                ((Collection) entry).add(next);
                continue;
            }

            Deque<Object> coll = new ArrayDeque<Object>();
            coll.add(entry);
            coll.add(next);
            sort.put(comparable, coll);
            hasColl = true;
        }

        Map<Comparable, Object> sorted;
        if (descending) {
            sorted = sort.descendingMap();
        }
        else {
            sorted = sort;
        }

        if (!hasColl) {
            return sorted.values();
        }

        Deque<Object> coll = new ArrayDeque<Object>();
        for (Map.Entry<Comparable, Object> entry : sorted.entrySet()) {
            if (entry.getValue() instanceof Collection) {
                coll.addAll((Collection) entry.getValue());
            }
            else {
                coll.add(entry.getValue());
            }
        }
        return coll;
    }
}
