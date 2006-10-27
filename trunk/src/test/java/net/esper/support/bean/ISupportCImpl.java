package net.esper.support.bean;

public class ISupportCImpl implements ISupportC
{
    private String valueC;

    public ISupportCImpl(String valueC)
    {
        this.valueC = valueC;
    }

    public String getC()
    {
        return valueC;
    }
}
