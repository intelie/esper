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

    /**
     * Returns true if the property exists, or false if the type does not have such a property.
     * <p>
     * Useful for dynamic properties of the syntax "property?" and the dynamic nested/indexed/mapped versions.
     * Dynamic nested properties follow the syntax "property?.nested" which is equivalent to "property?.nested?".
     * If any of the properties in the path of a dynamic nested property return null, the dynamic nested property
     * does not exists and the method returns false.
     * <p>
     * For non-dynamic properties, this method always returns true since a getter would not be available
     * unless
     * @param eventBean is the event to check if the dynamic property exists
     * @return indictor whether the property exists, always true for non-dynamic (default) properties 
     */
    public boolean isExistsProperty(EventBean eventBean);
}

