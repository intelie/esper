package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalFirstOfNoPredicate extends EnumEvalBase implements EnumEval {

    public EnumEvalFirstOfNoPredicate(int streamCountIncoming) {
        super(streamCountIncoming);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target == null || target.isEmpty()) {
            return null;
        }
        return target.iterator().next();
    }
}
