package net.esper.pattern;

/**
 * This exception is thrown to indicate a problem with a view expression.
 */
public final class PatternObjectException extends Exception
{
    /**
     * Constructor.
     * @param message is the error message
     */
    public PatternObjectException(final String message)
    {
        super(message);
    }

    /**
     * Constructor for an inner exception and message.
     * @param message is the error message
     * @param cause is the inner exception
     */
    public PatternObjectException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause is the inner exception
     */
    public PatternObjectException(final Throwable cause)
    {
        super(cause);
    }
}
