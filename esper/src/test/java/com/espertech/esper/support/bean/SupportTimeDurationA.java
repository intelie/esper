package com.espertech.esper.support.bean;

import java.util.Calendar;
import java.util.Date;

public class SupportTimeDurationA {

    private String key;
    private Long msecdate;
    private Date utildate;
    private Calendar caldate;
    private Long duration;

    public SupportTimeDurationA(String key, Long msecdate, Date utildate, Calendar caldate, Long duration) {
        this.key = key;
        this.msecdate = msecdate;
        this.utildate = utildate;
        this.caldate = caldate;
        this.duration = duration;
    }

    public SupportTimeDurationA(Long msecdate, Date utildate, Calendar caldate, Long duration) {
        this.msecdate = msecdate;
        this.utildate = utildate;
        this.caldate = caldate;
        this.duration = duration;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getDuration() {
        return duration;
    }

    public static SupportTimeDurationA make(String datestr, long duration) {
        if (datestr == null) {
            return new SupportTimeDurationA(null, null, null, duration);
        }
        // expected : 2002-05-30T09:00:00
        Date date = SupportDateTime.parse(datestr);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.MILLISECOND, 0);
        return new SupportTimeDurationA(date.getTime(), date, cal, duration);
    }

    public static SupportTimeDurationA make(String key, String datestr, long duration) {
        SupportTimeDurationA bean = make(datestr, duration);
        bean.setKey(key);
        return bean;
    }
}
