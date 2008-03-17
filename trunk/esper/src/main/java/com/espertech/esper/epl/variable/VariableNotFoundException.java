package com.espertech.esper.epl.variable;

/**
 * Exception indicating a variable does not exists.
 */
public class VariableNotFoundException extends VariableDeclarationException
{
    /**
     * Ctor.
     * @param msg the exception message.
     */
    public VariableNotFoundException(String msg)
    {
        super(msg);
    }
}
