package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.event.EventType;

/**
 * Interface for processors of select-clause items, implementors are computing results based on matching events.
 */
public interface SelectExprProcessor
{
    /**
     * Returns the event type that represents the select-clause items.
     * @return event type representing select-clause items
     */
    public EventType getResultEventType();

    /**
     * Computes the select-clause results and returns an event of the result event type that contains, in it's
     * properties, the selected items.
     * @param eventsPerStream
     * @return event with properties containing selected items
     */
    public EventBean process(EventBean[] eventsPerStream);
}
