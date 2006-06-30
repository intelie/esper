package net.esper.support.bean;

public class ISupportBCImpl implements ISupportB, ISupportC
{
    private String valueB;
    private String valueBaseAB;
    private String valueC;

    public ISupportBCImpl(String valueB, String valueBaseAB, String valueC)
    {
        this.valueB = valueB;
        this.valueBaseAB = valueBaseAB;
        this.valueC = valueC;
    }

    public String getB()
    {
        return valueB;
    }

    public String getBaseAB()
    {
        return valueBaseAB;
    }

    public String getC()
    {
        return valueC;
    }
}
