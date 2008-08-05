package com.espertech.esper.epl.variable;

/**
 * Exception indicating a variable type error.
 */
public class VariableTypeException extends VariableDeclarationException
{
    /**
     * Ctor.
     * @param msg the exception message.
     */
    public VariableTypeException(String msg)
    {
        super(msg);
    }
}