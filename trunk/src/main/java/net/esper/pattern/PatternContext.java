package net.esper.pattern;

import net.esper.schedule.SchedulingService;
import net.esper.filter.FilterService;

/**
 * Contains handles to implementations of services needed by evaluation nodes.
 */
public final class PatternContext
{
    private final FilterService filterService;
    private final SchedulingService schedulingService;

    /**
     * Constructor.
     * @param filterService implementation for filtering service
     * @param schedulingService implementation for schedule evaluation
     */
    public PatternContext(FilterService filterService, SchedulingService schedulingService)
    {
        this.filterService = filterService;
        this.schedulingService = schedulingService;
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
}