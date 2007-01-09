package net.esper.view.stream;

import net.esper.view.EventStream;
import net.esper.view.ZeroDepthStream;
import net.esper.filter.*;
import net.esper.collection.Pair;
import net.esper.collection.RefCountedMap;
import net.esper.event.EventBean;
import net.esper.util.ManagedLock;
import net.esper.core.EPStatementHandle;
import net.esper.core.EPStatementHandleCallback;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Service implementation to reuse event streams and existing filters using reference counting to remove filters
 * when not used.
 * <p>
 * This can be very effective in that if a client applications creates a large number of very similar
 * statements in terms of filters and views used then these resources are all re-used
 * across statements.
 * <p>
 * The re-use is multithread-safe in that
 * (A) statement start/stop is locked against other engine processing
 * (B) the first statement supplies the lock for shared filters and views, protecting multiple threads
 * from entering into the same view.
 * (C) joins statements do not participate in filter and view reuse  
 */
public class StreamFactorySvcReuse
{
    private RefCountedMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>> eventStreams;

    /**
     * Ctor.
     */
    public StreamFactorySvcReuse()
    {
        this.eventStreams = new RefCountedMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>>();
    }

    /**
     * See the method of the same name in {@link StreamFactoryService}. Always attempts to reuse an existing event stream.
     * May thus return a new event stream or an existing event stream depending on whether filter criteria match.
     * @param filterSpec is the filter definition
     * @param filterService is the filtering service
     * @param epStatementHandle is the statement resource lock
     * @return newly created event stream, not reusing existing instances
     */
    public EventStream createStream(FilterSpec filterSpec, FilterService filterService, EPStatementHandle epStatementHandle)
    {
        // Check if a stream for this filter already exists
        Pair<EventStream, EPStatementHandleCallback> pair = eventStreams.get(filterSpec);

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
        EPStatementHandleCallback handle = new EPStatementHandleCallback(epStatementHandle, filterCallback);

        // Store stream for reuse
        pair = new Pair<EventStream, EPStatementHandleCallback>(eventStream, handle);
        eventStreams.put(filterSpec, pair);

        // Activate filter
        FilterValueSet filterValues = filterSpec.getValueSet(null);
        filterService.add(filterValues, handle);

        return eventStream;
    }

    /**
     * See the method of the same name in {@link StreamFactoryService}.
     * @param filterSpec is the filter definition
     * @param filterService is the filtering service
     */
    public void dropStream(FilterSpec filterSpec, FilterService filterService)
    {
        Pair<EventStream, EPStatementHandleCallback> pair = eventStreams.get(filterSpec);
        boolean isLast = eventStreams.dereference(filterSpec);
        if (isLast)
        {
            filterService.remove(pair.getSecond());
        }
    }
}
