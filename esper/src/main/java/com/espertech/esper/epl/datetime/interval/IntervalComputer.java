package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public interface IntervalComputer {
    
    public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context);
}
