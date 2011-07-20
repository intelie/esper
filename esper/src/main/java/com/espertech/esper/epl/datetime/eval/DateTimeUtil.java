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

package com.espertech.esper.epl.datetime.eval;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static String print(long leftStart, long leftEnd, long rightStart, long rightEnd) {
        StringWriter writer = new StringWriter();

        writer.append("Left starts ");
        writer.append(print(leftStart));

        writer.append(" ends ");
        writer.append(print(leftEnd));

        writer.append("  Right starts ");
        writer.append(print(rightStart));

        writer.append(" ends ");
        writer.append(print(rightEnd));

        return writer.toString();
    }

    private static String print(long ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts);
        return new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
    }

}
