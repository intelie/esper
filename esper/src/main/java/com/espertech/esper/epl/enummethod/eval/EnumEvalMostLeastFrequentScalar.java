package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnumEvalMostLeastFrequentScalar extends EnumEvalBase implements EnumEval {

    private final boolean isMostFrequent;

    public EnumEvalMostLeastFrequentScalar(int streamCountIncoming, boolean isMostFrequent) {
        super(streamCountIncoming);
        this.isMostFrequent = isMostFrequent;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Map<Object, Integer> items = new LinkedHashMap<Object, Integer>();

        for (Object next : target) {
            Integer existing = items.get(next);

            if (existing == null) {
                existing = 1;
            }
            else {
                existing++;
            }
            items.put(next, existing);
        }

        return EnumEvalMostLeastFrequentEvent.getResult(items, isMostFrequent);
    }
}
