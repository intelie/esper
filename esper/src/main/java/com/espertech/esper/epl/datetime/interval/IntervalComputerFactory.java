package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.DatetimeMethodEnum;
import com.espertech.esper.epl.expression.*;

import javax.xml.datatype.Duration;
import java.util.List;

public class IntervalComputerFactory {

    public final static IntervalComputerBeforeNoParam BEFORE = new IntervalComputerBeforeNoParam();

    public static IntervalComputer make(DatetimeMethodEnum method, List<ExprNode> expressions) {
        ExprOptionalConstant start;
        ExprOptionalConstant end;
        if (expressions.size() == 1) {
            start = null;
            end = null;
        }
        else if (expressions.size() == 2) {
            start = getExprOrConstant(expressions.get(1));
            end = method == DatetimeMethodEnum.COINCIDES ? start : ExprOptionalConstant.make(Long.MAX_VALUE);
        }
        else if (expressions.size() == 3) {
            start = getExprOrConstant(expressions.get(1));
            end = getExprOrConstant(expressions.get(2));
        }
        else {
            throw new IllegalArgumentException("Number of parameter expression is more then 3 parameters");
        }

        if (method == DatetimeMethodEnum.BEFORE) {
            if (start == null) {
                return new IntervalComputerBeforeNoParam();
            }
            if (start.getOptionalConstant() != null && end.getOptionalConstant() != null) {
                return new IntervalComputerConstantBefore(start.getOptionalConstant(), end.getOptionalConstant());
            }
            return new IntervalComputerBeforeWithDeltaExpr(start.getEvaluator(), end.getEvaluator());
        }
        else if (method == DatetimeMethodEnum.AFTER) {
            if (start == null) {
                return new IntervalComputerAfterNoParam();
            }
            if (start.getOptionalConstant() != null && end.getOptionalConstant() != null) {
                return new IntervalComputerConstantAfter(start.getOptionalConstant(), end.getOptionalConstant());
            }
            return new IntervalComputerAfterWithDeltaExpr(start.getEvaluator(), end.getEvaluator());
        }
        else if (method == DatetimeMethodEnum.COINCIDES) {
            if (start == null) {
                return new IntervalComputerCoincidesNoParam();
            }
            if (start.getOptionalConstant() != null && end.getOptionalConstant() != null) {
                return new IntervalComputerConstantCoincides(start.getOptionalConstant(), end.getOptionalConstant());
            }
            return new IntervalComputerCoincidesWithDeltaExpr(start.getEvaluator(), end.getEvaluator());
        }
        throw new IllegalArgumentException("Unknown datetime method '" + method + "'");
    }

    private static ExprOptionalConstant getExprOrConstant(ExprNode exprNode) {
        if (exprNode instanceof ExprTimePeriod) {
            Long constant = null;
            if (exprNode.isConstantResult()) {
                double sec = (Double) exprNode.getExprEvaluator().evaluate(null, true, null);
                constant = (long)(sec * 1000L);
            }
            return new ExprOptionalConstant(exprNode.getExprEvaluator(), constant);
        }
        else if (exprNode instanceof ExprConstantNode) {
            ExprConstantNode constantNode = (ExprConstantNode) exprNode;
            return new ExprOptionalConstant(constantNode.getExprEvaluator(), ((Number)constantNode.getValue()).longValue());
        }
        else {
            return new ExprOptionalConstant(exprNode.getExprEvaluator(), null);
        }
    }

    /**
     * After.
     */
    public static class IntervalComputerConstantAfter extends IntervalComputerConstantBase implements IntervalComputer {

        public IntervalComputerConstantAfter(long start, long end) {
            super(start, end);
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return computeInternal(left, leftDuration, right, rightDuration, start, end);
        }

        public static Boolean computeInternal(long left, long leftDuration, long right, long rightDuration, long start, long end) {
            long delta = left - (right + rightDuration);
            return start <= delta && delta <= end;
        }
    }

    public static class IntervalComputerAfterWithDeltaExpr extends IntervalComputerExprBase {

        public IntervalComputerAfterWithDeltaExpr(ExprEvaluator start, ExprEvaluator finish) {
            super(start, finish);
        }

        public boolean compute(long left, long leftDuration, long right, long rightDuration, long start, long end) {
            return IntervalComputerConstantAfter.computeInternal(left, leftDuration, right, rightDuration, start, end);
        }
    }

    public static class IntervalComputerAfterNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return left > (right + rightDuration);
        }
    }

    /**
     * Before.
     */
    public static class IntervalComputerConstantBefore extends IntervalComputerConstantBase implements IntervalComputer {

        public IntervalComputerConstantBefore(long start, long end) {
            super(start, end);
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return computeInternal(left, leftDuration, right, start, end);
        }

        public static Boolean computeInternal(long left, long leftDuration, long right, long start, long end) {
            long delta = right - (left + leftDuration);
            return start <= delta && delta <= end;
        }
    }

    public static class IntervalComputerBeforeWithDeltaExpr extends IntervalComputerExprBase {

        public IntervalComputerBeforeWithDeltaExpr(ExprEvaluator start, ExprEvaluator finish) {
            super(start, finish);
        }

        public boolean compute(long left, long leftDuration, long right, long rightDuration, long start, long end) {
            return IntervalComputerConstantBefore.computeInternal(left, leftDuration, right, start, end);
        }
    }

    public static class IntervalComputerBeforeNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return (left + leftDuration) < right;
        }
    }

    /**
     * Coincides.
     */
    public static class IntervalComputerConstantCoincides extends IntervalComputerConstantBase implements IntervalComputer {

        public IntervalComputerConstantCoincides(long startThreshold, long endThreshold) {
            super(startThreshold, endThreshold);
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return computeInternal(left, leftDuration, right, rightDuration, start, end);
        }

        public static Boolean computeInternal(long left, long leftDuration, long right, long rightDuration, long startThreshold, long endThreshold) {
            return Math.abs(left - right) <= startThreshold &&
                   Math.abs((left + leftDuration) - (right + rightDuration)) <= endThreshold;
        }
    }

    public static class IntervalComputerCoincidesWithDeltaExpr extends IntervalComputerExprBase {

        public IntervalComputerCoincidesWithDeltaExpr(ExprEvaluator startThreshold, ExprEvaluator endThreshold) {
            super(startThreshold, endThreshold);
        }

        public boolean compute(long left, long leftDuration, long right, long rightDuration, long startThreshold, long endThreshold) {
            return IntervalComputerConstantCoincides.computeInternal(left, leftDuration, right, rightDuration, startThreshold, endThreshold);
        }
    }

    public static class IntervalComputerCoincidesNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return left == right && ((left + leftDuration) == (right + rightDuration));
        }
    }
}
