package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.datetime.DatetimeMethodEnum;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.type.RelationalOpEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IntervalOpImpl implements IntervalOp {

    private final ExprEvaluator evaluatorTimestamp;
    private ExprEvaluator evaluatorDuration;
    private final Integer streamId;
    private final String timestampPropertyName;

    private final IntervalComputer intervalComputer;

    public IntervalOpImpl(DatetimeMethodEnum method, EventType[] typesPerStream, List<ExprNode> expressions) {

        if (expressions.get(0) instanceof ExprStreamUnderlyingNode) {
            ExprStreamUnderlyingNode und = (ExprStreamUnderlyingNode) expressions.get(0);
            streamId = und.getStreamId();
            EventType type = typesPerStream[streamId];
            timestampPropertyName = type.getTimestampProperty();
            EventPropertyGetter getter = type.getGetter(timestampPropertyName);
            evaluatorTimestamp = new ExprEvaluatorStreamLongProp(streamId, getter);

            if (type.getDurationProperty() != null) {
                EventPropertyGetter getterDuration = type.getGetter(type.getDurationProperty());
                evaluatorDuration = new ExprEvaluatorStreamLongProp(streamId, getterDuration);
            }
        }
        else {
            streamId = null;
            timestampPropertyName = null;
            evaluatorTimestamp = expressions.get(0).getExprEvaluator();
        }

        intervalComputer = IntervalComputerFactory.make(method, expressions);
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

        long paramLong; // TODO this should not be if-then-else
        if (parameter instanceof Long) {
            paramLong = (Long) parameter;
        }
        else if (parameter instanceof Calendar) {
            paramLong = ((Calendar) parameter).getTimeInMillis();
        }
        else {
            paramLong = ((Date) parameter).getTime();
        }

        long paramDuration = 0;
        if (evaluatorDuration != null) {
            Object durationObj = evaluatorDuration.evaluate(eventsPerStream, isNewData, context);
            if (durationObj == null) {
                return null;
            }
            paramDuration = (Long) durationObj;
        }

        return intervalComputer.compute(ts, duration, paramLong, paramDuration, eventsPerStream, isNewData, context);
    }
}
