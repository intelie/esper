package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.Collection;
import java.util.HashMap;

public class EnumEvalAggregateBase {

    protected ExprEvaluator initialization;
    protected ExprEvaluator innerExpression;
    protected int streamNumLambda;
    protected MapEventBean resultEvent;
    protected String resultPropertyName;
    protected EventBean[] eventsLambda;

    public EnumEvalAggregateBase(ExprEvaluator initialization,
                                 ExprEvaluator innerExpression, int streamNumLambda,
                                 MapEventType resultEventType, String resultPropertyName) {
        this.initialization = initialization;
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.resultEvent = new MapEventBean(new HashMap<String, Object>(), resultEventType);
        this.resultPropertyName = resultPropertyName;
        this.eventsLambda = new EventBean[streamNumLambda + 2];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }
}
