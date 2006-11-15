package net.esper.support.bean;

public class ISupportBaseABImpl implements ISupportBaseAB
{
    private String valueBaseAB;

    public ISupportBaseABImpl(String valueBaseAB)
    {
        this.valueBaseAB = valueBaseAB;
    }

    public String getBaseAB()
    {
        return valueBaseAB;
    }
}
