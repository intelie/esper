package net.esper.schedule;

import net.esper.core.ExtensionServicesContext;

/**
 * Interface for scheduled callbacks.
 */
public interface ScheduleHandleCallback extends ScheduleHandle
{
    /**
     * Callback that is invoked as indicated by a schedule added to the scheduling service. 
     */
    public void scheduledTrigger(ExtensionServicesContext extensionServicesContext);
}
