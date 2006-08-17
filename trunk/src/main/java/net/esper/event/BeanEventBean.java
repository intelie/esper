package net.esper.event;

/**
 * Wrapper for Java bean (POJO or regular) Java objects the represent events.
 * Allows access to event properties, which is done through the getter supplied by the event type.
 * {@link EventType} instances containing type information are obtained from {@link BeanEventAdapter}.
 * Two BeanEventBean instances are equal if they have the same event type and refer to the same instance of event object.
 * Clients that need to compute equality between Java beans wrapped by this class need to obtain the underlying object.
 */
class BeanEventBean implements EventBean
{
    private EventType eventType;
    private Object event;

    /**
     * Constructor.
     * @param event is the event object
     * @param eventType is the schema information for the event object.
     */
    protected BeanEventBean(Object event, EventType eventType)
    {
        this.eventType = eventType;
        this.event = event;
    }

    public Object getUnderlying()
    {
        return event;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public final boolean equals(Object other)
    {
        if (!(other instanceof BeanEventBean))
        {
            return false;
        }
        BeanEventBean o = (BeanEventBean) other;
        if (other == this)
        {
            return true;
        }
        if (o.eventType != eventType)
        {
            return false;
        }
        if (o.event != event)
        {
            return false;
        }
        return true;
    }

    public Object get(String property) throws IllegalArgumentException, PropertyAccessException
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter == null)
        {
            throw new PropertyAccessException("Property named '" + property + "' is not a valid property name for this type");
        }
        return getter.get(this);
    }

    public String toString()
    {
        return "BeanEventBean" +
               " eventType=" + eventType +
               " bean=" + event;
    }
}
