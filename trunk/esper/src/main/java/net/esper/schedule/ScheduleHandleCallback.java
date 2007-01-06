package net.esper.schedule;

/**
 * Interface for scheduled callbacks.
 */
public interface ScheduleHandleCallback extends ScheduleHandle
{
    /**
     * Callback that is invoked as indicated by a schedule added to the scheduling service. 
     */
    public void scheduledTrigger();
}
