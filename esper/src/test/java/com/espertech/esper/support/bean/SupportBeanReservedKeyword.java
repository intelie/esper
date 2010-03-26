package com.espertech.esper.support.bean;

public class SupportBeanReservedKeyword
{
    private int seconds;
    private int order;
    private Inner timestamp;
    public SupportBeanReservedKeyword innerbean;

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

    public SupportBeanReservedKeyword getInnerbean() {
        return innerbean;
    }

    public void setInnerbean(SupportBeanReservedKeyword innerbean) {
        this.innerbean = innerbean;
    }

    public Inner getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Inner timestamp)
    {
        this.timestamp = timestamp;
    }

    public static class Inner
    {
        private int hour;

        public int getHour()
        {
            return hour;
        }

        public void setHour(int hour)
        {
            this.hour = hour;
        }
    }
}
