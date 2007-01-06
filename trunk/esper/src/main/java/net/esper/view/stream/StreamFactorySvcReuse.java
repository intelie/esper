package net.esper.view.stream;

import net.esper.view.EventStream;
import net.esper.view.ZeroDepthStream;
import net.esper.filter.*;
import net.esper.collection.Pair;
import net.esper.collection.RefCountedMap;
import net.esper.event.EventBean;
import net.esper.util.ManagedLock;
import net.esper.core.EPStatementHandle;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Service implementation to reuse event streams and existing filters using reference counting to remove filters
 * when not used.
 */
public class StreamFactorySvcReuse implements StreamFactoryService
{
    private RefCountedMap<FilterSpec, Pair<EventStream, FilterHandle>> eventStreams;

    /**
     * Ctor.
     */
    public StreamFactorySvcReuse()
    {
        this.eventStreams = new RefCountedMap<FilterSpec, Pair<EventStream, FilterHandle>>();
    }

    public EventStream createStream(FilterSpec filterSpec, FilterService filterService, EPStatementHandle epStatementHandle)
    {
        // Check if a stream for this filter already exists
        Pair<EventStream, FilterHandle> pair = eventStreams.get(filterSpec);

        if (pair != null)
        {
            eventStreams.reference(filterSpec);
            return pair.getFirst();
        }

        // New event stream
        final EventStream eventStream = new ZeroDepthStream(filterSpec.getEventType());

        FilterHandleCallback filterCallback = new FilterHandleCallback()
        {
            public void matchFound(EventBean event)
            {
                eventStream.insert(event);
            }
        };

        // Store stream for reuse
        pair = new Pair<EventStream, FilterHandle>(eventStream, filterCallback);
        eventStreams.put(filterSpec, pair);

        // Activate filter
        FilterValueSet filterValues = filterSpec.getValueSet(null);
        filterService.add(filterValues, filterCallback);

        return eventStream;
    }

    public void dropStream(FilterSpec filterSpec, FilterService filterService)
    {
        Pair<EventStream, FilterHandle> pair = eventStreams.get(filterSpec);
        boolean isLast = eventStreams.dereference(filterSpec);
        if (isLast)
        {
            filterService.remove(pair.getSecond());
        }
    }
}
