package com.espertech.esper.support.bean;

import java.io.Serializable;

public abstract class SupportBeanAbstractBase implements Serializable {

    private String v1;

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }
}
