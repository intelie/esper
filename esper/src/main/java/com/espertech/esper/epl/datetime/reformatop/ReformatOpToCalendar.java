package com.espertech.esper.epl.datetime.reformatop;

import java.util.Calendar;
import java.util.Date;

public class ReformatOpToCalendar implements ReformatOp {

    public Object evaluate(Long ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts);
        return cal;
    }

    public Object evaluate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d.getTime());
        return cal;
    }

    public Object evaluate(Calendar cal) {
        return cal;
    }

    public Class getReturnType() {
        return Calendar.class;
    }
}
