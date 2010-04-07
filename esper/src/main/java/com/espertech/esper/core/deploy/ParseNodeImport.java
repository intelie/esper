package com.espertech.esper.core.deploy;

public class ParseNodeImport extends ParseNode
{
    private String imported;

    public ParseNodeImport(String text, String imported)
    {
        super(text);
        this.imported = imported;
    }

    public String getImported()
    {
        return imported;
    }
}
