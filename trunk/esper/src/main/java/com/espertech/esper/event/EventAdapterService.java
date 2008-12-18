/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.*;
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.plugin.PlugInEventRepresentation;
import com.espertech.esper.collection.Pair;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for a service to resolve event names to event type.
 */
public interface EventAdapterService
{
    /**
     * Creates a thin adaper for an event object given an event type.
     * @param bean event object
     * @param eventType event type
     * @return event
     */
    public EventBean adapterForTypedBean(Object bean, BeanEventType eventType);

    /**
     * Adds an event type to the registery available for use, and originating outside as a non-adapter.
     * @param alias to add an event type under
     * @param eventType the type to add
     * @throws EventAdapterException if the alias is already in used by another type
     */
    public void addTypeByAlias(String alias, EventType eventType) throws EventAdapterException;

    /**
     * Return the event type for a given event name, or null if none is registered for that name.
     * @param eventTypeAlias is the event type alias name to return type for
     * @return event type for named event, or null if unknown/unnamed type
     */
    public EventType getExistsTypeByAlias(String eventTypeAlias);

    /**
     * Return all known event types.
     * @return event types
     */
    public EventType[] getAllTypes();

    /**
     * Add an event type with the given alias and a given set of properties,
     * wherein properties may itself be Maps, nested and strongly-typed.
     * <p>
     * If the alias already exists with the same event property information, returns the
     * existing EventType instance.
     * <p>
     * If the alias already exists with different event property information, throws an exception.
     * <p>
     * If the alias does not already exists, adds the alias and constructs a new {@link com.espertech.esper.event.MapEventType}.
     * @param eventTypeAlias is the alias name for the event type
     * @param propertyTypes is the names and types of event properties
     * @param optionalSupertype an optional set of Map event type aliases that are supertypes to the type
     * @return event type is the type added
     * @param isConfigured if the type is application-configured
     * @param namedWindow if the type is from a named window
     * @param insertInto if inserting into a stream
     * @throws EventAdapterException if alias already exists and doesn't match property type info
     */
    public EventType addNestableMapType(String eventTypeAlias, Map<String, Object> propertyTypes, Set<String> optionalSupertype, boolean isConfigured, boolean namedWindow, boolean insertInto) throws EventAdapterException;

    /**
     * Add an event type with the given alias and the given underlying event type,
     * as well as the additional given properties.
     * @param eventTypeAlias is the alias name for the event type
     * @param underlyingEventType is the event type for the event type that this wrapper wraps
     * @param propertyTypes is the names and types of any additional properties
     * @param isNamedWindow if the type is from a named window
     * @param isInsertInto if inserting into a stream
     * @return eventType is the type added
     * @throws EventAdapterException if alias already exists and doesn't match this type's info
     */
    public EventType addWrapperType(String eventTypeAlias, EventType underlyingEventType, Map<String, Object> propertyTypes, boolean isNamedWindow, boolean isInsertInto) throws EventAdapterException;

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
    public EventType createAnonymousMapType(Map<String, Object> propertyTypes);

    /**
     * Creata a wrapper around an event and some additional properties
     * @param event is the wrapped event
     * @param properties are the additional properties
     * @param eventType os the type metadata for any wrappers of this type
     * @return wrapper event bean
     */
    public EventBean adaptorForWrapper(EventBean event, Map<String, Object> properties, EventType eventType);

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
     * @param isConfigured if the class is application-configured
     * @return event type is the type added
     * @throws EventAdapterException if alias already exists and doesn't match class names
     */
    public EventType addBeanType(String eventTypeAlias, Class clazz, boolean isConfigured) throws EventAdapterException;

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
    public EventBean adapterForMap(Map<String, Object> event, String eventTypeAlias) throws EventAdapterException;

    /**
     * Create an event map bean from a set of event properties (name and value objectes) stored in a Map.
     * @param properties is key-value pairs for the event properties
     * @param eventType is the type metadata for any maps of that type
     * @return EventBean instance
     */
    public EventBean adaptorForTypedMap(Map<String, Object> properties, EventType eventType);

