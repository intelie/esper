package com.espertech.esper.event;

import com.espertech.esper.client.EPException;

/**
 * This exception is thrown to indicate a problem resolving an event type by name.
 */
public class EventAdapterException extends EPException
{
    /**
     * Ctor.
     * @param message - error message
     */
    public EventAdapterException(final String message)
    {
        super(message);
    }

    /**
     * Ctor.
     * @param message - error message
     * @param nested - nested exception
     */
    public EventAdapterException(final String message, Throwable nested)
    {
        super(message, nested);
    }
}
