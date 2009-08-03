package com.espertech.esper.support.bean;

public class SupportBeanReservedKeyword
{
    public int seconds;
    public int order;

    public SupportBeanReservedKeyword(int seconds, int order)
    {
        this.seconds = seconds;
        this.order = order;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }

    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }
}
