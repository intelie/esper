package net.esper.eql.db;

/**
 * Exception to indicate that a stream name could not be resolved.
 */
public class DatabaseException extends Exception
{
    /**
     * Ctor.
     * @param msg - message
     */
    public DatabaseException(String msg)
    {
        super(msg);
    }

    /**
     * Ctor.
     * @param message - error message
     * @param cause - cause is the inner exception
     */
    public DatabaseException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
