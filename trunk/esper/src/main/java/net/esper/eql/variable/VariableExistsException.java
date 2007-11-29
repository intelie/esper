package net.esper.eql.variable;

import net.esper.eql.core.StreamTypesException;

public class VariableExistsException extends StreamTypesException
{
    public VariableExistsException(String msg)
    {
        super(msg);
    }
}
