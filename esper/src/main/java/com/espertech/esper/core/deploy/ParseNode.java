package com.espertech.esper.core.deploy;

public abstract class ParseNode
{
    private String text;
    private int lineNum;

    protected ParseNode(String text, int lineNum)
    {
        this.text = text;
        this.lineNum = lineNum;
    }

    public String getText()
    {
        return text;
    }

    public int getLineNum()
    {
        return lineNum;
    }
}
