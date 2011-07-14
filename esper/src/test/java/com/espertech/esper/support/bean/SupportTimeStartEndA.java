package com.espertech.esper.support.bean;

import java.util.Calendar;
import java.util.Date;

public class SupportTimeStartEndA {

    private String key;
    private Long msecdateStart;
    private Date utildateStart;
    private Calendar caldateStart;
    private Long msecdateEnd;
    private Date utildateEnd;
    private Calendar caldateEnd;

    public SupportTimeStartEndA(String key, Long msecdateStart, Date utildateStart, Calendar caldateStart, Long msecdateEnd, Date utildateEnd, Calendar caldateEnd) {
        this.key = key;
        this.msecdateStart = msecdateStart;
        this.utildateStart = utildateStart;
        this.caldateStart = caldateStart;
        this.msecdateEnd = msecdateEnd;
        this.utildateEnd = utildateEnd;
        this.caldateEnd = caldateEnd;
    }

    public Long getMsecdateStart() {
        return msecdateStart;
    }

    public Date getUtildateStart() {
        return utildateStart;
    }

    public Calendar getCaldateStart() {
        return caldateStart;
    }

    public Long getMsecdateEnd() {
        return msecdateEnd;
    }

    public Date getUtildateEnd() {
        return utildateEnd;
    }

    public Calendar getCaldateEnd() {
        return caldateEnd;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static SupportTimeStartEndA make(String key, String datestr, long duration) {
        if (datestr == null) {
            return new SupportTimeStartEndA(key, null, null, null, null, null, null);
        }
        // expected : 2002-05-30T09:00:00.000
        long start = SupportDateTime.parseGetMSec(datestr);
        long end = start + duration;

        return new SupportTimeStartEndA(key, start, SupportDateTime.toDate(start), SupportDateTime.toCalendar(start),
                end, SupportDateTime.toDate(end), SupportDateTime.toCalendar(end));
    }
}
