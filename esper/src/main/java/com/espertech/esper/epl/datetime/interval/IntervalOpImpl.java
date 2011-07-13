package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.datetime.DatetimeMethodEnum;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.type.RelationalOpEnum;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IntervalOpImpl implements IntervalOp {

    private final ExprEvaluator evaluatorTimestamp;
    private final Integer streamId;
    private final String timestampPropertyName;
    private final IntervalOpEval intervalOpEval;

    public IntervalOpImpl(DatetimeMethodEnum method, String methodNameUse, EventType[] typesPerStream, List<ExprNode> expressions)
        throws ExprValidationException {

        ExprEvaluator evaluatorDuration = null;
        Class timestampType;

        if (expressions.get(0) instanceof ExprStreamUnderlyingNode) {
            ExprStreamUnderlyingNode und = (ExprStreamUnderlyingNode) expressions.get(0);
            streamId = und.getStreamId();
            EventType type = typesPerStream[streamId];
            timestampPropertyName = type.getTimestampPropertyName();
            if (timestampPropertyName == null) {
                throw new ExprValidationException("For date-time method '" + methodNameUse + "' the first parameter is event type '" + type.getName() + "', however no timestamp property has been defined for this event type");
            }

            timestampType = type.getPropertyType(timestampPropertyName);
            EventPropertyGetter getter = type.getGetter(timestampPropertyName);
            evaluatorTimestamp = new ExprEvaluatorStreamLongProp(streamId, getter);

            if (type.getDurationPropertyName() != null) {
                EventPropertyGetter getterDuration = type.getGetter(type.getDurationPropertyName());
                evaluatorDuration = new ExprEvaluatorStreamLongProp(streamId, getterDuration);
            }
        }
        else {
            streamId = null;
            timestampPropertyName = null;
            evaluatorTimestamp = expressions.get(0).getExprEvaluator();
            timestampType = evaluatorTimestamp.getType();

            if (!JavaClassHelper.isDatetimeClass(evaluatorTimestamp.getType())) {
                throw new ExprValidationException("For date-time method '" + methodNameUse + "' the first parameter expression returns '" + evaluatorTimestamp.getType() + "', however requires a Date, Calendar, Long-type return value or event (with timestamp)");
            }
        }

        IntervalComputer intervalComputer = IntervalComputerFactory.make(method, expressions);

        // evaluation without duration
        if (evaluatorDuration == null) {
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
                intervalOpEval = new IntervalOpEvalCalDuration(intervalComputer, evaluatorDuration);
            }
            else if (JavaClassHelper.isSubclassOrImplementsInterface(timestampType, Date.class)) {
                intervalOpEval = new IntervalOpEvalDateDuration(intervalComputer, evaluatorDuration);
            }
            else if (JavaClassHelper.getBoxedType(timestampType) == Long.class) {
                intervalOpEval = new IntervalOpEvalLongDuration(intervalComputer, evaluatorDuration);
            }
            else {
                throw new IllegalArgumentException("Invalid interval first parameter type '" + timestampType + "'");
            }
        }
    }

    public ExprDotNodeFilterAnalyzerDTIntervalDesc getFilterDesc(EventType[] typesPerStream, ExprDotNodeFilterAnalyzerInput inputDesc, String inputPropertyName) {
        if ((!(inputDesc instanceof ExprDotNodeFilterAnalyzerInputStream)) || inputPropertyName == null || streamId == null) {
            return null;
        }
        ExprDotNodeFilterAnalyzerInputStream inputStream = (ExprDotNodeFilterAnalyzerInputStream) inputDesc;
        ExprIdentNode inputIdentNode = new ExprIdentNodeImpl(typesPerStream[inputStream.getStreamNum()], inputPropertyName, inputStream.getStreamNum());
        ExprIdentNode paramIdentNode = new ExprIdentNodeImpl(typesPerStream[streamId], timestampPropertyName, streamId);
        return new ExprDotNodeFilterAnalyzerDTIntervalDesc(DatetimeMethodEnum.BEFORE,
                inputStream.getStreamNum(), inputPropertyName, inputIdentNode,
                streamId, timestampPropertyName, paramIdentNode, RelationalOpEnum.LT
                );
    }

    public Object evaluate(long ts, long duration, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Object parameter = evaluatorTimestamp.evaluate(eventsPerStream, isNewData, context);
        if (parameter == null) {
            return parameter;
        }

        return intervalOpEval.evaluate(ts, duration, parameter, eventsPerStream, isNewData, context);
    }

    public static interface IntervalOpEval {
        public Object evaluate(long ts, long duration, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context);
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

        public Object evaluate(long ts, long duration, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(ts, duration, ((Date) parameter).getTime(), 0, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalLong extends IntervalOpEvalDateBase {

        public IntervalOpEvalLong(IntervalComputer intervalComputer) {
            super(intervalComputer);
        }

        public Object evaluate(long ts, long duration, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(ts, duration, (Long) parameter, 0, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalCal extends IntervalOpEvalDateBase {

        public IntervalOpEvalCal(IntervalComputer intervalComputer) {
            super(intervalComputer);
        }

        public Object evaluate(long ts, long duration, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(ts, duration, ((Calendar) parameter).getTimeInMillis(), 0, eventsPerStream, isNewData, context);
        }
    }

    public abstract static class IntervalOpEvalDateDurationBase implements IntervalOpEval {
        protected final IntervalComputer intervalComputer;
        private final ExprEvaluator evaluatorDuration;

        protected IntervalOpEvalDateDurationBase(IntervalComputer intervalComputer, ExprEvaluator evaluatorDuration) {
            this.intervalComputer = intervalComputer;
            this.evaluatorDuration = evaluatorDuration;
        }

        public abstract Object evaluate(long ts, long duration, Object parameter, long durationParameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context);

        public Object evaluate(long ts, long duration, Object parameter, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            Object durationObj = evaluatorDuration.evaluate(eventsPerStream, isNewData, context);
            if (durationObj == null) {
                return null;
            }
            long paramDuration = (Long) durationObj;
            return evaluate(ts, duration, parameter, paramDuration, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalDateDuration extends IntervalOpEvalDateDurationBase {

        public IntervalOpEvalDateDuration(IntervalComputer intervalComputer, ExprEvaluator evaluatorDuration) {
            super(intervalComputer, evaluatorDuration);
        }

        public Object evaluate(long ts, long duration, Object parameter, long paramDuration, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(ts, duration, ((Date) parameter).getTime(), paramDuration, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalLongDuration extends IntervalOpEvalDateDurationBase {

        public IntervalOpEvalLongDuration(IntervalComputer intervalComputer, ExprEvaluator evaluatorDuration) {
            super(intervalComputer, evaluatorDuration);
        }

        public Object evaluate(long ts, long duration, Object parameter, long paramDuration, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(ts, duration, (Long) parameter, paramDuration, eventsPerStream, isNewData, context);
        }
    }

    public static class IntervalOpEvalCalDuration extends IntervalOpEvalDateDurationBase {

        public IntervalOpEvalCalDuration(IntervalComputer intervalComputer, ExprEvaluator evaluatorDuration) {
            super(intervalComputer, evaluatorDuration);
        }

        public Object evaluate(long ts, long duration, Object parameter, long paramDuration, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
            return intervalComputer.compute(ts, duration, ((Calendar) parameter).getTimeInMillis(), paramDuration, eventsPerStream, isNewData, context);
        }
    }
}
