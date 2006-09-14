package net.esper.event;

import java.util.Map;

public class CompositeEventBean implements EventBean
{
    private final Map<String, EventBean> wrappedEvents;
    private final EventType eventType;

    public CompositeEventBean(Map<String, EventBean> wrappedEvents, EventType eventType)
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
        return eventType.getGetter(property).get(this);
    }

    public Object getUnderlying()
    {
        return wrappedEvents;
    }
}
