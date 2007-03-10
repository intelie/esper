package net.esper.schedule;

import net.esper.core.ExtensionServicesContext;

/**
 * Interface for scheduled callbacks.
 */
public interface ScheduleHandleCallback extends ScheduleHandle
{
    /**
     * Callback that is invoked as indicated by a schedule added to the scheduling service. 
     * @param extensionServicesContext is a marker interface for providing custom extension services
     * passed to the triggered class
     */
    public void scheduledTrigger(ExtensionServicesContext extensionServicesContext);
}
