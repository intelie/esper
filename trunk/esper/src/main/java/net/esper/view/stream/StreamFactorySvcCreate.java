package net.esper.view.stream;

import net.esper.collection.Pair;
import net.esper.core.EPStatementHandle;
import net.esper.core.EPStatementHandleCallback;
import net.esper.event.EventBean;
import net.esper.filter.FilterHandleCallback;
import net.esper.filter.FilterService;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterValueSet;
import net.esper.view.EventStream;
import net.esper.view.ZeroDepthStream;

import java.util.IdentityHashMap;

/**
 * Service implementation to create a new event stream and register newly with the filter service.
 * <p>
 * Thus views under the newly created event stream are not reused across statements, for multithread-safety.
 */
public class StreamFactorySvcCreate
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

    /**
     * See the method of the same name in {@link StreamFactoryService}. Always creates a new event stream.
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

    /**
     * See the method of the same name in {@link StreamFactoryService}.
     * @param filterSpec is the filter definition
     * @param filterService is the filtering service
     */
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
