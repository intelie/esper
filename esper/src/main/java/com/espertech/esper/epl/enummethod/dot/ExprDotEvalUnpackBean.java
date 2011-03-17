package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class ExprDotEvalUnpackBean implements ExprDotEval {

    private final Class returnType;

    public ExprDotEvalUnpackBean(EventType lambdaType) {
        returnType = lambdaType.getUnderlyingType();
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        EventBean event = (EventBean) target;
        return event.getUnderlying();
    }

    public Class getResultType() {
        return returnType;
    }

    public EventType getResultEventType() {
        return null;
    }
}
