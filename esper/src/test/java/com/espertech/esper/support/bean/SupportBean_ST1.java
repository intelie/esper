package com.espertech.esper.support.bean;

public class SupportBean_ST1 {

    private String id;
    private String key1;
    private int p10;
    private Long p11Long;

    public SupportBean_ST1(String id, String key1, int p10) {
        this.id = id;
        this.key1 = key1;
        this.p10 = p10;
    }

    public SupportBean_ST1(String id, Long p11Long) {
        this.id = id;
        this.p11Long = p11Long;
    }

    public String getId() {
        return id;
    }

    public String getKey1() {
        return key1;
    }

    public int getP10() {
        return p10;
    }

    public Long getP11Long() {
        return p11Long;
    }
}
