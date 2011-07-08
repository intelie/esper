package com.espertech.esper.epl.datetime;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.calop.CalendarOp;
import com.espertech.esper.epl.datetime.interval.IntervalOp;
import com.espertech.esper.epl.datetime.reformatop.ReformatOp;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExprDotEvalDTCalOpsReformatInterval implements ExprDotEval {
    private static final Log log = LogFactory.getLog(ExprDotEvalDTCalOpsReformatInterval.class);

    private final String methodName;
    private final ReformatOp reformatOp;
    private final IntervalOp intervalOp;
    private final List<CalendarOp> calendarOps;

    public ExprDotEvalDTCalOpsReformatInterval(String methodName, ReformatOp reformatOp, IntervalOp intervalOp, List<CalendarOp> calendarOps) {
        this.methodName = methodName;
        this.reformatOp = reformatOp;
        this.intervalOp = intervalOp;
        this.calendarOps = calendarOps;
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        if (target instanceof Calendar) {
            Calendar cal = (Calendar) ((Calendar) target).clone();
            evaluateCalOps(cal, eventsPerStream, isNewData, exprEvaluatorContext);
            if (reformatOp != null) {
                return reformatOp.evaluate(cal);
            }
            return intervalOp.evaluate(cal.getTimeInMillis(), 0, eventsPerStream, isNewData, exprEvaluatorContext);
        }
        else if (target instanceof Date) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(((Date) target).getTime());
            evaluateCalOps(cal, eventsPerStream, isNewData, exprEvaluatorContext);
            if (reformatOp != null) {
                return reformatOp.evaluate(cal);
            }
            return intervalOp.evaluate(cal.getTimeInMillis(), 0, eventsPerStream, isNewData, exprEvaluatorContext);
        }
        else if (target instanceof Long) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((Long) target);
            evaluateCalOps(cal, eventsPerStream, isNewData, exprEvaluatorContext);
            if (reformatOp != null) {
                return reformatOp.evaluate(cal);
            }
            return intervalOp.evaluate(cal.getTimeInMillis(), 0, eventsPerStream, isNewData, exprEvaluatorContext);
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

    private void evaluateCalOps(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        for (CalendarOp calendarOp : calendarOps) {
            calendarOp.evaluate(cal, eventsPerStream, isNewData, exprEvaluatorContext);
        }
    }
}
