package com.espertech.esper.support.bean;

public class SupportBean_ST0 {

    private String id;
    private String key0;
    private int p00;
    private Long p01Long;
    private String pcommon;

    public SupportBean_ST0(String id, int p00) {
        this.id = id;
        this.p00 = p00;
    }

    public SupportBean_ST0(String id, String key0, int p00) {
        this.id = id;
        this.key0 = key0;
        this.p00 = p00;
    }

    public SupportBean_ST0(String id, Long p01Long) {
        this.id = id;
        this.p01Long = p01Long;
    }

    public SupportBean_ST0(String id, int p00, String pcommon) {
        this.id = id;
        this.p00 = p00;
        this.pcommon = pcommon;
    }

    public String getId() {
        return id;
    }

    public String getKey0() {
        return key0;
    }

    public int getP00() {
        return p00;
    }

    public Long getP01Long() {
        return p01Long;
    }

    public String getPcommon() {
        return pcommon;
    }

    public void setPcommon(String pcommon) {
        this.pcommon = pcommon;
    }
}
