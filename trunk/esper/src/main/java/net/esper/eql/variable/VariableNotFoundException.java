package net.esper.eql.variable;

import net.esper.eql.core.StreamTypesException;

public class VariableNotFoundException extends StreamTypesException
{
    public VariableNotFoundException(String msg)
    {
        super(msg);
    }
}
