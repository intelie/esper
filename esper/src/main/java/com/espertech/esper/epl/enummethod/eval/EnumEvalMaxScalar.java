package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalMaxScalar extends EnumEvalBase implements EnumEval {

    public EnumEvalMaxScalar(int streamCountIncoming) {
        super(streamCountIncoming);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Comparable minKey = null;

        for (Object next : target) {

            Object comparable = next;
            if (comparable == null) {
                continue;
            }

            if (minKey == null) {
                minKey = (Comparable) comparable;
            }
            else {
                if (minKey.compareTo(comparable) < 0) {
                    minKey = (Comparable) comparable;
                }
            }
        }

        return minKey;
    }
}
