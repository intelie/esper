package com.espertech.esper.support.epl;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.event.EventBean;

public class SupportExprEvaluator implements ExprEvaluator
{
    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return eventsPerStream[0].get("boolPrimitive");
    }
}
