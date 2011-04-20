package com.espertech.esper.support.bean;

public class SupportBeanCtorThree {

    private final SupportBean_ST0 st0;
    private final SupportBean_ST1[] st1;

    public SupportBeanCtorThree(SupportBean_ST0 st0, SupportBean_ST1[] st1) {
        this.st0 = st0;
        this.st1 = st1;
    }

    public SupportBean_ST0 getSt0() {
        return st0;
    }

    public SupportBean_ST1[] getSt1() {
        return st1;
    }
}
