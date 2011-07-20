/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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
