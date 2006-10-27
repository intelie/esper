package net.esper.eql.core;

/**
 * Base class for stream and property name resolution errors.
 */
public abstract class StreamTypesException extends Exception
{
    /**
     * Ctor.
     * @param msg - message
     */
    public StreamTypesException(String msg)
    {
        super(msg);
    }
}
