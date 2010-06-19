package com.espertech.esper.support.bean;

public class ISupportAImplSuperGImpl extends ISupportAImplSuperG
{
    private String valueG;
    private String valueA;
    private String valueBaseAB;

    public ISupportAImplSuperGImpl(String valueG, String valueA, String valueBaseAB)
    {
        this.valueG = valueG;
        this.valueA = valueA;
        this.valueBaseAB = valueBaseAB;
    }

    public String getG()
    {
        return valueG;
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
