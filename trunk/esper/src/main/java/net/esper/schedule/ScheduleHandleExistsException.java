package net.esper.schedule;

/**
 * This exception is thrown to indicate trying to add a scheduling callback
 * that already existed.
 */
public final class ScheduleHandleExistsException extends ScheduleServiceException
{
    /**
     * Constructor.
     * @param message is the error message
     */
    public ScheduleHandleExistsException(final String message)
    {
        super(message);
    }

    /**
     * Constructor for an inner exception and message.
     * @param message is the error message
     * @param cause is the inner exception
     */
    public ScheduleHandleExistsException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause is the inner exception
     */
    public ScheduleHandleExistsException(final Throwable cause)
    {
        super(cause);
    }
}
