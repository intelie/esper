package net.esper.view.stream;

import net.esper.collection.Pair;
import net.esper.collection.RefCountedMap;
import net.esper.core.EPStatementHandle;
import net.esper.core.EPStatementHandleCallback;
import net.esper.event.EventBean;
import net.esper.filter.FilterHandleCallback;
import net.esper.filter.FilterService;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterValueSet;
import net.esper.view.EventStream;
import net.esper.view.ZeroDepthStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.IdentityHashMap;


/**
 * Service implementation to reuse or not reuse event streams and existing filters depending on
 * the type of statement.
 * <p>
 * For non-join statements, the class manages the reuse of event streams when filters match, and thus
 * when an event stream is reused such can be the views under the stream. For joins however, this can lead to
 * problems in multithread-safety since the statement resource lock would then have to be multiple locks,
 * i.e. the reused statement's resource lock and the join statement's own lock, at a minimum.
 * <p>
 * For join statements, always creating a new event stream and
 * therefore not reusing view resources, for use with joins.
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
public class StreamFactorySvcImpl implements StreamFactoryService
{
    private static Log log = LogFactory.getLog(StreamFactorySvcImpl.class);

    // Using identify hash map - ignoring the equals semantics on filter specs
    // Thus two filter specs objects are always separate entries in the map
    private final IdentityHashMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>> eventStreamsIdentity;

    // Using a reference-counted map for non-join statements
    private final RefCountedMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>> eventStreamsRefCounted;

    /**
     * Ctor.
     */
    public StreamFactorySvcImpl()
    {
        this.eventStreamsRefCounted = new RefCountedMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>>();
        this.eventStreamsIdentity = new IdentityHashMap<FilterSpec, Pair<EventStream, EPStatementHandleCallback>>();
    }

    /**
     * See the method of the same name in {@link net.esper.view.stream.StreamFactoryService}. Always attempts to reuse an existing event stream.
     * May thus return a new event stream or an existing event stream depending on whether filter criteria match.
     * @param filterSpec is the filter definition
     * @param epStatementHandle is the statement resource lock
     * @return newly createdStatement event stream, not reusing existing instances
     */
    public EventStream createStream(FilterSpec filterSpec, FilterService filterService, EPStatementHandle epStatementHandle, boolean isJoin)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".createStream hashCode=" + filterSpec.hashCode() + " filter=" + filterSpec);
        }

        // Check if a stream for this filter already exists
        Pair<EventStream, EPStatementHandleCallback> pair = null;
        if (isJoin)
        {
            pair = eventStreamsIdentity.get(filterSpec);
        }
        else
        {
            pair = eventStreamsRefCounted.get(filterSpec);
        }

        // If pair exists, either reference count or illegal state
        if (pair != null)
        {
            if (isJoin)
            {
                throw new IllegalStateException("Filter spec object already found in collection");
            }
            else
            {
                log.debug(".createStream filter already found");
                eventStreamsRefCounted.reference(filterSpec);
                return pair.getFirst();                
            }
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
        if (isJoin)
        {
            eventStreamsIdentity.put(filterSpec, pair);
        }
        else
        {
            eventStreamsRefCounted.put(filterSpec, pair);
        }
        
        // Activate filter
        FilterValueSet filterValues = filterSpec.getValueSet(null);
        filterService.add(filterValues, handle);

        return eventStream;
    }

    /**
     * See the method of the same name in {@link net.esper.view.stream.StreamFactoryService}.
     * @param filterSpec is the filter definition
     */
    public void dropStream(FilterSpec filterSpec, FilterService filterService, boolean isJoin)
    {
        Pair<EventStream, EPStatementHandleCallback> pair = null;

        if (isJoin)
        {
            pair = eventStreamsIdentity.get(filterSpec);
            if (pair == null)
            {
                throw new IllegalStateException("Filter spec object not in collection");
            }
            eventStreamsIdentity.remove(filterSpec);
            filterService.remove(pair.getSecond());
        }
        else
        {
            pair = eventStreamsRefCounted.get(filterSpec);
            boolean isLast = eventStreamsRefCounted.dereference(filterSpec);
            if (isLast)
            {
                filterService.remove(pair.getSecond());
            }
        }
    }
}
