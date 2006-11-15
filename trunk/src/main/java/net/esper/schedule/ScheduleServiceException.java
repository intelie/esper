package net.esper.schedule;

/**
 * This exception is thrown to indicate a problem with scheduling, such as trying to add a scheduling callback
 * that already existed or trying to remove one that didn't exist.
 */
public final class ScheduleServiceException extends RuntimeException
{
    /**
     * Constructor.
     * @param message is the error message
     */
    public ScheduleServiceException(final String message)
    {
        super(message);
    }

    /**
     * Constructor for an inner exception and message.
     * @param message is the error message
     * @param cause is the inner exception
     */
    public ScheduleServiceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause is the inner exception
     */
    public ScheduleServiceException(final Throwable cause)
    {
        super(cause);
    }
}
