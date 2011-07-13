package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public abstract class IntervalComputerExprBase implements IntervalComputer {
    private final ExprEvaluator start;
    private final ExprEvaluator finish;

    public IntervalComputerExprBase(IntervalStartEndParameterPair pair) {
        this.start = pair.getStart().getEvaluator();
        this.finish = pair.getEnd().getEvaluator();
    }

    public abstract boolean compute(long left, long leftDuration, long right, long rightDuration, long start, long end);

    public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
        Object startValue = start.evaluate(eventsPerStream, newData, context);
        if (startValue == null) {
            return null;
        }

        Object endValue = finish.evaluate(eventsPerStream, newData, context);
        if (endValue == null) {
            return null;
        }

        long start = toLong(startValue);
        long end = toLong(endValue);

        if (start > end) {
            return compute(left, leftDuration, right, rightDuration, end, start);
        }
        else {
            return compute(left, leftDuration, right, rightDuration, start, end);
        }
    }

    public static long toLong(Object value) {
        double d = ((Number) value).doubleValue();
        return (long) (d * 1000);
    }
}
