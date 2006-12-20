package net.esper.schedule;

/**
 * Interface for a service that allows to add and remove callbacks for a certain time which are called when
 * the evaluate method is invoked and the current time is on or after the callback times.
 * It is the expectation that the triggerPast method is called
 * with same or ascending values for each subsequent call. Callbacks with are triggered are automatically removed
 * by implementations.
 */
public interface SchedulingService
{
    /**
     * Add a callback for after the given milliseconds from the current time.
     * If the same callback (equals) was already added before, the method will not add a new
     * callback or change the existing callback to a new time, but throw an exception.
     * @param afterMSec number of millisec to get a callback
     * @param callback to add
     * @param slot allows ordering of concurrent callbacks
     * @throws ScheduleServiceException thrown if the add operation did not complete
     */
    public void add(long afterMSec, ScheduleCallback callback, ScheduleSlot slot)
            throws ScheduleServiceException;

    /**
     * Add a callback for a time specified by the schedule specification passed in based on the current time.
     * If the same callback (equals) was already added before, the method will not add a new
     * callback or change the existing callback to a new time, but throw an exception.
     * @param scheduleSpec holds the crontab-like information defining the next occurance
     * @param callback to add
     * @param slot allows ordering of concurrent callbacks
     * @throws ScheduleServiceException thrown if the add operation did not complete
     */
    public void add(ScheduleSpec scheduleSpec, ScheduleCallback callback, ScheduleSlot slot)
            throws ScheduleServiceException;

    /**
     * Remove a callback.
     * If the callback to be removed was not found an exception is thrown.
     * @param callback to remove
     * @param slot for which the callback was added
     * @throws ScheduleServiceException thrown if the callback was not located
     */
    public void remove(ScheduleCallback callback, ScheduleSlot slot)
            throws ScheduleServiceException;

    /**
     * Gets the last time known to the scheduling service.
     * @return time that has last been set on this service
     */
    public long getTime();

    /**
     * Set the time based upon which the evaluation of events invokes callbacks.
     * @param timestamp to set
     */
    public void setTime(long timestamp);

    /**
     * Evaluate the current time and perform any callbacks.
     */
    public void evaluate();

    /**
     * Returns a bucket from which slots can be allocated for ordering concurrent callbacks.
     * @return bucket
     */
    public ScheduleBucket allocateBucket();
}

