package net.esper.support.bean;

public class ISupportAImpl implements ISupportA
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
