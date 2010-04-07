package com.espertech.esper.core.deploy;

public class ParseNodeUses extends ParseNode
{
    private String uses;

    public ParseNodeUses(String text, String uses)
    {
        super(text);
        this.uses = uses;
    }

    public String getUses()
    {
        return uses;
    }
}
