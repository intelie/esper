package com.espertech.esper.client;

/**
 * Indicates that a variable was not found.
 */
public class VariableNotFoundException extends EPException
{
    /**
     * Ctor.
     * @param message supplies exception details
     */
    public VariableNotFoundException(final String message)
    {
        super(message);
    }
}
