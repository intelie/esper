package net.esper.filter;

import net.esper.event.EventBean;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of the filter service interface.
 * Does not allow the same filter callback to be added more then once.
 */
public final class FilterServiceImpl implements FilterService
{
    private final EventTypeIndexBuilder indexBuilder;
    private final EventTypeIndex eventTypeIndex;
    private final AtomicInteger numEventsEvaluated = new AtomicInteger();

    /**
     * Constructor.
     */
    protected FilterServiceImpl()
    {
        eventTypeIndex = new EventTypeIndex();
        indexBuilder = new EventTypeIndexBuilder(eventTypeIndex);
    }

    public final void add(FilterValueSet filterValueSet, FilterCallback filterCallback)
    {
        indexBuilder.add(filterValueSet, filterCallback);
    }

    public final void remove(FilterCallback filterCallback)
    {
        indexBuilder.remove(filterCallback);
    }

    public final void evaluate(EventBean eventBean)
    {
        numEventsEvaluated.incrementAndGet();

        // Finds all matching filters and return their callbacks
        List<FilterCallback> matches = new LinkedList<FilterCallback>();
        eventTypeIndex.matchEvent(eventBean, matches);

        if (matches.size() == 0)
        {
            return;
        }

        for (FilterCallback actionable : matches)
        {
            actionable.matchFound(eventBean);
        }
    }

    public final int getNumEventsEvaluated()
    {
        return numEventsEvaluated.get();
    }
}
