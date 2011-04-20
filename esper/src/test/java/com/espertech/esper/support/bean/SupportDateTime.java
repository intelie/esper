package com.espertech.esper.support.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SupportDateTime {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private Long msecdate;
    private Date utildate;
    private Calendar caldate;

    public SupportDateTime(Long msecdate, Date utildate, Calendar caldate) {
        this.msecdate = msecdate;
        this.utildate = utildate;
        this.caldate = caldate;
    }

    public Long getMsecdate() {
        return msecdate;
    }

    public Date getUtildate() {
        return utildate;
    }

    public Calendar getCaldate() {
        return caldate;
    }

    public static SupportDateTime make(String datestr) {
        if (datestr == null) {
            return new SupportDateTime(null, null, null);
        }
        // expected : 2002-05-30T09:00:00
        Date date = parse(datestr);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.MILLISECOND, 0);
        return new SupportDateTime(date.getTime(), date, cal);
    }

    public static long parseGetMSec(String datestr) {
        return parse(datestr).getTime();
    }

    public static Date parseGetDate(String datestr) {
        return parse(datestr);
    }

    public static Calendar parseGetCal(String datestr) {
        Calendar cal = Calendar.getInstance();
        parse(datestr);
        cal.setTimeInMillis(cal.getTimeInMillis());
        return cal;
    }

    private static Date parse(String datestr) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            date = sdf.parse(datestr);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error parsing date '" + datestr + "' as format '" + DATE_FORMAT + "' : " + ex.getMessage(), ex);
        }
        return date;
    }

    public static Object getValueCoerced(String expectedTime, String format) {
        long msec = parseGetMSec(expectedTime);
        return coerce(msec, format);
    }

    private static Object coerce(long msec, String format) {
        if (format.equalsIgnoreCase("msec")) {
            return msec;
        }
        else if (format.equalsIgnoreCase("util")) {
            return new Date(msec);
        }
        else if (format.equalsIgnoreCase("cal")) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(msec);
            return cal;
        }
        else if (format.equalsIgnoreCase("sdf")) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(msec);
            SimpleDateFormat sdf = new SimpleDateFormat();
            return sdf.format(cal.getTime());
        }
        else if (format.equalsIgnoreCase("null")) {
            return null;
        }
        else {
            throw new RuntimeException("Unrecognized format abbreviation '" + format + "'");
        }
    }

    public static Object[] getArrayCoerced(String expectedTime, String... desc) {
        Object[] result = new Object[desc.length];
        long msec = parseGetMSec(expectedTime);
        for (int i = 0; i < desc.length; i++) {
            result[i] = coerce(msec, desc[i]);
        }
        return result;
    }

    public static Object[] getArrayCoerced(String[] expectedTimes, String desc) {
        Object[] result = new Object[expectedTimes.length];
        for (int i = 0; i < expectedTimes.length; i++) {
            long msec = parseGetMSec(expectedTimes[i]);
            result[i] = coerce(msec, desc);
        }
        return result;
    }

    public static String print(Object date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (date instanceof Long) {
            return sdf.format(new Date((Long) date));
        }
        if (date instanceof Date) {
            return sdf.format((Date) date);
        }
        if (date instanceof Calendar) {
            return sdf.format(((Calendar) date).getTime());
        }
        throw new IllegalArgumentException("Date format for type '" + date.getClass() + "' not possible");
    }
}
