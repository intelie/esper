package net.esper.view.stream;

import net.esper.view.EventStream;
import net.esper.view.ZeroDepthStream;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterService;
import net.esper.filter.FilterCallback;
import net.esper.filter.FilterValueSet;
import net.esper.collection.Pair;
import net.esper.collection.RefCountedMap;
import net.esper.event.EventBean;

/**
 * Service implementation to reuse event streams and existing filters using reference counting to remove filters
 * when not used.
 */
public class StreamReuseServiceImpl implements StreamReuseService
{
    private RefCountedMap<FilterSpec, Pair<EventStream, FilterCallback>> eventStreams;

    /**
     * Ctor.
     */
    public StreamReuseServiceImpl()
    {
        this.eventStreams = new RefCountedMap<FilterSpec, Pair<EventStream, FilterCallback>>();
    }

    public EventStream createStream(FilterSpec filterSpec, FilterService filterService)
    {
        // Check if a stream for this filter already exists
        Pair<EventStream, FilterCallback> pair = eventStreams.get(filterSpec);

        if (pair != null)
        {
            eventStreams.reference(filterSpec);
            return pair.getFirst();
        }

        // New event stream
        final EventStream eventStream = new ZeroDepthStream(filterSpec.getEventType());

        FilterCallback filterCallback = new FilterCallback()
        {
            public void matchFound(EventBean event)
            {
                eventStream.insert(event);
            }
        };

        // Store stream for reuse
        pair = new Pair<EventStream, FilterCallback>(eventStream, filterCallback);
        eventStreams.put(filterSpec, pair);

        // Activate filter
        FilterValueSet filterValues = filterSpec.getValueSet(null);
        filterService.add(filterValues, filterCallback);

        return eventStream;
    }

    public void dropStream(FilterSpec filterSpec, FilterService filterService)
    {
        Pair<EventStream, FilterCallback> pair = eventStreams.get(filterSpec);
        boolean isLast = eventStreams.dereference(filterSpec);
        if (isLast)
        {
            filterService.remove(pair.getSecond());
        }
    }
}
