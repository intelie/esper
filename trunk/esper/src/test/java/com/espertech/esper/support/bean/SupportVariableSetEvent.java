package com.espertech.esper.support.bean;

public class SupportVariableSetEvent
{
    private String variableName;
    private String value;

    public SupportVariableSetEvent(String variableName, String value)
    {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName()
    {
        return variableName;
    }

    public String getValue()
    {
        return value;
    }
}
