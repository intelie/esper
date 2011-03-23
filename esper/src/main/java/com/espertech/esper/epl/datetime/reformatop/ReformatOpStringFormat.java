package com.espertech.esper.epl.datetime.reformatop;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReformatOpStringFormat implements ReformatOp {

    public Object evaluate(Long ts) {
        return action(new Date(ts));
    }

    public Object evaluate(Date d) {
        return action(d);
    }

    public Object evaluate(Calendar cal) {
        return action(cal.getTime());
    }

    private static String action(Date d) {
        SimpleDateFormat format = new SimpleDateFormat();
        return format.format(d);
    }

    public Class getReturnType() {
        return String.class;
    }
}
