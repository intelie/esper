package com.espertech.esper.epl.datetime.calop;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Calendar;

public class CalendarOpWithMin implements CalendarOp {

    private final CalendarFieldEnum fieldName;

    public CalendarOpWithMin(CalendarFieldEnum fieldName) {
        this.fieldName = fieldName;
    }

    public void evaluate(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        cal.set(fieldName.getCalendarField(), cal.getActualMinimum(fieldName.getCalendarField()));
    }
}
