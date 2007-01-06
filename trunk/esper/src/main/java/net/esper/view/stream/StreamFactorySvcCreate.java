package net.esper.view.stream;

import net.esper.filter.*;
import net.esper.view.EventStream;
import net.esper.view.ZeroDepthStream;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.util.ManagedLock;
import net.esper.core.EPStatementHandleCallback;
import net.esper.core.EPStatementHandle;

import java.util.IdentityHashMap;

/**
 * Service implementation to create a new event stream and register newly with the filter service.
 * <p>
 * Thus views under the newly created event stream are not reused across statements, for multithread-safety.
 */
public class StreamFactorySvcCreate implements StreamFactoryService
{
    // Using identify hash map - ignoring the equals semantics on filter specs
    // Thus two filter specs objects are always separate entries in the map
    private IdentityHashMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>> eventStreams;

    /**
     * Ctor.
     */
    public StreamFactorySvcCreate()
    {
        this.eventStreams = new IdentityHashMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>>();
    }

    public EventStream createStream(FilterSpec filterSpec, FilterService filterService, EPStatementHandle epStatementHandle)
    {
        // Check if a stream for this filter already exists
        Pair<EventStream, EPStatementHandleCallback> pair = eventStreams.get(filterSpec);

        if (pair != null)
        {
            throw new IllegalStateException("Filter spec object already found in collection");
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

    public void dropStream(FilterSpec filterSpec, FilterService filterService)
    {
        Pair<EventStream, EPStatementHandleCallback> pair = eventStreams.get(filterSpec);
        if (pair == null)
        {
            throw new IllegalStateException("Filter spec object not in collection");
        }
        eventStreams.remove(filterSpec);
        filterService.remove(pair.getSecond());
    }
}
