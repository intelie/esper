package net.esper.view;

import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleBucket;
import net.esper.event.EventAdapterService;
import net.esper.core.EPStatementHandle;

/**
 * Contains handles to the implementation of the the scheduling service for use in view evaluation.
 */
public final class ViewServiceContext
{
    private final SchedulingService schedulingService;
    private final ScheduleBucket scheduleBucket;
    private final EventAdapterService eventAdapterService;
    private final EPStatementHandle epStatementHandle;

    /**
     * Constructor.
     * @param schedulingService implementation for schedule registration
     * @param scheduleBucket is for ordering scheduled callbacks within the view statements
     * @param eventAdapterService service for generating events and handling event types
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     */
    public ViewServiceContext(SchedulingService schedulingService,
                              ScheduleBucket scheduleBucket,
                              EventAdapterService eventAdapterService,
                              EPStatementHandle epStatementHandle)
    {
        this.schedulingService = schedulingService;
        this.eventAdapterService = eventAdapterService;
        this.scheduleBucket = scheduleBucket;
        this.epStatementHandle = epStatementHandle;
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

    /**
     * Returns the schedule bucket for ordering schedule callbacks within this pattern.
     * @return schedule bucket
     */
    public ScheduleBucket getScheduleBucket()
    {
        return scheduleBucket;
    }

    /**
     * Returns the statement's resource locks.
     * @return statement resource lock/handle
     */
    public EPStatementHandle getEpStatementHandle()
    {
        return epStatementHandle;
    }
}