package com.espertech.esper.epl.variable;

/**
 * Exception indicating a problem in a variable declaration.
 */
public class VariableDeclarationException extends Exception
{
    /**
     * Ctor.
     * @param msg the exception message.
     */
    public VariableDeclarationException(String msg)
    {
        super(msg);
    }
}
