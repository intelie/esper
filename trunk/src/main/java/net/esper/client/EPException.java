package net.esper.client;

/**
 * This exception is thrown to indicate a problem in administration and runtime. 
 */
public class EPException extends RuntimeException
{
    /**
     * Ctor.
     * @param message - error message
     */
    public EPException(final String message)
    {
        super(message);
    }

    /**
     * Ctor for an inner exception and message.
     * @param message - error message
     * @param cause - inner exception
     */
    public EPException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Ctor - just an inner exception.
     * @param cause - inner exception
     */
    public EPException(final Throwable cause)
    {
        super(cause);
    }
}
