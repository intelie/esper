package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalSumEvents extends EnumEvalBase implements EnumEval {

    private final AggregationMethod aggregationMethod;

    public EnumEvalSumEvents(ExprEvaluator innerExpression, int streamCountIncoming, AggregationMethod aggregationMethod) {
        super(innerExpression, streamCountIncoming);
        this.aggregationMethod = aggregationMethod;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        aggregationMethod.clear();
        
        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {
            eventsLambda[streamNumLambda] = next;

            Object value = innerExpression.evaluate(eventsLambda, isNewData, context);
            aggregationMethod.enter(value);
        }

        return aggregationMethod.getValue();
    }
}
