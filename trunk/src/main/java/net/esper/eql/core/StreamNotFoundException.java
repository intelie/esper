package net.esper.eql.core;

/**
 * Exception to indicate that a stream name could not be resolved.
 */
public class StreamNotFoundException extends StreamTypesException
{
    /**
     * Ctor.
     * @param msg - message
     */
    public StreamNotFoundException(String msg)
    {
        super(msg);
    }
}
