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
