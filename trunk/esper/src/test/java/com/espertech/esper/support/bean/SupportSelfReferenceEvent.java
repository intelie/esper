package com.espertech.esper.support.bean;

public class SupportSelfReferenceEvent
{
    private SupportSelfReferenceEvent selfRef;

    private String value;

    public SupportSelfReferenceEvent()
    {
    }

    public SupportSelfReferenceEvent getSelfRef()
    {
        return selfRef;
    }

    public String getValue()
    {
        return value;
    }

    public void setSelfRef(SupportSelfReferenceEvent selfRef)
    {
        this.selfRef = selfRef;
    }
}
