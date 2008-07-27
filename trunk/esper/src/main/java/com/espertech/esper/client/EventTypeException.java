package com.espertech.esper.client;

/**
 * Indicates that a problem occurred looking up, assigning or creating and event type.
 */
public class EventTypeException extends EPException
{
    /**
     * Ctor.
     * @param message supplies exception details
     */
    public EventTypeException(final String message)
    {
        super(message);
    }
}
