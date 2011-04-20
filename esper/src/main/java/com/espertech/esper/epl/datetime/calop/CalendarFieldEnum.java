package com.espertech.esper.epl.datetime.calop;

import java.io.StringWriter;
import java.util.Calendar;

public enum CalendarFieldEnum {
    MILLISEC(Calendar.MILLISECOND, "msec,millisecond,milliseconds"),
    SECOND(Calendar.SECOND, "sec,second,seconds"),
    MINUTE(Calendar.MINUTE, "min,minute,minutes"),
    HOUR(Calendar.HOUR_OF_DAY, "hour,hours"),
    DAY(Calendar.DATE, "day,days"),
    MONTH(Calendar.MONTH, "month,months"),
    WEEK(Calendar.WEEK_OF_YEAR, "week,weeks"),
    YEAR(Calendar.YEAR, "year,years")
    ;

    private final int calendarField;
    private final String[] names;

    CalendarFieldEnum(int calendarField, String names) {
        this.calendarField = calendarField;
        this.names = names.split(",");
    }

    public static String getValidList() {
        StringWriter writer = new StringWriter();
        String delimiter = "";
        for (CalendarFieldEnum v : CalendarFieldEnum.values()) {
            for (String name : v.names) {
                writer.append(delimiter);
                writer.append(name);
                delimiter = ",";
            }
        }
        return writer.toString();
    }

    public int getCalendarField() {
        return calendarField;
    }

    public String[] getNames() {
        return names;
    }

    public static CalendarFieldEnum fromString(String field) {
        String compareTo = field.trim().toLowerCase();
        for (CalendarFieldEnum v : CalendarFieldEnum.values()) {
            for (String name : v.names) {
                if (name.equals(compareTo)) {
                    return v;
                }
            }
        }
        return null;
    }
}

