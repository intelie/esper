package net.esper.eql.core;

/**
 * Exception to indicate that a stream name could not be resolved.
 */
public class DatabaseRefException extends Exception
{
    /**
     * Ctor.
     * @param msg - message
     */
    public DatabaseRefException(String msg)
    {
        super(msg);
    }

    public DatabaseRefException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
