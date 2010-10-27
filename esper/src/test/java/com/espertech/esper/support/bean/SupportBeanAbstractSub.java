package com.espertech.esper.support.bean;

public class SupportBeanAbstractSub extends SupportBeanAbstractBase {

    private String v2;

    public SupportBeanAbstractSub(String v2) {
        this.v2 = v2;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }
}
