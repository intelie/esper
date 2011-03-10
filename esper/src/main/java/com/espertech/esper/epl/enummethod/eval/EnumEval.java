package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public interface EnumEval {

    public EventBean[] getEventsPrototype();
    public Object evaluateLambda(Collection<EventBean> target, boolean isNewData, ExprEvaluatorContext context);
}
