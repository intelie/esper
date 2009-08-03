package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprTimePeriod;

public class MatchRecognizeInterval
{
    private final ExprTimePeriod timePeriodExpr;
    private final long msec;

    public MatchRecognizeInterval(ExprTimePeriod timePeriodExpr)
    {
        this.timePeriodExpr = timePeriodExpr;
        double seconds = (Double) timePeriodExpr.evaluate(null, true);
        msec = (long) (seconds * 1000);
    }

    public ExprTimePeriod getTimePeriodExpr()
    {
        return timePeriodExpr;
    }

    public long getMSec()
    {
        return msec;
    }
}