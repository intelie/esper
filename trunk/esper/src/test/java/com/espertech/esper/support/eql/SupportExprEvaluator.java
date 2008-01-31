package com.espertech.esper.support.eql;

import com.espertech.esper.eql.expression.ExprEvaluator;
import com.espertech.esper.event.EventBean;

public class SupportExprEvaluator implements ExprEvaluator
{
    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return eventsPerStream[0].get("boolPrimitive");
    }
}
