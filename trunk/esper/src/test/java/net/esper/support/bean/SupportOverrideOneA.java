package net.esper.support.bean;

import java.io.Serializable;

public class SupportOverrideOneA extends SupportOverrideOne implements Serializable
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
