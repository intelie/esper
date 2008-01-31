package com.espertech.esper.support.bean;

public class SupportMarkerImplB implements SupportMarkerInterface
{
    private int id;

    public SupportMarkerImplB(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
