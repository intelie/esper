package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportBeanSimple implements Serializable
{
    private String myString;
    private int myInt;

    public SupportBeanSimple(String myString, int myInt)
    {
        this.myString = myString;
        this.myInt = myInt;
    }

    public String getMyString()
    {
        return myString;
    }

    public void setMyString(String myString)
    {
        this.myString = myString;
    }

    public int getMyInt()
    {
        return myInt;
    }

    public void setMyInt(int myInt)
    {
        this.myInt = myInt;
    }


}
