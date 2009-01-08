package com.espertech.esper.regression.client;

public class MyAutoNamedEventType
{
    private int id;

    public MyAutoNamedEventType(int id)
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
