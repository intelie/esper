package net.esper.event;

/**
 * Get property values from an event instance for a given event property.
 * Instances that implement this interface are usually bound to a particular {@link EventType} and cannot
 * be used to access {@link EventBean} instances of a different type.
 */
public interface EventPropertyGetter
{
    /**
     * Return the value for the property in the event object specified when the instance was obtained.
     * Useful for fast access to event properties. Throws a PropertyAccessException if the getter instance
     * doesn't match the EventType it was obtained from, and to indicate other property access problems.
     * @param eventBean is the event to get the value of a property from
     * @return value of property in event
     * @throws PropertyAccessException to indicate that property access failed
     */
    public Object get(EventBean eventBean) throws PropertyAccessException;
}

