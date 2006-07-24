package net.esper.support.bean;

public class ISupportBImpl implements ISupportB
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
