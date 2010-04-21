package com.espertech.esper.core.deploy;

public class ParseNodeImport extends ParseNode
{
    private String imported;

    public ParseNodeImport(String text, int lineNum, String imported)
    {
        super(text, lineNum);
        this.imported = imported;
    }

    public String getImported()
    {
        return imported;
    }
}
