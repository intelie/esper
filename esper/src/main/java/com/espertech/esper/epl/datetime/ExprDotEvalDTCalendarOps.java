package com.espertech.esper.epl.datetime;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.calop.CalendarOp;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExprDotEvalDTCalendarOps implements ExprDotEval
{
    private static final Log log = LogFactory.getLog(ExprDotEvalDTCalendarOps.class);

    private final DTEvaluator dtEvaluator;
    private final List<CalendarOp> calendarOps;
    private final ExprDotEvalTypeInfo returnType;

    public ExprDotEvalDTCalendarOps(Class inputType, List<CalendarOp> calendarOps) {
        this.calendarOps = calendarOps;
        if (inputType == Long.class) {
            dtEvaluator = new DTEvaluatorLong();
            returnType = ExprDotEvalTypeInfo.scalarOrUnderlying(Long.class);
        }
        else if (inputType == Calendar.class) {
            dtEvaluator = new DTEvaluatorCal();
            returnType = ExprDotEvalTypeInfo.scalarOrUnderlying(Calendar.class);
        }
        else if (JavaClassHelper.isSubclassOrImplementsInterface(inputType, Date.class)) {
            dtEvaluator = new DTEvaluatorDate();
            returnType = ExprDotEvalTypeInfo.scalarOrUnderlying(Date.class);
        }
        else {
            throw new UnsupportedOperationException("Calendar ops not supported on input type '" + inputType + "'");
        }
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        return dtEvaluator.evaluate(target, eventsPerStream, isNewData, exprEvaluatorContext);
    }

    private void evaluateCalOps(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        for (CalendarOp calendarOp : calendarOps) {
            calendarOp.evaluate(cal, eventsPerStream, isNewData, exprEvaluatorContext);
        }
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        return returnType;
    }

    private interface DTEvaluator {
        public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext);
    }

    private class DTEvaluatorLong implements DTEvaluator {
        public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
            if (target == null) {
                return null;
            }

            Long longValue = (Long) target;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(longValue);

            evaluateCalOps(cal, eventsPerStream, isNewData, exprEvaluatorContext);

            return cal.getTimeInMillis();
        }
    }

    private class DTEvaluatorDate implements DTEvaluator {
        public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
            if (target == null) {
                return null;
            }

            Date dateValue = (Date) target;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dateValue.getTime());

            evaluateCalOps(cal, eventsPerStream, isNewData, exprEvaluatorContext);

            return cal.getTime();
        }
    }

    private class DTEvaluatorCal implements DTEvaluator {
        public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
            if (target == null) {
                return null;
            }

            Calendar calValue = (Calendar) target;
            Calendar cal = (Calendar) calValue.clone();

            evaluateCalOps(cal, eventsPerStream, isNewData, exprEvaluatorContext);

            return cal;
        }
    }
}
