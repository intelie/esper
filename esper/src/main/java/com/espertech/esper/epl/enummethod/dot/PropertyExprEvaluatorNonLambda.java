package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprValidationException;

import java.util.Map;

public class PropertyExprEvaluatorNonLambda implements ExprEvaluator {

    private final int streamId;
    private final EventPropertyGetter getter;
    private final Class returnType;

    public PropertyExprEvaluatorNonLambda(int streamId, EventPropertyGetter getter, Class returnType) {
        this.streamId = streamId;
        this.getter = getter;
        this.returnType = returnType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        EventBean eventInQuestion = eventsPerStream[streamId];
        if (eventInQuestion == null) {
            return null;
        }
        return getter.get(eventInQuestion);
    }

    public Class getType() {
        return returnType;
    }

    public Map<String, Object> getEventType() throws ExprValidationException {
        return null;
    }
}
