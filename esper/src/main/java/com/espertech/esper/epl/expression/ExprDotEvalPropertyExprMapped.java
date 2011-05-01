package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetterMapped;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExprDotEvalPropertyExprMapped extends ExprDotEvalPropertyExprBase {
    private static final Log log = LogFactory.getLog(ExprDotEvalPropertyExprMapped.class);

    private final EventPropertyGetterMapped mappedGetter;

    public ExprDotEvalPropertyExprMapped(String statementName, String propertyName, int streamNum, ExprEvaluator exprEvaluator, Class propertyType, EventPropertyGetterMapped mappedGetter) {
        super(statementName, propertyName, streamNum, exprEvaluator, propertyType);
        this.mappedGetter = mappedGetter;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        EventBean eventInQuestion = eventsPerStream[super.streamNum];
        if (eventInQuestion == null) {
            return null;
        }
        Object result = exprEvaluator.evaluate(eventsPerStream, isNewData, context);
        if (result != null && (!(result instanceof String))) {
            log.warn(super.getWarningText("string", result));
            return null;
        }
        return mappedGetter.get(eventInQuestion, (String) result);
    }
}
