package com.espertech.esper.support.bean;

public class SupportLegacyBeanInt
{
    public int fieldIntPrimitive;

    public SupportLegacyBeanInt(int fieldIntPrimitive)
    {
        this.fieldIntPrimitive = fieldIntPrimitive;
    }

    public int getIntPrimitive()
    {
        return fieldIntPrimitive;
    }

    public int readIntPrimitive()
    {
        return fieldIntPrimitive;
    }
}
