package com.espertech.esper.core.deploy;

public abstract class ParseNode
{
    private String text;

    protected ParseNode(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
