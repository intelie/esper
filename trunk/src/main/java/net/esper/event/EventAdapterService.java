package net.esper.event;

import java.util.Map;

/**
 * Interface for a service to resolve event names to event type.
 */
public interface EventAdapterService
{
    /**
     * Return the event type for a given event name, or null if none is registered for that name.
     * @param eventTypeAlias is the event type alias name to return type for
     * @return event type for named event, or null if unknown/unnamed type
     */
    public EventType getEventType(String eventTypeAlias);

    /**
     * Add an event type with the given alias and a given set of properties.
     * <p>
     * If the alias already exists with the same event property information, returns the
     * existing EventType instance.
     * <p>
     * If the alias already exists with different event property information, throws an exception.
     * <p>
     * If the alias does not already exists, adds the alias and constructs a new {@link net.esper.event.MapEventType}.
     * @param eventTypeAlias is the alias name for the event type
     * @param propertyTypes is the names and types of event properties
     * @return event type is the type added
     * @throws EventAdapterException if alias already exists and doesn't match property type info
     */
    public EventType addMapType(String eventTypeAlias, Map<String, Class> propertyTypes) throws EventAdapterException;

    /**
     * Creates a new anonymous EventType instance for an event type that contains a map of name value pairs.
     * The method accepts a Map that contains the property names as keys and Class objects as the values.
     * The Class instances represent the property types.
     * <p>
     * New instances are created by this method on every invocation. Clients to this method need to
     * cache the returned EventType instance to reuse EventType's for same-typed events.
     * <p>
     * @param propertyTypes is a map of String to Class objects
     * @return EventType implementation for map field names and value types
     */
    public EventType createAnonymousMapType(Map<String, Class> propertyTypes);

    /**
     * Create an event wrapper bean from a set of event properties (name and value objectes) stored in a Map.
     * @param properties is key-value pairs for the event properties
     * @param eventType is the type metadata for any maps of that type
     * @return EventBean instance
     */
    public EventBean createMapFromValues(Map<String, Object> properties, EventType eventType);

    /**
     * Create an aggregate event wrapper bean from a set of named events stored in a Map.
     * @param events is key-value pairs where the key is the event name and the value is the event
     * @param eventType is the type metadata for any maps of that type
     * @return EventBean instance
     */
    public EventBean createMapFromUnderlying(Map<String, EventBean> events, EventType eventType);

    /**
     * Add an event type with the given alias and Java fully-qualified class name.
     * <p>
     * If the alias already exists with the same class name, returns the existing EventType instance.
     * <p>
     * If the alias already exists with different class name, throws an exception.
     * <p>
     * If the alias does not already exists, adds the alias and constructs a new {@link net.esper.event.BeanEventType}.
     * @param eventTypeAlias is the alias name for the event type
     * @param fullyQualClassName is the fully qualified class name
     * @return event type is the type added
     * @throws EventAdapterException if alias already exists and doesn't match class names
     */
    public EventType addBeanType(String eventTypeAlias, String fullyQualClassName) throws EventAdapterException;

    /**
     * Add an event type with the given alias and Java class.
     * <p>
     * If the alias already exists with the same Class, returns the existing EventType instance.
     * <p>
     * If the alias already exists with different Class name, throws an exception.
     * <p>
     * If the alias does not already exists, adds the alias and constructs a new {@link net.esper.event.BeanEventType}.
     * @param eventTypeAlias is the alias name for the event type
     * @param clazz is the fully Java class
     * @return event type is the type added
     * @throws EventAdapterException if alias already exists and doesn't match class names
     */
    public EventType addBeanType(String eventTypeAlias, Class clazz) throws EventAdapterException;

    /**
     * Wrap the native event returning an {@link EventBean}.
     * @param event to be wrapped
     * @return event bean wrapping native underlying event
     */
    public EventBean adapterForBean(Object event);

    /**
     * Wrap the Map-type event returning an {@link EventBean} using the event type alias name
     * to identify the EventType that the event should carry.
     * @param event to be wrapped
     * @param eventTypeAlias alias for the event type of the event
     * @return event bean wrapping native underlying event
     * @throws EventAdapterException if the alias has not been declared, or the event cannot be wrapped using that
     * alias's event type
     */
    public EventBean adapterForMap(Map<String,Object> event, String eventTypeAlias) throws EventAdapterException;

    /**
     * Create an event type based on the original type passed in adding one or more properties.
     * @param originalType - event type to add property to
     * @param fieldNames - names of properties
     * @param fieldTypes - types of properties
     * @return new event type
     */
    public EventType createAddToEventType(EventType originalType, String[] fieldNames, Class[] fieldTypes);
}
