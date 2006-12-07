package net.esper.util;

import net.esper.client.EPException;

/**
 * Exception to represent a mismatch in Java types in an expression.
 */
public class CoercionException extends EPException
{
    /**
     * Ctor.
     * @param message supplies the detailed description
     */
    public CoercionException(final String message)
    {
        super(message);
    }
}
