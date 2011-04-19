package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;

public class EnumEvalBase {

    protected ExprEvaluator innerExpression;
    protected int streamNumLambda;
    protected EventBean[] eventsLambda;

    public EnumEvalBase(ExprEvaluator innerExpression, int streamCountIncoming) {
        this(streamCountIncoming);
        this.innerExpression = innerExpression;
    }

    public EnumEvalBase(int streamCountIncoming) {
        this.streamNumLambda = streamCountIncoming;
        this.eventsLambda = new EventBean[streamCountIncoming + 1];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }

    public ExprEvaluator getInnerExpression() {
        return innerExpression;
    }
}
