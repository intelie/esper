package com.espertech.esper.epl.datetime;

import java.util.Calendar;

public class CalendarWithDuration {
    private final Calendar calendar;
    private final long duration;

    public CalendarWithDuration(Calendar calendar, long duration) {
        this.calendar = calendar;
        this.duration = duration;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public long getDuration() {
        return duration;
    }
}
