package com.espertech.esper.support.bean;

public class SupportBeanRendererTwo
{
    private String stringVal;
    private SupportEnum enumValue;

    public SupportBeanRendererTwo()
    {
    }

    public SupportEnum getEnumValue()
    {
        return enumValue;
    }

    public void setEnumValue(SupportEnum enumValue)
    {
        this.enumValue = enumValue;
    }

    public String getStringVal()
    {
        return stringVal;
    }

    public void setStringVal(String stringVal)
    {
        this.stringVal = stringVal;
    }
}
