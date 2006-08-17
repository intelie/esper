package net.esper.pattern;

import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleBucket;
import net.esper.filter.FilterService;

/**
 * Contains handles to implementations of services needed by evaluation nodes.
 */
public final class PatternContext
{
    private final FilterService filterService;
    private final SchedulingService schedulingService;
    private final ScheduleBucket scheduleBucket;

    /**
     * Constructor.
     * @param filterService implementation for filtering service
     * @param scheduleBucket schedule buckets for use by scheduling service for ordering scheduling callbacks for pattern statements 
     * @param schedulingService implementation for schedule evaluation
     */
    public PatternContext(FilterService filterService, SchedulingService schedulingService, ScheduleBucket scheduleBucket)
    {
        this.filterService = filterService;
        this.schedulingService = schedulingService;
        this.scheduleBucket = scheduleBucket;
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
}