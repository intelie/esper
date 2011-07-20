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

import com.espertech.esper.epl.datetime.calop.CalendarFieldEnum;

import java.util.Calendar;
import java.util.Date;

public class ReformatOpGetField implements ReformatOp {

    private final CalendarFieldEnum fieldNum;

    public ReformatOpGetField(CalendarFieldEnum fieldNum) {
        this.fieldNum = fieldNum;
    }

    public Object evaluate(Long ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts);
        return action(cal);
    }

    public Object evaluate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d.getTime());
        return action(cal);
    }

    public Object evaluate(Calendar cal) {
        return action(cal);
    }

    private int action(Calendar cal) {
        return cal.get(fieldNum.getCalendarField());
    }

    public Class getReturnType() {
        return Integer.class;
    }
}
