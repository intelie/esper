package com.espertech.esper.support.epl;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.client.EventBean;

import java.util.Map;

public class SupportExprEvaluator implements ExprEvaluator
{
    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context)
    {
        return eventsPerStream[0].get("boolPrimitive");
    }

    public Class getType()
    {
        return boolean.class;
    }

    public Map<String, Object> getEventType() {
        return null;
    }                                        
}
