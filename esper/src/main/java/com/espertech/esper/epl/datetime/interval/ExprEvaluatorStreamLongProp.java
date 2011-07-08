package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprValidationException;

import java.util.Map;

public class ExprEvaluatorStreamLongProp implements ExprEvaluator {

    private final int streamId;
    private final EventPropertyGetter getter;

    public ExprEvaluatorStreamLongProp(int streamId, EventPropertyGetter getter) {
        this.streamId = streamId;
        this.getter = getter;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        EventBean event = eventsPerStream[streamId];
        if (event == null) {
            return null;
        }
        return getter.get(event);
    }

    public Class getType() {
        return Long.class;
    }

    public Map<String, Object> getEventType() throws ExprValidationException {
        return null;
    }
}
