package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.DatetimeMethodEnum;
import com.espertech.esper.epl.expression.*;

import java.util.List;

public class IntervalComputerFactory {

    public final static IntervalComputerBeforeNoParam BEFORE = new IntervalComputerBeforeNoParam();

    public static IntervalComputer make(DatetimeMethodEnum method, List<ExprNode> expressions) {
        ExprOptionalConstant[] params = getParameters(expressions);

        if (method == DatetimeMethodEnum.BEFORE) {
            if (params.length == 0) {
                return new IntervalComputerBeforeNoParam();
            }
            IntervalStartEndParameterPair pair = IntervalStartEndParameterPair.fromParamsWithLongMaxEnd(params);
            if (pair.isConstant()) {
                return new IntervalComputerConstantBefore(pair);
            }
            return new IntervalComputerBeforeWithDeltaExpr(pair);
        }
        else if (method == DatetimeMethodEnum.AFTER) {
            if (params.length == 0) {
                return new IntervalComputerAfterNoParam();
            }
            IntervalStartEndParameterPair pair = IntervalStartEndParameterPair.fromParamsWithLongMaxEnd(params);
            if (pair.isConstant()) {
                return new IntervalComputerConstantAfter(pair);
            }
            return new IntervalComputerAfterWithDeltaExpr(pair);
        }
        else if (method == DatetimeMethodEnum.COINCIDES) {
            if (params.length == 0) {
                return new IntervalComputerCoincidesNoParam();
            }
            IntervalStartEndParameterPair pair = IntervalStartEndParameterPair.fromParamsWithSameEnd(params);
            if (pair.isConstant()) {
                return new IntervalComputerConstantCoincides(pair);
            }
            return new IntervalComputerCoincidesWithDeltaExpr(pair);
        }
        else if (method == DatetimeMethodEnum.DURING || method == DatetimeMethodEnum.INCLUDES) {
            if (params.length == 0) {
                if (method == DatetimeMethodEnum.DURING) {
                    return new IntervalComputerDuringNoParam();
                }
                return new IntervalComputerIncludesNoParam();
            }
            if (params.length == 1) {
                return new IntervalComputerDuringAndIncludesThreshold(method == DatetimeMethodEnum.DURING, params[0].getEvaluator());
            }
            if (params.length == 2) {
                return new IntervalComputerDuringAndIncludesMinMax(method == DatetimeMethodEnum.DURING, params[0].getEvaluator(), params[1].getEvaluator());
            }
            return new IntervalComputerDuringMinMaxStartEnd(method == DatetimeMethodEnum.DURING, params);
        }
        else if (method == DatetimeMethodEnum.FINISHES) {
            if (params.length == 0) {
                return new IntervalComputerFinishesNoParam();
            }
            return new IntervalComputerFinishesThreshold(params[0].getEvaluator());
        }
        else if (method == DatetimeMethodEnum.FINISHEDBY) {
            if (params.length == 0) {
                return new IntervalComputerFinishedByNoParam();
            }
            return new IntervalComputerFinishedByThreshold(params[0].getEvaluator());
        }
        else if (method == DatetimeMethodEnum.MEETS) {
            if (params.length == 0) {
                return new IntervalComputerMeetsNoParam();
            }
            return new IntervalComputerMeetsThreshold(params[0].getEvaluator());
        }
        else if (method == DatetimeMethodEnum.METBY) {
            if (params.length == 0) {
                return new IntervalComputerMetByNoParam();
            }
            return new IntervalComputerMetByThreshold(params[0].getEvaluator());
        }
        else if (method == DatetimeMethodEnum.OVERLAPS || method == DatetimeMethodEnum.OVERLAPPEDBY) {
            if (params.length == 0) {
                if (method == DatetimeMethodEnum.OVERLAPS) {
                    return new IntervalComputerOverlapsNoParam();
                }
                return new IntervalComputerOverlappedByNoParam();
            }
            if (params.length == 1) {
                return new IntervalComputerOverlapsAndByThreshold(method == DatetimeMethodEnum.OVERLAPS, params[0].getEvaluator());
            }
            return new IntervalComputerOverlapsAndByMinMax(method == DatetimeMethodEnum.OVERLAPS, params[0].getEvaluator(), params[1].getEvaluator());
        }
        else if (method == DatetimeMethodEnum.STARTS) {
            if (params.length == 0) {
                return new IntervalComputerStartsNoParam();
            }
            return new IntervalComputerStartsThreshold(params[0].getEvaluator());
        }
        else if (method == DatetimeMethodEnum.STARTEDBY) {
            if (params.length == 0) {
                return new IntervalComputerStartedByNoParam();
            }
            return new IntervalComputerStartedByThreshold(params[0].getEvaluator());
        }
        throw new IllegalArgumentException("Unknown datetime method '" + method + "'");
    }

