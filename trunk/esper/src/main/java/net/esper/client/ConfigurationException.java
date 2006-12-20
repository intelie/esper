package net.esper.client;

/**
 * Thrown to indicate a configuration problem.
 */
final public class ConfigurationException extends EPException
{
    /**
     * Ctor.
     * @param message - error message
     */
    public ConfigurationException(final String message)
    {
        super(message);
    }

    /**
     * Ctor for an inner exception and message.
     * @param message - error message
     * @param cause - inner exception
     */
    public ConfigurationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Ctor - just an inner exception.
     * @param cause - inner exception
     */
    public ConfigurationException(final Throwable cause)
    {
        super(cause);
    }
}
