package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;

public interface ExprDotEval
{
    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext);
    public ExprDotEvalTypeInfo getTypeInfo();
}
