package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprDotNodeFilterAnalyzerDTIntervalDesc;
import com.espertech.esper.epl.expression.ExprDotNodeFilterAnalyzerInput;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public interface IntervalOp {
    public Object evaluate(long ts, long duration, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context);
    public ExprDotNodeFilterAnalyzerDTIntervalDesc getFilterDesc(EventType[] typesPerStream, ExprDotNodeFilterAnalyzerInput inputDesc, String inputPropertyName);
}
