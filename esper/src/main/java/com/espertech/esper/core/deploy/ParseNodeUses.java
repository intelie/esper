package com.espertech.esper.core.deploy;

public class ParseNodeUses extends ParseNode
{
    private String uses;

    public ParseNodeUses(String text, int lineNum, String uses)
    {
        super(text, lineNum);
        this.uses = uses;
    }

    public String getUses()
    {
        return uses;
    }
}
