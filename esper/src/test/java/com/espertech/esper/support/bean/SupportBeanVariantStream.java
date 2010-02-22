package com.espertech.esper.support.bean;

import java.io.Serializable;

// For testing variant streams to act as a variant of SupportBean
public class SupportBeanVariantStream implements Serializable
{
    private String string;
    private boolean boolBoxed;
    private Integer intPrimitive;
    private int longPrimitive;
    private float doublePrimitive;
    private SupportEnum enumValue;

    public SupportBeanVariantStream(String string)
    {
        this.string = string;
    }

    public SupportBeanVariantStream(String string, boolean boolBoxed, Integer intPrimitive, int longPrimitive, float doublePrimitive, SupportEnum enumValue)
    {
        this.string = string;
        this.boolBoxed = boolBoxed;
        this.intPrimitive = intPrimitive;
        this.longPrimitive = longPrimitive;
        this.doublePrimitive = doublePrimitive;
        this.enumValue = enumValue;
    }

    public String getString()
    {
        return string;
    }

    public boolean isBoolBoxed()
    {
        return boolBoxed;
    }

    public Integer getIntPrimitive()
    {
        return intPrimitive;
    }

    public int getLongPrimitive()
    {
        return longPrimitive;
    }

    public float getDoublePrimitive()
    {
        return doublePrimitive;
    }

    public SupportEnum getEnumValue()
    {
        return enumValue;
    }
}
