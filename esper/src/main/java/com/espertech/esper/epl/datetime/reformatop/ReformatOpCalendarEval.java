package com.espertech.esper.epl.datetime.reformatop;

import java.util.Calendar;
import java.util.Date;

public class ReformatOpCalendarEval implements ReformatOp {

    private final CalendarEval calendarEval;

    public ReformatOpCalendarEval(CalendarEval calendarEval) {
        this.calendarEval = calendarEval;
    }

    public Object evaluate(Long ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts);
        return calendarEval.evaluateInternal(cal);
    }

    public Object evaluate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d.getTime());
        return calendarEval.evaluateInternal(cal);
    }

    public Object evaluate(Calendar cal) {
        return calendarEval.evaluateInternal(cal);
    }

    public Class getReturnType() {
        return Integer.class;
    }
}
