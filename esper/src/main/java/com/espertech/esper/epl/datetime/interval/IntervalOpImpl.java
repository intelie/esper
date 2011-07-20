/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.datetime.eval.DatetimeMethodEnum;
import com.espertech.esper.epl.datetime.eval.ExprDotNodeFilterAnalyzerDTIntervalDesc;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.type.RelationalOpEnum;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IntervalOpImpl implements IntervalOp {

    private final ExprEvaluator evaluatorTimestamp;

    private Integer parameterStreamNum;
    private String parameterPropertyStart;
    private String parameterPropertyEnd;

    private final IntervalOpEval intervalOpEval;

    public IntervalOpImpl(DatetimeMethodEnum method, String methodNameUse, EventType[] typesPerStream, List<ExprNode> expressions)
        throws ExprValidationException {

        ExprEvaluator evaluatorEndTimestamp = null;
        Class timestampType;

        if (expressions.get(0) instanceof ExprStreamUnderlyingNode) {
            ExprStreamUnderlyingNode und = (ExprStreamUnderlyingNode) expressions.get(0);
            parameterStreamNum = und.getStreamId();
            EventType type = typesPerStream[parameterStreamNum];
            parameterPropertyStart = type.getStartTimestampPropertyName();
            if (parameterPropertyStart == null) {
                throw new ExprValidationException("For date-time method '" + methodNameUse + "' the first parameter is event type '" + type.getName() + "', however no timestamp property has been defined for this event type");
            }

            timestampType = type.getPropertyType(parameterPropertyStart);
            EventPropertyGetter getter = type.getGetter(parameterPropertyStart);
            evaluatorTimestamp = new ExprEvaluatorStreamLongProp(parameterStreamNum, getter);

            if (type.getEndTimestampPropertyName() != null) {
                parameterPropertyEnd = type.getEndTimestampPropertyName();
                EventPropertyGetter getterEndTimestamp = type.getGetter(type.getEndTimestampPropertyName());
                evaluatorEndTimestamp = new ExprEvaluatorStreamLongProp(parameterStreamNum, getterEndTimestamp);
            }
            else {
                parameterPropertyEnd = parameterPropertyStart;
            }
        }
        else {
            evaluatorTimestamp = expressions.get(0).getExprEvaluator();
            timestampType = evaluatorTimestamp.getType();

            if (expressions.get(0) instanceof ExprIdentNode) {
                ExprIdentNode identNode = (ExprIdentNode) expressions.get(0);
                parameterStreamNum = identNode.getStreamId();
                parameterPropertyStart = identNode.getResolvedPropertyName();
                parameterPropertyEnd = parameterPropertyStart;
            }

            if (!JavaClassHelper.isDatetimeClass(evaluatorTimestamp.getType())) {
                throw new ExprValidationException("For date-time method '" + methodNameUse + "' the first parameter expression returns '" + evaluatorTimestamp.getType() + "', however requires a Date, Calendar, Long-type return value or event (with timestamp)");
            }
        }

        IntervalComputer intervalComputer = IntervalComputerFactory.make(method, expressions);

        // evaluation without end timestamp
        if (evaluatorEndTimestamp == null) {
            if (JavaClassHelper.isSubclassOrImplementsInterface(timestampType, Calendar.class)) {
                intervalOpEval = new IntervalOpEvalCal(intervalComputer);
            }
            else if (JavaClassHelper.isSubclassOrImplementsInterface(timestampType, Date.class)) {
                intervalOpEval = new IntervalOpEvalDate(intervalComputer);
            }
            else if (JavaClassHelper.getBoxedType(timestampType) == Long.class) {
                intervalOpEval = new IntervalOpEvalLong(intervalComputer);
            }
            else {
                throw new IllegalArgumentException("Invalid interval first parameter type '" + timestampType + "'");
            }
        }
        else {
            if (JavaClassHelper.isSubclassOrImplementsInterface(timestampType, Calendar.class)) {
                intervalOpEval = new IntervalOpEvalCalWithEnd(intervalComputer, evaluatorEndTimestamp);
            }
            else if (JavaClassHelper.isSubclassOrImplementsInterface(timestampType, Date.class)) {
                intervalOpEval = new IntervalOpEvalDateWithEnd(intervalComputer, evaluatorEndTimestamp);
            }
            else if (JavaClassHelper.getBoxedType(timestampType) == Long.class) {
                intervalOpEval = new IntervalOpEvalLongWithEnd(intervalComputer, evaluatorEndTimestamp);
            }
            else {
                throw new IllegalArgumentException("Invalid interval first parameter type '" + timestampType + "'");
            }
        }
    }

    /**
     * Obtain information used by filter analyzer to handle this dot-method invocation as part of query planning/indexing.
     *
     * @param typesPerStream event types
     * @param currentMethod
     * @param currentParameters
     * @param inputDesc descriptor of what the input to this interval method is
     * */
    public ExprDotNodeFilterAnalyzerDTIntervalDesc getFilterDesc(EventType[] typesPerStream, DatetimeMethodEnum currentMethod, List<ExprNode> currentParameters, ExprDotNodeFilterAnalyzerInput inputDesc) {

        // with intervals is not currently query planned
        if (currentParameters.size() > 1) {
            return null;
        }

        // Get input (target)
        int targetStreamNum;
        String targetPropertyStart;
        String targetPropertyEnd;
        if (inputDesc instanceof ExprDotNodeFilterAnalyzerInputStream) {
            ExprDotNodeFilterAnalyzerInputStream targetStream = (ExprDotNodeFilterAnalyzerInputStream) inputDesc;
            targetStreamNum = targetStream.getStreamNum();
            EventType targetType = typesPerStream[targetStreamNum];
            targetPropertyStart = targetType.getStartTimestampPropertyName();
            targetPropertyEnd = targetType.getEndTimestampPropertyName() != null ? targetType.getEndTimestampPropertyName() : targetPropertyStart;
        }
        else if (inputDesc instanceof ExprDotNodeFilterAnalyzerInputProp) {
            ExprDotNodeFilterAnalyzerInputProp targetStream = (ExprDotNodeFilterAnalyzerInputProp) inputDesc;
            targetStreamNum = targetStream.getStreamNum();
            targetPropertyStart = targetStream.getPropertyName();
            targetPropertyEnd = targetStream.getPropertyName();
        }
        else {
            return null;
        }

        // check parameter info
        if (parameterPropertyStart == null) {
            return null;
        }

        return new ExprDotNodeFilterAnalyzerDTIntervalDesc(currentMethod, typesPerStream,
                targetStreamNum, targetPropertyStart, targetPropertyEnd,
                parameterStreamNum, parameterPropertyStart, parameterPropertyEnd);
    }

    public Object evaluate(long startTs, long endTs, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Object parameter = evaluatorTimestamp.evaluate(eventsPerStream, isNewData, context);
        if (parameter == null) {
            return parameter;
        }

        return intervalOpEval.evaluate(startTs, endTs, parameter, eventsPerStream, isNewData, context);
    }

    public static interface IntervalOpEval {
        public Object evaluate(long startTs, long endTs, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context);
    }

    public abstract static class IntervalOpEvalDateBase implements IntervalOpEval {
        protected final IntervalComputer intervalComputer;

        public IntervalOpEvalDateBase(IntervalComputer intervalComputer) {
            this.intervalComputer = intervalComputer;
        }
    }

    public static class IntervalOpEvalDate extends IntervalOpEvalDateBase {

        public IntervalOpEvalDate(IntervalComputer intervalComputer) {
            super(intervalComputer);
        }

        public Object evaluate(long startTs, long endTs, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            long time = ((Date) parameter).getTime();
            return intervalComputer.compute(startTs, endTs, time, time, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalLong extends IntervalOpEvalDateBase {

        public IntervalOpEvalLong(IntervalComputer intervalComputer) {
            super(intervalComputer);
        }

        public Object evaluate(long startTs, long endTs, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            long time = (Long) parameter;
            return intervalComputer.compute(startTs, endTs, time, time, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalCal extends IntervalOpEvalDateBase {

        public IntervalOpEvalCal(IntervalComputer intervalComputer) {
            super(intervalComputer);
        }

        public Object evaluate(long startTs, long endTs, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            long time = ((Calendar) parameter).getTimeInMillis();
            return intervalComputer.compute(startTs, endTs, time, time, eventsPerStream, isNewData, context);
        }
    }

    public abstract static class IntervalOpEvalDateWithEndBase implements IntervalOpEval {
        protected final IntervalComputer intervalComputer;
        private final ExprEvaluator evaluatorEndTimestamp;

        protected IntervalOpEvalDateWithEndBase(IntervalComputer intervalComputer, ExprEvaluator evaluatorEndTimestamp) {
            this.intervalComputer = intervalComputer;
            this.evaluatorEndTimestamp = evaluatorEndTimestamp;
        }

        public abstract Object evaluate(long startTs, long endTs, Object parameterStartTs, Object parameterEndTs, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context);

        public Object evaluate(long startTs, long endTs, Object parameterStartTs, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            Object paramEndTs = evaluatorEndTimestamp.evaluate(eventsPerStream, isNewData, context);
            if (paramEndTs == null) {
                return null;
            }
            return evaluate(startTs, endTs, parameterStartTs, paramEndTs, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalDateWithEnd extends IntervalOpEvalDateWithEndBase {

        public IntervalOpEvalDateWithEnd(IntervalComputer intervalComputer, ExprEvaluator evaluatorEndTimestamp) {
            super(intervalComputer, evaluatorEndTimestamp);
        }

        public Object evaluate(long startTs, long endTs, Object parameterStartTs, Object parameterEndTs, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(startTs, endTs, ((Date) parameterStartTs).getTime(), ((Date) parameterEndTs).getTime(), eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalLongWithEnd extends IntervalOpEvalDateWithEndBase {

        public IntervalOpEvalLongWithEnd(IntervalComputer intervalComputer, ExprEvaluator evaluatorEndTimestamp) {
            super(intervalComputer, evaluatorEndTimestamp);
        }

        public Object evaluate(long startTs, long endTs, Object parameterStartTs, Object parameterEndTs, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(startTs, endTs, (Long) parameterStartTs, (Long) parameterEndTs, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalCalWithEnd extends IntervalOpEvalDateWithEndBase {

        public IntervalOpEvalCalWithEnd(IntervalComputer intervalComputer, ExprEvaluator evaluatorEndTimestamp) {
            super(intervalComputer, evaluatorEndTimestamp);
        }

        public Object evaluate(long startTs, long endTs, Object parameterStartTs, Object parameterEndTs, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(startTs, endTs, ((Calendar) parameterStartTs).getTimeInMillis(), ((Calendar) parameterEndTs).getTimeInMillis(), eventsPerStream, isNewData, context);
        }
    }
}
