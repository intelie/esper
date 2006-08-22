package net.esper.support.eql;

import net.esper.eql.expression.ExprEvaluator;
import net.esper.event.EventBean;

public class SupportExprEvaluator implements ExprEvaluator
{
    public Object evaluate(EventBean[] eventsPerStream)
    {
        return eventsPerStream[0].get("boolPrimitive");
    }
}
