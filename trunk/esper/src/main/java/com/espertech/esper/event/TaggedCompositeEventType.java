package com.espertech.esper.event;

import com.espertech.esper.collection.Pair;

import java.util.Map;

/**
 * Interface for composite event type in which each property is itself an event.
 * <p>
 * For use with patterns in which pattern tags are properties in a result event and property values
 * are the event itself that is matching in a pattern.
 */
public interface TaggedCompositeEventType
{
    /**
     * Returns the event types for each composing event.
     * @return map of tag name and event type
     */
    public Map<String, Pair<EventType, String>> getTaggedEventTypes();
}
