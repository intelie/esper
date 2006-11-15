package net.esper.eql.db;

/**
 * Exception to indicate that a stream name could not be resolved.
 */
public class DatabaseConfigException extends Exception
{
    /**
     * Ctor.
     * @param msg - message
     */
    public DatabaseConfigException(String msg)
    {
        super(msg);
    }

    /**
     * Ctor.
     * @param message - error message
     * @param cause - cause is the inner exception
     */
    public DatabaseConfigException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
