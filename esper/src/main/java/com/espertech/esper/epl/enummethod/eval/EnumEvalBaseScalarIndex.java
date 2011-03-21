package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

public abstract class EnumEvalBaseScalarIndex implements EnumEval {

    protected final ExprEvaluator innerExpression;
    protected final int streamNumLambda;
    protected final String evalPropertyName;
    protected final MapEventBean evalEvent;
    protected final String indexPropertyName;
    protected final MapEventBean indexEvent;

    protected EventBean[] eventsLambda;

    public EnumEvalBaseScalarIndex(ExprEvaluator innerExpression, int streamNumLambda, MapEventType evalEventType, String evalPropertyName, MapEventType indexEventType, String indexPropertyName) {
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.evalPropertyName = evalPropertyName;
        this.evalEvent = new MapEventBean(new HashMap<String, Object>(), evalEventType);
        this.indexPropertyName = indexPropertyName;
        this.indexEvent = new MapEventBean(new HashMap<String, Object>(), indexEventType);
        this.eventsLambda = new EventBean[streamNumLambda + 2];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }
}
