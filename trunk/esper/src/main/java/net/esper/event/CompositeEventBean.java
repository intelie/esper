package net.esper.event;

import java.util.Map;

/**
 * Event bean wrapper for events that consists of a Map of name tags as key values and
 * event bean wrappers as value objects, for use by pattern expressions.
 */
public class CompositeEventBean implements EventBeanSPI
{
    private final Map<String, EventBean> wrappedEvents;
    private final EventType eventType;
    private Object eventBeanId;

    /**
     * Ctor.
     * @param wrappedEvents is the event properties map with keys being the property name tages
     * and values the wrapped event
     * @param eventType is the event type instance for the wrapper
     */
    public CompositeEventBean(Map<String, EventBean> wrappedEvents, EventType eventType, Object eventBeanId)
    {
        this.wrappedEvents = wrappedEvents;
        this.eventType = eventType;
        this.eventBeanId = eventBeanId;
    }

    public Object getEventBeanId()
    {
        return eventBeanId;
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
        return eventType.getGetter(property).get(this);
    }

    public Object getUnderlying()
    {
        return wrappedEvents;
    }
}
