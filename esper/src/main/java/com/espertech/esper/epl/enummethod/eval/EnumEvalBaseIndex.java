package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

public abstract class EnumEvalBaseIndex implements EnumEval {

    protected ExprEvaluator innerExpression;
    protected int streamNumLambda;
    protected MapEventBean indexEvent;
    protected String indexPropertyName;
    protected EventBean[] eventsLambda;

    public EnumEvalBaseIndex(ExprEvaluator innerExpression, int streamNumLambda, MapEventType indexEventType, String indexPropertyName) {
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.indexEvent = new MapEventBean(new HashMap<String, Object>(), indexEventType);
        this.indexPropertyName = indexPropertyName;
        this.eventsLambda = new EventBean[streamNumLambda + 2];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }
}
