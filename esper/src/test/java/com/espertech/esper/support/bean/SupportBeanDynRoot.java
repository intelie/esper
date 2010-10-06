package com.espertech.esper.support.bean;

public class SupportBeanDynRoot implements SupportMarkerInterface
{
    private Object item;

    public SupportBeanDynRoot(Object inner)
    {
        this.item = inner;
    }

    public Object getItem()
    {
        return item;
    }
}
