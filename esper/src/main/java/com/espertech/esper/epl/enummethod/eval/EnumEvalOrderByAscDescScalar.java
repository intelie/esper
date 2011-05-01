package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EnumEvalOrderByAscDescScalar extends EnumEvalBase implements EnumEval {

    private final boolean descending;

    public EnumEvalOrderByAscDescScalar(int streamCountIncoming, boolean descending) {
        super(streamCountIncoming);
        this.descending = descending;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        if (target == null || target.isEmpty()) {
            return target;
        }

        List list = new ArrayList(target);
        if (descending) {
            Collections.sort(list, Collections.reverseOrder());
        }
        else {
            Collections.sort(list);
        }
        return list;
    }
}
