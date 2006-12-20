package net.esper.support.bean;

public class SupportOverrideOneA extends SupportOverrideOne
{
    private String valOneA;

    public SupportOverrideOneA(String valOneA, String valOne, String valBase)
    {
        super(valOne, valBase);
        this.valOneA = valOneA;
    }

    public String getVal()
    {
        return valOneA;
    }
}
