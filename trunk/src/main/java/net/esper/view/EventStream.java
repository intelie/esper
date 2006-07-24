package net.esper.view;

import net.esper.event.EventBean;

/**
 * Streams are the basic I/O mechanism for the EventStream system.
 * Incoming data is placed into streams for consumption by queries,
 * and outgoing data passes through streams on its way to external consumers.
 * A EventStream has within it an OrderedData data collection, organized by arrival times of the incoming events.
 * The engine organizes incoming events sequentially by arrival,
 * and so the contents of each EventStream is gauranteed to have a natural ordering depending on arrival sequence
 * into the stream. Most simply, if there is a single feed into a stream,
 * the data in the stream will have ordering based on inputs from that feed.
 *
 * Ordinarily, streams are not accessed directly by client code. Instead, the EventProcessingRuntime interface API
 * is used. The events must conform to the type expected by the stream into which they are placed.
 * Data typing is stream-driven: two streams may expect data of the same event type, but different queries and operations
 * can be placed on the two, and they can get events from different sources.
 *
 * Streams are boundary objects within the event stream system.
 * That means that as top-level containers, they are the level at which the system will be distributed across multiple
 * machines, cloned, locked, and persisted. Streams have two faces then.
 * One is abstract: a stream represents a time-ordered collection of homogenous events following a published metadata
 * schema.
 * The other is concrete: streams can be pushed across the network, split across machines, and persisted by the system.
 * For writing queries that perform operations on the data in streams, the abstract face is all that is needed,
 * and that is what is made available in the public API.
 * The more physical operations on streams are coordinated by the engine itself, and managed by the system administrator.
 */
public interface EventStream extends OrderedEventCollection, Viewable
{
    /**
     * Callback from views to indicate that the stream can remove references to all events up to (and including) a particular index.
     * @param view is the view on the stream indicating which events can be let go.
     * @param index is the index into the stream's data register up to which that view no longer needs.
     */
    public void doneWith(StreamView view, long index);

    /**
     * Get the depth of data that can be held in a stream.
     * When more events than this number are placed onto a stream,
     * it will start to not hold references to the oldest events placed onto it.
     * A stream may get rid of such references even earlier if no view is using them, as well.
     * @return The maximum number of events the stream will hold in memory.
     */
    public int getDepth();

    /**
     * Get the name of this stream.
     * @return name of stream
     */
    public String getName();

    /**
     * Insert a new event onto the stream.
     * @param event to insert
     */
    public void insert(EventBean event);
}
