package com.espertech.esper.epl.datetime.reformatop;

import java.util.Calendar;
import java.util.Date;

public interface CalendarEval {
    public Object evaluateInternal(Calendar cal);
}
