package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.agg.BigDecimalAvgAggregator;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalAverageBigDecimal extends EnumEvalBase implements EnumEval {

    public EnumEvalAverageBigDecimal(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context) {

        BigDecimalAvgAggregator agg = new BigDecimalAvgAggregator();

        for (EventBean next : target) {
            eventsLambda[streamNumLambda] = next;

            Number num = (Number) innerExpression.evaluate(eventsLambda, isNewData, context);
            if (num == null) {
                continue;
            }
            agg.enter(num);
        }

        return agg.getValue();
    }
}
