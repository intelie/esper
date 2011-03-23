package com.espertech.esper.client.util;

import com.espertech.esper.epl.datetime.reformatop.ReformatOpToCalendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    private static final Log log = LogFactory.getLog(ReformatOpToCalendar.class);

    public static Calendar toCalendar(String str) {
        return parseGetCal(str, new SimpleDateFormat());
    }

    public static Calendar toCalendar(String str, String format) {
        Date d = parse(str, format);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d.getTime());
        return cal;
    }

    public static Date toDate(String str) {
        return parse(str);
    }

    public static Date toDate(String str, String format) {
        return parse(str, format);
    }

    private static Date parse(String str) {
        return parse(str, new SimpleDateFormat());
    }

    public static Long toMillisec(String str) {
        Date date = parse(str);
        if (date == null) {
            return null;
        }
        return date.getTime();
    }

    public static Long toMillisec(String str, String format) {
        Date date = parse(str, format);
        if (date == null) {
            return null;
        }
        return date.getTime();
    }    

    private static Date parse(String str, String format) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat(format);
        }
        catch (Exception ex) {
            log.warn("Error in date format '" + str + "': " + ex.getMessage(), ex);
            return null;
        }
        return parse(str, sdf);
    }

    private static Date parse(String str, SimpleDateFormat format) {
        Date d;
        try {
            d = format.parse(str);
        } catch (ParseException e) {
            log.warn("Error parsing date '" + str + "' according to format '" + format.toPattern() + "': " + e.getMessage(), e);
            return null;
        }
        return d;
    }

    private static Calendar parseGetCal(String str, SimpleDateFormat format) {
        Date d = parse(str, format);
        if (d == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d.getTime());
        return cal;
    }
}
