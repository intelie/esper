package com.espertech.esper.epl.datetime.eval;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static String print(long left, long leftDuration, long right, long rightDuration) {
        StringWriter writer = new StringWriter();

        writer.append("Left starts ");
        writer.append(print(left));

        writer.append(" ends ");
        writer.append(print(left + leftDuration));

        writer.append("  Right starts ");
        writer.append(print(right));

        writer.append(" ends ");
        writer.append(print(right + rightDuration));

        return writer.toString();
    }

    private static String print(long ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts);
        return new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
    }

}
