package com.espertech.esper.support.bean;

public class SupportBeanErrorTestingOne     // don't make serializable
{
    public SupportBeanErrorTestingOne()
    {
        throw new RuntimeException("Default ctor manufactured test exception");
    }

    public void setValue(String value)
    {
        throw new RuntimeException("Setter manufactured test exception");
    }

    public String getValue()
    {
        throw new RuntimeException("Getter manufactured test exception");
    }
}
