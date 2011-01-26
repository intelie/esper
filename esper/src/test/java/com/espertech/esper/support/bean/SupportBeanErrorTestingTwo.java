package com.espertech.esper.support.bean;

public class SupportBeanErrorTestingTwo
{
    private String value;
    
    public SupportBeanErrorTestingTwo()
    {
        value = "default";
    }

    public void setValue(String value)
    {
        throw new RuntimeException("Setter manufactured test exception");
    }

    public String getValue()
    {
        return value;
    }
}
