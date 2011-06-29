package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportBeanObject implements Serializable
{
    private Object one;
    private Object two;
    private Object three;
    private Object four;
    private Object five;
    private Object six;

    public SupportBeanObject() {
    }

    public SupportBeanObject(Object one) {
        this.one = one;
    }

    public Object getFive()
    {
        return five;
    }

    public void setFive(Object five)
    {
        this.five = five;
    }

    public Object getFour()
    {
        return four;
    }

    public void setFour(Object four)
    {
        this.four = four;
    }

    public Object getOne()
    {
        return one;
    }

    public void setOne(Object one)
    {
        this.one = one;
    }

    public Object getSix()
    {
        return six;
    }

    public void setSix(Object six)
    {
        this.six = six;
    }

    public Object getThree()
    {
        return three;
    }

    public void setThree(Object three)
    {
        this.three = three;
    }

    public Object getTwo()
    {
        return two;
    }

    public void setTwo(Object two)
    {
        this.two = two;
    }
}