    private static ExprOptionalConstant[] getParameters(List<ExprNode> expressions) {
        ExprOptionalConstant[] params = new ExprOptionalConstant[expressions.size() - 1];
        for (int i = 1; i < expressions.size(); i++) {
            params[i - 1] = getExprOrConstant(expressions.get(i));
        }
        return params;
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

        public IntervalComputerConstantAfter(IntervalStartEndParameterPair pair) {
            super(pair);
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

        public IntervalComputerAfterWithDeltaExpr(IntervalStartEndParameterPair pair) {
            super(pair);
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

        public IntervalComputerConstantBefore(IntervalStartEndParameterPair pair) {
            super(pair);
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

        public IntervalComputerBeforeWithDeltaExpr(IntervalStartEndParameterPair pair) {
            super(pair);
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

        public IntervalComputerConstantCoincides(IntervalStartEndParameterPair pair) {
            super(pair);
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

        public IntervalComputerCoincidesWithDeltaExpr(IntervalStartEndParameterPair pair) {
            super(pair);
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

    /**
     * During And Includes.
     */
    public static class IntervalComputerDuringNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return right < left && ((left + leftDuration) < (right + rightDuration));
        }
    }

    public static class IntervalComputerIncludesNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return left < right &&  ((right + rightDuration) < (left + leftDuration));
        }
    }

    public static class IntervalComputerDuringAndIncludesThreshold implements IntervalComputer {

        private final boolean during;
        private final ExprEvaluator threshold;

        public IntervalComputerDuringAndIncludesThreshold(boolean during, ExprEvaluator threshold) {
            this.during = during;
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);

            if (during) {
                long deltaStart = left - right;
                if (deltaStart <= 0 || deltaStart > threshold) {
                    return false;
                }

                long deltaEnd = (right + rightDuration) - (left + leftDuration);
                return !(deltaEnd <= 0 || deltaEnd > threshold);
            }
            else {
                long deltaStart = right - left;
                if (deltaStart <= 0 || deltaStart > threshold) {
                    return false;
                }

                long deltaEnd = (left + leftDuration) - (right + rightDuration);
                return !(deltaEnd <= 0 || deltaEnd > threshold);
            }
        }
    }

    public static class IntervalComputerDuringAndIncludesMinMax implements IntervalComputer {

        private final boolean during;
        private final ExprEvaluator minEval;
        private final ExprEvaluator maxEval;

        public IntervalComputerDuringAndIncludesMinMax(boolean during, ExprEvaluator minEval, ExprEvaluator maxEval) {
            this.during = during;
            this.minEval = minEval;
            this.maxEval = maxEval;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object minObject = minEval.evaluate(eventsPerStream, newData, context);
            if (minObject == null) {
                return null;
            }
            long min = IntervalComputerExprBase.toLong(minObject);

            Object maxObject = maxEval.evaluate(eventsPerStream, newData, context);
            if (maxObject == null) {
                return null;
            }
            long max = IntervalComputerExprBase.toLong(maxObject);

            if (during) {
                return computeInternalDuring(left, leftDuration, right, rightDuration, min, max, min, max);
            }
            else {
                return computeInternalIncludes(left, leftDuration, right, rightDuration, min, max, min, max);
            }
        }

        public static boolean computeInternalDuring(long left, long leftDuration, long right, long rightDuration,
                                        long startMin, long startMax, long endMin, long endMax) {
            if (startMin <= 0) {
                startMin = 1;
            }
            long deltaStart = left - right;
            if (deltaStart < startMin || deltaStart > startMax) {
                return false;
            }

            long deltaEnd = (right + rightDuration) - (left + leftDuration);
            return !(deltaEnd < endMin || deltaEnd > endMax);
        }

        public static boolean computeInternalIncludes(long left, long leftDuration, long right, long rightDuration,
                                        long startMin, long startMax, long endMin, long endMax) {
            if (startMin <= 0) {
                startMin = 1;
            }
            long deltaStart = right - left;
            if (deltaStart < startMin || deltaStart > startMax) {
                return false;
            }

            long deltaEnd = (left + leftDuration) - (right + rightDuration);
            return !(deltaEnd < endMin || deltaEnd > endMax);
        }
    }

    public static class IntervalComputerDuringMinMaxStartEnd implements IntervalComputer {

        private final boolean during;
        private final ExprEvaluator minStartEval;
        private final ExprEvaluator maxStartEval;
        private final ExprEvaluator minEndEval;
        private final ExprEvaluator maxEndEval;

        public IntervalComputerDuringMinMaxStartEnd(boolean during, ExprOptionalConstant[] params) {
            this.during = during;
            minStartEval = params[0].getEvaluator();
            maxStartEval = params[1].getEvaluator();
            minEndEval = params[2].getEvaluator();
            maxEndEval = params[3].getEvaluator();
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object minStartObject = minStartEval.evaluate(eventsPerStream, newData, context);
            if (minStartObject == null) {
                return null;
            }

            Object maxStartObject = maxStartEval.evaluate(eventsPerStream, newData, context);
            if (maxStartObject == null) {
                return null;
            }

            Object minEndObject = minEndEval.evaluate(eventsPerStream, newData, context);
            if (minEndObject == null) {
                return null;
            }

            Object maxEndObject = maxEndEval.evaluate(eventsPerStream, newData, context);
            if (maxEndObject == null) {
                return null;
            }

            long minStart = IntervalComputerExprBase.toLong(minStartObject);
            long maxStart = IntervalComputerExprBase.toLong(maxStartObject);
            long minEnd = IntervalComputerExprBase.toLong(minEndObject);
            long maxEnd = IntervalComputerExprBase.toLong(maxEndObject);

            if (during) {
                return IntervalComputerDuringAndIncludesMinMax.computeInternalDuring(left, leftDuration, right, rightDuration, minStart, maxStart, minEnd, maxEnd);
            }
            else {
                return IntervalComputerDuringAndIncludesMinMax.computeInternalIncludes(left, leftDuration, right, rightDuration, minStart, maxStart, minEnd, maxEnd);
            }
        }
    }

    /**
     * Finishes.
     */
    public static class IntervalComputerFinishesNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return right < left && ((left + leftDuration) == (right + rightDuration));
        }
    }