    /**
     * Returns an adapter for the XML DOM document that exposes it's data as event properties for use in statements.
     * @param node is the node to wrap
     * @return event wrapper for document
     */
    public EventBean adapterForDOM(Node node);

    /**
     * Create a new anonymous event type with the given underlying event type,
     * as well as the additional given properties.
     * @param underlyingEventType is the event type for the event type that this wrapper wraps
     * @param propertyTypes is the names and types of any additional properties
     * @return eventType is the type createdStatement
     * @throws EventAdapterException if alias already exists and doesn't match this type's info
     */
    public EventType createAnonymousWrapperType(EventType underlyingEventType, Map<String, Object> propertyTypes) throws EventAdapterException;

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

    /**
     * Returns a subset of the functionality of the service specific to creating POJO bean event types.
     * @return bean event type factory
     */
    public BeanEventTypeFactory getBeanEventTypeFactory();

    /**
     * Add a plug-in event representation.
     * @param eventRepURI URI is the unique identifier for the event representation
     * @param pluginEventRep is the instance
     */
    public void addEventRepresentation(URI eventRepURI, PlugInEventRepresentation pluginEventRep);

    /**
     * Adds a plug-in event type.
     * @param alias is the name of the event type
     * @param resolutionURIs is the URIs of plug-in event representations, or child URIs of such
     * @param initializer is configs for the type
     * @return type
     */
    public EventType addPlugInEventType(String alias, URI[] resolutionURIs, Serializable initializer);

    /**
     * Returns an event sender for a specific type, only generating events of that type.
     * @param runtimeEventSender the runtime handle for sending the wrapped type
     * @param eventTypeAlias is the name of the event type to return the sender for
     * @return event sender that is static, single-type
     */
    public EventSender getStaticTypeEventSender(EPRuntimeEventSender runtimeEventSender, String eventTypeAlias);

    /**
     * Returns an event sender that dynamically decides what the event type for a given object is.
     * @param runtimeEventSender the runtime handle for sending the wrapped type
     * @param uri is for plug-in event representations to provide implementations, if accepted, to make a wrapped event
     * @return event sender that is dynamic, multi-type based on multiple event bean factories provided by
     * plug-in event representations
     */
    public EventSender getDynamicTypeEventSender(EPRuntimeEventSender runtimeEventSender, URI[] uri);

    /**
     * Update a given Map  event type.
     * @param mapEventTypeAlias alias to update
     * @param typeMap additional properties to add, nesting allowed
     * @throws EventAdapterException when the type is not found or is not a Map
     */
    public void updateMapEventType(String mapEventTypeAlias, Map<String, Object> typeMap) throws EventAdapterException;

    /**
     * Casts event type of a list of events to either Wrapper or Map type.
     * @param events to cast
     * @param targetType target type
     * @return type casted event array
     */
    public EventBean[] typeCast(List<EventBean> events, EventType targetType);

    /**
     * Removes an event type by a given alias indicating by the return value whether the type
     * was found or not.
     * <p>
     * Does not uncache an existing class loaded by a JVM. Does remove XML root element names.
     * Does not handle value-add event types.
     * @param alias to remove
     * @return true if found and removed, false if not found
     */
    public boolean removeType(String alias);

    /**
     * Creates an anonymous map that has no alias, however in a fail-over scenario
     * events of this type may be recoverable and therefore the type is only semi-anonymous,
     * identified by the tags and event type aliases used. 
     * @param taggedEventTypes simple type per property name
     * @param arrayEventTypes array type per property name
     * @param isUsedByChildViews if the type is going to be in used by child views
     * @return event type
     */
    public EventType createSemiAnonymousMapType(Map<String, Pair<EventType, String>> taggedEventTypes, Map<String, Pair<EventType, String>> arrayEventTypes, boolean isUsedByChildViews);
}
