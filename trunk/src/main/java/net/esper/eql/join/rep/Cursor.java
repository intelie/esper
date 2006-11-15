package net.esper.eql.join.rep;

import net.esper.event.EventBean;

/**
 * This class supplies position information for {@link net.esper.eql.join.exec.LookupInstructionExec}
 * to use for iterating over events for lookup.
 */
public class Cursor
{
    private final EventBean event;
    private final int stream;
    private final Node node;

    /**
     * Ctor.
     * @param event is the current event
     * @param stream is the current stream
     * @param node is the node containing the set of events to which the event belongs to
     */
    public Cursor(EventBean event, int stream, Node node)
    {
        this.event = event;
        this.stream = stream;
        this.node = node;
    }

    /**
     * Supplies current event.
     * @return event
     */
    public EventBean getEvent()
    {
        return event;
    }

    /**
     * Returns current stream the event belongs to.
     * @return stream number for event
     */
    public int getStream()
    {
        return stream;
    }

    /**
     * Returns current result node the event belong to.
     * @return result node of event
     */
    public Node getNode()
    {
        return node;
    }
}
