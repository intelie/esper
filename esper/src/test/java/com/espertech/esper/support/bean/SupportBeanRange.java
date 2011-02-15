package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportBeanRange implements Serializable
{
    private String id;
    private String key;
    private Integer rangeStart;
    private Integer rangeEnd;
    private String rangeStartStr;
    private String rangeEndStr;
    private Long rangeStartLong;
    private Long rangeEndLong;

    public SupportBeanRange() {
    }

    public static SupportBeanRange makeLong(String id, String key, Long rangeStartLong, Long rangeEndLong) {
        SupportBeanRange bean = new SupportBeanRange();
        bean.id = id;
        bean.key = key;
        bean.rangeStartLong = rangeStartLong;
        bean.rangeEndLong = rangeEndLong;
        return bean;
    }

    public SupportBeanRange(String id, Integer rangeStart, Integer rangeEnd) {
        this.id = id;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public SupportBeanRange(String id, String key, String rangeStartStr, String rangeEndStr) {
        this.id = id;
        this.key = key;
        this.rangeStartStr = rangeStartStr;
        this.rangeEndStr = rangeEndStr;
    }

    public SupportBeanRange(String id, String key, Integer rangeStart, Integer rangeEnd) {
        this.id = id;
        this.key = key;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public Long getRangeStartLong() {
        return rangeStartLong;
    }

    public Long getRangeEndLong() {
        return rangeEndLong;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    public Integer getRangeStart() {
        return rangeStart;
    }

    public Integer getRangeEnd() {
        return rangeEnd;
    }

    public String getRangeStartStr() {
        return rangeStartStr;
    }

    public String getRangeEndStr() {
        return rangeEndStr;
    }
}
