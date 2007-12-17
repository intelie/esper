package net.esper.support.bean;

public class ISupportDImpl implements ISupportD
{
    private String valueD;
    private String valueBaseD;
    private String valueBaseDBase;

    public ISupportDImpl(String valueD, String valueBaseD, String valueBaseDBase)
    {
        this.valueD = valueD;
        this.valueBaseD = valueBaseD;
        this.valueBaseDBase = valueBaseDBase;
    }

    public String getD()
    {
        return valueD;
    }

    public String getBaseD()
    {
        return valueBaseD;
    }

    public String getBaseDBase()
    {
        return valueBaseDBase;
    }
}
