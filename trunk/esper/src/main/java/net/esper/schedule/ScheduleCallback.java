package net.esper.schedule;

/**
 * Interface for scheduled callbacks.
 */
public interface ScheduleCallback
{
    /**
     * Callback that is invoked as indicated by a schedule added to the scheduling service. 
     */
    public void scheduledTrigger();
}
