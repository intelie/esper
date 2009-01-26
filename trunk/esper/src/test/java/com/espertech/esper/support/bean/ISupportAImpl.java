package com.espertech.esper.support.bean;

import java.io.Serializable;

public class ISupportAImpl implements ISupportA, Serializable
{
    private String valueA;
    private String valueBaseAB;

    public ISupportAImpl(String valueA, String valueBaseAB)
    {
        this.valueA = valueA;
        this.valueBaseAB = valueBaseAB;
    }

    public String getA()
    {
        return valueA;
    }

    public String getBaseAB()
    {
        return valueBaseAB;
    }
}
