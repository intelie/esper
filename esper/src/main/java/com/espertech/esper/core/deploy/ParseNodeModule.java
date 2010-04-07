package com.espertech.esper.core.deploy;

public class ParseNodeModule extends ParseNode
{
    private String moduleName;

    public ParseNodeModule(String text, String moduleName)
    {
        super(text);
        this.moduleName = moduleName;
    }

    public String getModuleName()
    {
        return moduleName;
    }
}
