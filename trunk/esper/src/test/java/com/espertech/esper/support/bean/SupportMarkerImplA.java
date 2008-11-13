package com.espertech.esper.support.bean;

public class SupportMarkerImplA implements SupportMarkerInterface
{
    private String id;

    public SupportMarkerImplA(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
