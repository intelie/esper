package com.espertech.esper.client.hook;

public class SQLInputParameterContext
{
    public int parameterNumber;
    public Object parameterValue;

    public SQLInputParameterContext()
    {
    }

    public void setParameterValue(Object parameterValue)
    {
        this.parameterValue = parameterValue;
    }

    public void setParameterNumber(int parameterNumber)
    {
        this.parameterNumber = parameterNumber;
    }

    public int getParameterNumber()
    {
        return parameterNumber;
    }

    public Object getParameterValue()
    {
        return parameterValue;
    }
}
