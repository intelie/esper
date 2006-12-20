package net.esper.support.bean;

public class SupportOverrideOneB extends SupportOverrideOne
{
    private String valOneB;

    public SupportOverrideOneB(String valOneB, String valOne, String valBase)
    {
        super(valOne, valBase);
        this.valOneB = valOneB;
    }

    public String getVal()
    {
        return valOneB;
    }
}