    public static class IntervalComputerFinishesThreshold implements IntervalComputer {

        private final ExprEvaluator threshold;

        public IntervalComputerFinishesThreshold(ExprEvaluator threshold) {
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);

            if (right >= left) {
                return false;
            }
            long delta = Math.abs((left + leftDuration) - (right + rightDuration));
            return delta <= threshold;
        }
    }

    /**
     * Finishes-By.
     */
    public static class IntervalComputerFinishedByNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return left < right && ((left + leftDuration) == (right + rightDuration));
        }
    }

    public static class IntervalComputerFinishedByThreshold implements IntervalComputer {

        private final ExprEvaluator threshold;

        public IntervalComputerFinishedByThreshold(ExprEvaluator threshold) {
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);

            if (left >= right) {
                return false;
            }
            long delta = Math.abs((left + leftDuration) - (right + rightDuration));
            return delta <= threshold;
        }
    }

    /**
     * Meets.
     */
    public static class IntervalComputerMeetsNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return (left + leftDuration) == right;
        }
    }

    public static class IntervalComputerMeetsThreshold implements IntervalComputer {

        private final ExprEvaluator threshold;

        public IntervalComputerMeetsThreshold(ExprEvaluator threshold) {
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);

            long delta = Math.abs(right - (left + leftDuration));
            return delta <= threshold;
        }
    }

    /**
     * Met-By.
     */
    public static class IntervalComputerMetByNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return (right + rightDuration) == left;
        }
    }

    public static class IntervalComputerMetByThreshold implements IntervalComputer {

        private final ExprEvaluator threshold;

        public IntervalComputerMetByThreshold(ExprEvaluator threshold) {
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);

            long delta = Math.abs(left - (right + rightDuration));
            return delta <= threshold;
        }
    }

    /**
     * Overlaps.
     */
    public static class IntervalComputerOverlapsNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return (left < right) &&
                   (right < (left + leftDuration)) &&
                   ((left + leftDuration) < (right + rightDuration));
        }
    }

    public static class IntervalComputerOverlapsAndByThreshold implements IntervalComputer {

        private final boolean overlaps;
        private final ExprEvaluator threshold;

        public IntervalComputerOverlapsAndByThreshold(boolean overlaps, ExprEvaluator threshold) {
            this.overlaps = overlaps;
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);

            if (overlaps) {
                return computeInternalOverlaps(left, leftDuration, right, rightDuration, 0, threshold);
            }
            else {
                return computeInternalOverlaps(right, rightDuration, left, leftDuration, 0, threshold);
            }
        }

        public static boolean computeInternalOverlaps(long left, long leftDuration, long right, long rightDuration, long min, long max) {
            boolean match = ((left < right) &&
                   (right < (left + leftDuration)) &&
                   ((left + leftDuration) < (right + rightDuration)));
            if (!match) {
                return false;
            }
            long delta = (left + leftDuration) - right;
            return min <= delta && delta <= max;
        }
    }

    public static class IntervalComputerOverlapsAndByMinMax implements IntervalComputer {

        private final boolean overlaps;
        private final ExprEvaluator minEval;
        private final ExprEvaluator maxEval;

        public IntervalComputerOverlapsAndByMinMax(boolean overlaps, ExprEvaluator minEval, ExprEvaluator maxEval) {
            this.overlaps = overlaps;
            this.minEval = minEval;
            this.maxEval = maxEval;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object minValue = minEval.evaluate(eventsPerStream, newData, context);
            if (minValue == null) {
                return null;
            }
            Object maxValue = maxEval.evaluate(eventsPerStream, newData, context);
            if (maxValue == null) {
                return null;
            }

            long minThreshold = IntervalComputerExprBase.toLong(minValue);
            long maxThreshold = IntervalComputerExprBase.toLong(maxValue);

            if (overlaps) {
                return IntervalComputerOverlapsAndByThreshold.computeInternalOverlaps(left, leftDuration, right, rightDuration, minThreshold, maxThreshold);
            }
            else {
                return IntervalComputerOverlapsAndByThreshold.computeInternalOverlaps(right, rightDuration, left, leftDuration, minThreshold, maxThreshold);
            }
        }
    }

    /**
     * OverlappedBy.
     */
    public static class IntervalComputerOverlappedByNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return (right < left) &&
                   (left < (right + rightDuration)) &&
                   ((right + rightDuration) < (left + leftDuration));
        }
    }

    /**
     * Starts.
     */
    public static class IntervalComputerStartsNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return (left == right) && ((left + leftDuration) < (right + rightDuration));
        }
    }

    public static class IntervalComputerStartsThreshold implements IntervalComputer {

        private final ExprEvaluator threshold;

        public IntervalComputerStartsThreshold(ExprEvaluator threshold) {
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);
            long delta = Math.abs(left - right);
            return delta <= threshold && ((left + leftDuration) < (right + rightDuration));
        }
    }

    /**
     * Started-by.
     */
    public static class IntervalComputerStartedByNoParam implements IntervalComputer {

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {
            return (left == right) && ((left + leftDuration) > (right + rightDuration));
        }
    }

    public static class IntervalComputerStartedByThreshold implements IntervalComputer {

        private final ExprEvaluator threshold;

        public IntervalComputerStartedByThreshold(ExprEvaluator threshold) {
            this.threshold = threshold;
        }

        public Boolean compute(long left, long leftDuration, long right, long rightDuration, EventBean[] eventsPerStream, boolean newData, ExprEvaluatorContext context) {

            Object thresholdValue = threshold.evaluate(eventsPerStream, newData, context);
            if (thresholdValue == null) {
                return null;
            }

            long threshold = IntervalComputerExprBase.toLong(thresholdValue);
            long delta = Math.abs(left - right);
            return delta <= threshold && ((left + leftDuration) > (right + rightDuration));
        }
    }
}
