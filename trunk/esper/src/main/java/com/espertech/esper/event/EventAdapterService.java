package com.espertech.esper.event;

import java.util.Map;

import org.w3c.dom.Node;
import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.client.ConfigurationEventTypeLegacy;
import com.espertech.esper.client.Configuration;

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
    public EventType getExistsTypeByAlias(String eventTypeAlias);

    /**
     * Add an event type with the given alias and a given set of properties.
     * <p>
     * If the alias already exists with the same event property information, returns the
     * existing EventType instance.
     * <p>
     * If the alias already exists with different event property information, throws an exception.
     * <p>
     * If the alias does not already exists, adds the alias and constructs a new {@link com.espertech.esper.event.MapEventType}.
     * @param eventTypeAlias is the alias name for the event type
     * @param propertyTypes is the names and types of event properties
     * @return event type is the type added
     * @throws EventAdapterException if alias already exists and doesn't match property type info
     */
    public EventType addMapType(String eventTypeAlias, Map<String, Class> propertyTypes) throws EventAdapterException;
    
    
    /**
     * Add an event type with the given alias and the given underlying event type, 
     * as well as the additional given properties.
     * @param eventTypeAlias is the alias name for the event type
     * @param underlyingEventType is the event type for the event type that this wrapper wraps
     * @param propertyTypes is the names and types of any additional properties 
     * @return eventType is the type added
     * @throws EventAdapterException if alias already exists and doesn't match this type's info
     */
    public EventType addWrapperType(String eventTypeAlias, EventType underlyingEventType, Map<String, Class> propertyTypes) throws EventAdapterException;
    
    /**
     * Creates a new anonymous EventType instance for an event type that contains a map of name value pairs.
     * The method accepts a Map that contains the property names as keys and Class objects as the values.
     * The Class instances represent the property types.
     * <p>
     * New instances are createdStatement by this method on every invocation. Clients to this method need to
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
     * Creata a wrapper around an event and some additional properties
     * @param event is the wrapped event
     * @param properties are the additional properties
     * @param eventType os the type metadata for any wrappers of this type
     * @return wrapper event bean
     */
    public EventBean createWrapper(EventBean event, Map<String, Object> properties, EventType eventType);

    /**
     * Add an event type with the given alias and Java fully-qualified class name.
     * <p>
     * If the alias already exists with the same class name, returns the existing EventType instance.
     * <p>
     * If the alias already exists with different class name, throws an exception.
     * <p>
     * If the alias does not already exists, adds the alias and constructs a new {@link com.espertech.esper.event.BeanEventType}.
     * <p>
     * Takes into account all event-type-auto-alias-package names supplied and
     * attempts to resolve the class name via the packages if the direct resolution failed. 
     * @param eventTypeAlias is the alias name for the event type
     * @param fullyQualClassName is the fully qualified class name
     * @param considerAutoAlias whether auto-alias by Java packages should be considered
     * @return event type is the type added
     * @throws EventAdapterException if alias already exists and doesn't match class names
     */
    public EventType addBeanType(String eventTypeAlias, String fullyQualClassName, boolean considerAutoAlias) throws EventAdapterException;

    /**
     * Add an event type with the given alias and Java class.
     * <p>
     * If the alias already exists with the same Class, returns the existing EventType instance.
     * <p>
     * If the alias already exists with different Class name, throws an exception.
     * <p>
     * If the alias does not already exists, adds the alias and constructs a new {@link com.espertech.esper.event.BeanEventType}.
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
    public EventBean adapterForMap(Map event, String eventTypeAlias) throws EventAdapterException;

    /**
     * Create an event type based on the original type passed in adding one or more properties.
     * @param originalType - event type to add property to
     * @param fieldNames - names of properties
     * @param fieldTypes - types of properties
     * @return new event type
     */
    public EventType createAddToEventType(EventType originalType, String[] fieldNames, Class[] fieldTypes);

    /**
     * Returns an adapter for the XML DOM document that exposes it's data as event properties for use in statements.
     * @param node is the node to wrap
     * @return event wrapper for document
     */
    public EventBean adapterForDOM(Node node);

    /**
     * Creates an unnamed composite event type with event properties that are name-value pairs
     * with values being other event types. Pattern statement generate events of such type.
     * @param taggedEventTypes is a map of name keys and event type values
     * @return event type representing a composite event
     */
    public EventType createAnonymousCompositeType(Map<String, EventType> taggedEventTypes);

    /**
     * Creates a wrapper for a composite event type. The wrapper wraps an event that
     * consists of name-value pairs in which the values are other already-wrapped events.
     * @param eventType is the composite event type
     * @param taggedEvents is the name-event map
     * @return wrapper for composite event
     */
    public EventBean adapterForCompositeEvent(EventType eventType, Map<String, EventBean> taggedEvents);

    /**
     * Create a new anonymous event type with the given underlying event type, 
     * as well as the additional given properties.
     * @param underlyingEventType is the event type for the event type that this wrapper wraps
     * @param propertyTypes is the names and types of any additional properties 
     * @return eventType is the type createdStatement
     * @throws EventAdapterException if alias already exists and doesn't match this type's info
     */
    public EventType createAnonymousWrapperType(EventType underlyingEventType, Map<String, Class> propertyTypes) throws EventAdapterException;

    /**
     * Adds an XML DOM event type.
     * @param eventTypeAlias is the alias to add the type for
     * @param configurationEventTypeXMLDOM is the XML DOM config info
     * @return event type
     */
    public EventType addXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM);

    /**
     * Sets the configured legacy Java class information.
     * @param classLegacyInfo is configured legacy 
     */
    public void setClassLegacyConfigs(Map<String, ConfigurationEventTypeLegacy> classLegacyInfo);

    /**
     * Sets the resolution style for case-sentitivity.
     * @param classPropertyResolutionStyle for resolving properties.
     */    
    public void setDefaultPropertyResolutionStyle(Configuration.PropertyResolutionStyle classPropertyResolutionStyle);

    /**
     * Adds a Java package name of a package that Java event classes reside in.
     * @param javaPackageName is the fully-qualified Java package name of the Java package that event classes reside in
     */
    public void addAutoAliasPackage(String javaPackageName);
}
