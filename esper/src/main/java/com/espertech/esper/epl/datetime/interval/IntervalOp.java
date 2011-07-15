package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.datetime.eval.DatetimeMethodEnum;
import com.espertech.esper.epl.datetime.eval.ExprDotNodeFilterAnalyzerDTIntervalDesc;
import com.espertech.esper.epl.expression.ExprDotNodeFilterAnalyzerInput;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;

public interface IntervalOp {
    public Object evaluate(long startTs, long endTs, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context);
    public ExprDotNodeFilterAnalyzerDTIntervalDesc getFilterDesc(EventType[] typesPerStream, DatetimeMethodEnum currentMethod, List<ExprNode> currentParameters, ExprDotNodeFilterAnalyzerInput inputDesc);
}
