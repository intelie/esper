package net.esper.view;

/**
 * This exception is thrown to indicate a problem with a view expression. 
 */
public final class ViewProcessingException extends Exception
{
    /**
     * Constructor.
     * @param message is the error message
     */
    public ViewProcessingException(final String message)
    {
        super(message);
    }

    /**
     * Constructor for an inner exception and message.
     * @param message is the error message
     * @param cause is the inner exception
     */
    public ViewProcessingException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause is the inner exception
     */
    public ViewProcessingException(final Throwable cause)
    {
        super(cause);
    }
}
