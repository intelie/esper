package com.espertech.esper.support.bean;

public final class SupportBeanTypeChange
{
    private final Integer intBoxed;
    private final String intPrimitive;

    public SupportBeanTypeChange(Integer intBoxed, String intPrimitive)
    {
        this.intBoxed = intBoxed;
        this.intPrimitive = intPrimitive;
    }

    public Integer getIntBoxed()
    {
        return intBoxed;
    }

    public String getIntPrimitive()
    {
        return intPrimitive;
    }
}
