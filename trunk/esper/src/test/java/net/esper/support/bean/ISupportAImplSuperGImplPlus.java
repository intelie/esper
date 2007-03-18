package net.esper.support.bean;

import java.io.Serializable;

public class ISupportAImplSuperGImplPlus extends ISupportAImplSuperG implements ISupportB, ISupportC, Serializable
{
    String valueG;
    String valueA;
    String valueBaseAB;
    String valueB;
    String valueC;

    public ISupportAImplSuperGImplPlus()
    {
    }

    public ISupportAImplSuperGImplPlus(String valueG, String valueA, String valueBaseAB, String valueB, String valueC)
    {
        this.valueG = valueG;
        this.valueA = valueA;
        this.valueBaseAB = valueBaseAB;
        this.valueB = valueB;
        this.valueC = valueC;
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

    public String getB()
    {
        return valueB;
    }

    public String getC()
    {
        return valueC;
    }
}
