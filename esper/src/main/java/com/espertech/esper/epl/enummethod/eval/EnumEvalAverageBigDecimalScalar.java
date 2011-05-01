package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.agg.BigDecimalAvgAggregator;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventType;

import java.util.Collection;

public class EnumEvalAverageBigDecimalScalar extends EnumEvalBase implements EnumEval {

    public EnumEvalAverageBigDecimalScalar(int streamCountIncoming) {
        super(streamCountIncoming);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        BigDecimalAvgAggregator agg = new BigDecimalAvgAggregator();

        for (Object next : target) {

            Number num = (Number) next;
            if (num == null) {
                continue;
            }
            agg.enter(num);
        }

        return agg.getValue();
    }
}
