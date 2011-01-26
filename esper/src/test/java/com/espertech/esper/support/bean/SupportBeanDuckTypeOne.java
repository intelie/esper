package com.espertech.esper.support.bean;

public class SupportBeanDuckTypeOne implements SupportBeanDuckType
{
    private String stringValue;

    public SupportBeanDuckTypeOne(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public String makeString() {
        return stringValue;
    }

    public Object makeCommon() {
        return new SupportBeanDuckTypeTwo(-1);
    }

    public double returnDouble() {
        return 12.9876d;
    }
}
