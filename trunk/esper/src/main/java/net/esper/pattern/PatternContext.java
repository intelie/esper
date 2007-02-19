package net.esper.pattern;

import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleBucket;
import net.esper.filter.FilterService;
import net.esper.event.EventAdapterService;
import net.esper.util.ManagedLock;
import net.esper.core.EPStatementHandle;

/**
 * Contains handles to implementations of services needed by evaluation nodes.
 */
public final class PatternContext
{
    private final FilterService filterService;
    private final SchedulingService schedulingService;
    private final ScheduleBucket scheduleBucket;
    private final EventAdapterService eventAdapterService;
    private final EPStatementHandle epStatementHande;

    /**
     * Constructor.
     * @param filterService implementation for filtering service
     * @param scheduleBucket schedule buckets for use by scheduling service for ordering scheduling callbacks for pattern statements 
     * @param schedulingService implementation for schedule evaluation
     * @param eventAdapterService service for event adapters or wrappers
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     */
    public PatternContext(FilterService filterService,
                          SchedulingService schedulingService,
                          ScheduleBucket scheduleBucket,
                          EventAdapterService eventAdapterService,
                          EPStatementHandle epStatementHandle)
    {
        this.filterService = filterService;
        this.schedulingService = schedulingService;
        this.scheduleBucket = scheduleBucket;
        this.eventAdapterService = eventAdapterService;
        this.epStatementHande = epStatementHandle;
    }

    /**
     * Returns service to use for filter evaluation.
     * @return filter evaluation service implemetation
     */
    public final FilterService getFilterService()
    {
        return filterService;
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
     * Returns the schedule bucket for ordering schedule callbacks within this pattern.
     * @return schedule bucket
     */
    public ScheduleBucket getScheduleBucket()
    {
        return scheduleBucket;
    }

    /**
     * Returns teh service providing event adaptering or wrapping.
     * @return event adapter service
     */
    public EventAdapterService getEventAdapterService()
    {
        return eventAdapterService;
    }

    /**
     * Returns the statement's resource handle for locking.
     * @return handle of statement
     */
    public EPStatementHandle getEpStatementHandle()
    {
        return epStatementHande;
    }
}