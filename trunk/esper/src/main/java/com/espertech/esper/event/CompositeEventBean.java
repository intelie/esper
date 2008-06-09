package com.espertech.esper.event;

import java.util.Map;

/**
 * Event bean wrapper for events that consists of a Map of name tags as key values and
 * event bean wrappers as value objects, for use by pattern expressions.
 */
public class CompositeEventBean implements EventBean, TaggedCompositeEventBean
{
    private final Map<String, Object> wrappedEvents;
    private final EventType eventType;

    /**
     * Ctor.
     * @param wrappedEvents is the event properties map with keys being the property name tages
     * and values the wrapped event
     * @param eventType is the event type instance for the wrapper
     */
    public CompositeEventBean(Map<String, Object> wrappedEvents, EventType eventType)
    {
        this.wrappedEvents = wrappedEvents;
        this.eventType = eventType;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Object get(String property) throws PropertyAccessException
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter == null)
        {
            throw new IllegalArgumentException("Property named '" + property + "' is not a valid property name for this type");
        }
        return getter.get(this);
    }

    public Object getUnderlying()
    {
        return wrappedEvents;
    }

    public EventBean getEventBean(String property)
    {
        return (EventBean) wrappedEvents.get(property);
    }
}
