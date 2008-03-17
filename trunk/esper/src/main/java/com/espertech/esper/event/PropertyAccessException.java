package com.espertech.esper.event;

/**
 * This exception is thrown to indicate a problem with a accessing a property of an {@link EventBean}. 
 */
public final class PropertyAccessException extends RuntimeException
{
    /**
     * Constructor.
     * @param message is the error message
     */
    public PropertyAccessException(final String message)
    {
        super(message);
    }

    /**
     * Constructor for an inner exception and message.
     * @param message is the error message
     * @param cause is the inner exception
     */
    public PropertyAccessException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause is the inner exception
     */
    public PropertyAccessException(final Throwable cause)
    {
        super(cause);
    }
}
