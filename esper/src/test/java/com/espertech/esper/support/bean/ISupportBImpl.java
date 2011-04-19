package com.espertech.esper.support.bean;

import java.io.Serializable;

public class ISupportBImpl implements ISupportB, Serializable
{
    private String valueB;
    private String valueBaseAB;

    public ISupportBImpl(String valueB, String valueBaseAB)
    {
        this.valueB = valueB;
        this.valueBaseAB = valueBaseAB;
    }

    public String getB()
    {
        return valueB;
    }

    public String getBaseAB()
    {
        return valueBaseAB;
    }
}
