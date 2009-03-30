package com.espertech.esper.support.bean;

public class SupportOverrideOne extends SupportOverrideBase
{
    private String valOne;

    public SupportOverrideOne(String valOne, String valBase)
    {
        super(valBase);
        this.valOne = valOne;
    }

    public String getVal()
    {
        return valOne;
    }
}
