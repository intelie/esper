package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportBeanNumeric implements Serializable
{
    private int intOne;
    private int intTwo;

    public SupportBeanNumeric(int intOne, int intTwo)
    {
        this.intOne = intOne;
        this.intTwo = intTwo;
    }

    public int getIntOne()
    {
        return intOne;
    }

    public int getIntTwo()
    {
        return intTwo;
    }
}
