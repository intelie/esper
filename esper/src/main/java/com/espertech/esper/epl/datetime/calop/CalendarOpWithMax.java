package com.espertech.esper.epl.datetime.calop;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Calendar;

public class CalendarOpWithMax implements CalendarOp {

    private final CalendarFieldEnum fieldName;

    public CalendarOpWithMax(CalendarFieldEnum fieldName) {
        this.fieldName = fieldName;
    }

    public void evaluate(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        cal.set(fieldName.getCalendarField(), cal.getActualMaximum(fieldName.getCalendarField()));
    }
}
