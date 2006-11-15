package net.esper.support.bean;

public class ISupportABCImpl implements ISupportA, ISupportB, ISupportC
{
    private String valueA;
    private String valueB;
    private String valueBaseAB;
    private String valueC;

    public ISupportABCImpl(String valueA, String valueB, String valueBaseAB, String valueC)
    {
        this.valueA = valueA;
        this.valueB = valueB;
        this.valueBaseAB = valueBaseAB;
        this.valueC = valueC;
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
