package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalSumScalar extends EnumEvalBase implements EnumEval {

    private final AggregationMethod aggregationMethod;

    public EnumEvalSumScalar(int streamCountIncoming, AggregationMethod aggregationMethod) {
        super(streamCountIncoming);
        this.aggregationMethod = aggregationMethod;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        aggregationMethod.clear();
        
        for (Object next : target) {

            aggregationMethod.enter(next);
        }

        return aggregationMethod.getValue();
    }
}
