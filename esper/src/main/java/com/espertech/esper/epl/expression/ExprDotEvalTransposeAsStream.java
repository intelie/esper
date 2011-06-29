package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;

import java.util.Map;

public class ExprDotEvalTransposeAsStream implements ExprEvaluator {

    private final ExprEvaluator inner;

    public ExprDotEvalTransposeAsStream(ExprEvaluator inner) {
        this.inner = inner;
    }

    public Class getType() {
        return inner.getType();
    }

    public Map<String, Object> getEventType() throws ExprValidationException {
        return inner.getEventType();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        return inner.evaluate(eventsPerStream, isNewData, context);
    }
}
