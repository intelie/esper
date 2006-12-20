package net.esper.view.stream;

import net.esper.view.EventStream;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterService;

/**
 * Service on top of the filter service for reuseing filter callbacks and their associated EventStream instances.
 * Same filter specifications (equal) do not need to be added to the filter service twice and the
 * EventStream instance that is the stream of events for that filter can be reused.
 */
public interface StreamReuseService
{
    /**
     * Create or reuse existing EventStream instance representing that event filter.
     * When called for some filters, should return same stream.
     * @param filterSpec event filter definition
     * @param filterService filter service to activate filter if not already active
     * @return event stream representing active filter
     */
    public EventStream createStream(FilterSpec filterSpec, FilterService filterService);

    /**
     * Drop the event stream associated with the filter passed in.
     * Throws an exception if already dropped.
     * @param filterSpec is the event filter definition associated with the event stream to be dropped
     * @param filterService to be used to deactivate filter when the last event stream is dropped
     */
    public void dropStream(FilterSpec filterSpec, FilterService filterService);
}
