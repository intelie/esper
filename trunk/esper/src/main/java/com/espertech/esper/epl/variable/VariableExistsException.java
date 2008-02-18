package com.espertech.esper.epl.variable;

/**
 * Exception indicating a a variable already exists.
 */
public class VariableExistsException extends VariableDeclarationException
{
    /**
     * Ctor.
     * @param msg the exception message.
     */
    public VariableExistsException(String msg)
    {
        super(msg);
    }
}
