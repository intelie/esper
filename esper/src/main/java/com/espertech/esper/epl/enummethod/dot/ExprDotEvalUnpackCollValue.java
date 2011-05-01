package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class ExprDotEvalUnpackCollValue implements ExprDotEval {

    private final ExprDotEvalTypeInfo typeInfo;

    public ExprDotEvalUnpackCollValue(ExprDotEvalTypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        return target;
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        return typeInfo;
    }
}
