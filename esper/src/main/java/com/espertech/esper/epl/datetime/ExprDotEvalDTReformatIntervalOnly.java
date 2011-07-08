package com.espertech.esper.epl.datetime;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.datetime.interval.IntervalOp;
import com.espertech.esper.epl.datetime.reformatop.ReformatOp;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;

public class ExprDotEvalDTReformatIntervalOnly implements ExprDotEval
{
    private static final Log log = LogFactory.getLog(ExprDotEvalDTReformatIntervalOnly.class);

    private final String methodName;
    private final ReformatOp reformatOp;
    private final IntervalOp intervalOp;

    // TODO interface instead of all the if-then-else and dynamic getter-get, add null checking
    public ExprDotEvalDTReformatIntervalOnly(String methodName, ReformatOp reformatOp, IntervalOp intervalOp) {
        this.methodName = methodName;
        this.reformatOp = reformatOp;
        this.intervalOp = intervalOp;
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        if (target instanceof Calendar) {
            if (intervalOp != null) {
                return intervalOp.evaluate(((Calendar) target).getTimeInMillis(), 0, eventsPerStream, isNewData, exprEvaluatorContext);
            }
            return reformatOp.evaluate((Calendar) target);
        }
        else if (target instanceof Date) {
            if (intervalOp != null) {
                return intervalOp.evaluate(((Date) target).getTime(), 0, eventsPerStream, isNewData, exprEvaluatorContext);
            }
            return reformatOp.evaluate((Date) target);
        }
        else if (target instanceof Long) {
            if (intervalOp != null) {
                return intervalOp.evaluate((Long) target, 0, eventsPerStream, isNewData, exprEvaluatorContext);
            }
            return reformatOp.evaluate((Long) target);
        }
        else if (target instanceof EventBean) {
            EventBean bean = ((EventBean) target);
            EventPropertyGetter getter = bean.getEventType().getGetter(bean.getEventType().getTimestampProperty());
            Object timestamp = getter.get(bean);
            Long timestampLong = (Long) timestamp;

            long duration = 0;
            EventPropertyGetter getterDuration = bean.getEventType().getGetter(bean.getEventType().getDurationProperty());
            Object durationObject = getterDuration.get(bean);
            duration = (Long) durationObject;
            if (intervalOp != null) {
                return intervalOp.evaluate(timestampLong, duration, eventsPerStream, isNewData, exprEvaluatorContext);
            }
            return reformatOp.evaluate(timestampLong);
        }
        else {
            log.warn("Date-time method '" + methodName + "' received non-datetime input class " + target.getClass().getName());
            return null;
        }
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        if (intervalOp != null) {
            return ExprDotEvalTypeInfo.scalarOrUnderlying(Boolean.class);
        }
        return ExprDotEvalTypeInfo.scalarOrUnderlying(reformatOp.getReturnType());
    }
}
