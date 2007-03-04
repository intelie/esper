package net.esper.view.stream;

import net.esper.view.EventStream;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterService;
import net.esper.util.ManagedLock;
import net.esper.core.EPStatementHandle;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Service on top of the filter service for reuseing filter callbacks and their associated EventStream instances.
 * Same filter specifications (equal) do not need to be added to the filter service twice and the
 * EventStream instance that is the stream of events for that filter can be reused.
 * <p>
 * We are re-using streams such that views under such streams can be reused for efficient resource use.
 */
public interface StreamFactoryService
{
    /**
     * Create or reuse existing EventStream instance representing that event filter.
     * When called for some filters, should return same stream.
     * @param filterSpec event filter definition
     * @param filterService filter service to activate filter if not already active
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     * @param isJoin is indicatng whether the stream will participate in a join statement, information
     * necessary for stream reuse and multithreading concerns
     * @return event stream representing active filter
     */
    public EventStream createStream(FilterSpec filterSpec, FilterService filterService, EPStatementHandle epStatementHandle,
                                    boolean isJoin);

    /**
     * Drop the event stream associated with the filter passed in.
     * Throws an exception if already dropped.
     * @param filterSpec is the event filter definition associated with the event stream to be dropped
     * @param filterService to be used to deactivate filter when the last event stream is dropped
     * @param isJoin is indicatng whether the stream will participate in a join statement, information
     * necessary for stream reuse and multithreading concerns
     */
    public void dropStream(FilterSpec filterSpec, FilterService filterService, boolean isJoin);
}
