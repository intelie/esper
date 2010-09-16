package com.espertech.esper.support.bean;

public class SupportBeanDuckTypeTwo implements SupportBeanDuckType
{
    private int intValue;

    public SupportBeanDuckTypeTwo(int intValue)
    {
        this.intValue = intValue;
    }

    public int makeInteger() {
        return intValue;
    }

    public Object makeCommon() {
        return new SupportBeanDuckTypeOne("mytext");
    }

    public double returnDouble() {
        return 11.1234d;
    }
}
