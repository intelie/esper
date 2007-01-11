package net.esper.view.stream;

import net.esper.filter.FilterSpec;
import net.esper.filter.FilterService;
import net.esper.filter.FilterHandleCallback;
import net.esper.filter.FilterValueSet;
import net.esper.view.EventStream;
import net.esper.view.ZeroDepthStream;
import net.esper.core.EPStatementHandleCallback;
import net.esper.core.EPStatementHandle;
import net.esper.collection.Pair;
import net.esper.collection.RefCountedMap;
import net.esper.event.EventBean;

/**
 * Service implementation to reuse or not reuse event streams and existing filters depending on
 * the type of statement.
 * <p>
 * Works together with {@link StreamFactorySvcReuse} for the reuse of event streams when filters match, and thus
 * when an event stream is reused such can be the views under the stream. For joins however, this can lead to
 * problems in multithread-safety since the statement resource lock would then have to be multiple locks,
 * i.e. the reused statement's resource lock and the join statement's own lock, at a minimum.
 * <p>
 * Works together with {@link StreamFactorySvcCreate} for always creating a new event stream and
 * therefore not reusing view resources, for use with joins.
 */
public class StreamFactorySvcImpl implements StreamFactoryService
{
    private StreamFactorySvcReuse reuseService;
    private StreamFactorySvcCreate createService;

    /**
     * Ctor.
     */
    public StreamFactorySvcImpl()
    {
        reuseService = new StreamFactorySvcReuse();
        createService = new StreamFactorySvcCreate(); 
    }

    public EventStream createStream(FilterSpec filterSpec, FilterService filterService, EPStatementHandle epStatementHandle, boolean isJoin)
    {
        // For
        if (isJoin)
        {
            return createService.createStream(filterSpec, filterService, epStatementHandle);
        }
        else
        {
            return reuseService.createStream(filterSpec, filterService, epStatementHandle);
        }
    }

    public void dropStream(FilterSpec filterSpec, FilterService filterService, boolean isJoin)
    {
        if (isJoin)
        {
            createService.dropStream(filterSpec, filterService);
        }
        else
        {
            reuseService.dropStream(filterSpec, filterService);
        }
    }
}
