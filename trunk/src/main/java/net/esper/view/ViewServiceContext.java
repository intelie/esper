package net.esper.view;

import net.esper.schedule.SchedulingService;
import net.esper.event.EventAdapterService;

/**
 * Contains handles to the implementation of the the scheduling service for use in view evaluation.
 */
public final class ViewServiceContext
{
    private final SchedulingService schedulingService;
    private final EventAdapterService eventAdapterService; 

    /**
     * Constructor.
     * @param schedulingService implementation for schedule registration
     * @param eventAdapterService service for generating events and handling event types
     */
    public ViewServiceContext(SchedulingService schedulingService,
                              EventAdapterService eventAdapterService)
    {
        this.schedulingService = schedulingService;
        this.eventAdapterService = eventAdapterService;
    }

    /**
     * Returns service to use for schedule evaluation.
     * @return schedule evaluation service implemetation
     */
    public final SchedulingService getSchedulingService()
    {
        return schedulingService;
    }

    /**
     * Returns service for generating events and handling event types.
     * @return event adapter service
     */
    public EventAdapterService getEventAdapterService()
    {
        return eventAdapterService;
    }
}