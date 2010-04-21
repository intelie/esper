package com.espertech.esper.core.deploy;

public class ParseNodeModule extends ParseNode
{
    private String moduleName;

    public ParseNodeModule(String text, int lineNum, String moduleName)
    {
        super(text, lineNum);
        this.moduleName = moduleName;
    }

    public String getModuleName()
    {
        return moduleName;
    }
}
