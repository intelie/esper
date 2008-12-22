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
import com.espertech.esper.collection.Pair;
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.event.xml.*;
import com.espertech.esper.plugin.*;
import com.espertech.esper.util.URIUtil;
import com.espertech.esper.util.UuidGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation for resolving event name to event type.
 * <p>
 * The implementation assigned a unique identifier to each event type.
 * For Class-based event types, only one EventType instance and one event type id exists for the same class.
 * <p>
 * Alias names must be unique, that is an alias name must resolve to a single event type.
 * <p>
 * Each event type can have multiple aliases defined for it. For example, expressions such as
 * "select * from A" and "select * from B"
 * in which A and B are aliases for the same class X the select clauses each fireStatementStopped for events of type X.
 * In summary, aliases A and B point to the same underlying event type and therefore event type id.
 */
public class EventAdapterServiceImpl implements EventAdapterService
{
    private static Log log = LogFactory.getLog(EventAdapterServiceImpl.class);

    private final ConcurrentHashMap<Class, BeanEventType> typesPerJavaBean;
    private final Map<String, EventType> aliasToTypeMap;
    private final Map<String, PlugInEventTypeHandler> aliasToHandlerMap;
    private BeanEventAdapter beanEventAdapter;
    private Map<String, EventType> xmldomRootElementNames;
    private LinkedHashSet<String> javaPackageNames;
    private final Map<URI, PlugInEventRepresentation> plugInRepresentations;

    /**
     * Ctor.
     */
    public EventAdapterServiceImpl()
    {
        aliasToTypeMap = new HashMap<String, EventType>();
        xmldomRootElementNames = new HashMap<String, EventType>();
        javaPackageNames = new LinkedHashSet<String>();
        aliasToHandlerMap = new HashMap<String, PlugInEventTypeHandler>();

        // Share the mapping of class to type with the type creation for thread safety
        typesPerJavaBean = new ConcurrentHashMap<Class, BeanEventType>();
        beanEventAdapter = new BeanEventAdapter(typesPerJavaBean, this);
        plugInRepresentations = new HashMap<URI, PlugInEventRepresentation>();
    }

    public EventType[] getAllTypes()
    {
        Collection<EventType> types = aliasToTypeMap.values();
        return types.toArray(new EventType[types.size()]);
    }

    public synchronized void addTypeByAlias(String alias, EventType eventType) throws EventAdapterException
    {
        if (aliasToTypeMap.containsKey(alias))
        {
            throw new EventAdapterException("Alias by name '" + alias + "' already exists");
        }
        aliasToTypeMap.put(alias, eventType);
    }

    public void addEventRepresentation(URI eventRepURI, PlugInEventRepresentation pluginEventRep) throws EventAdapterException
    {
        if (plugInRepresentations.containsKey(eventRepURI))
        {
            throw new EventAdapterException("Plug-in event representation URI by name " + eventRepURI + " already exists");
        }
        plugInRepresentations.put(eventRepURI, pluginEventRep);
    }

    public EventType addPlugInEventType(String alias, URI[] resolutionURIs, Serializable initializer) throws EventAdapterException
    {
        if (aliasToTypeMap.containsKey(alias))
        {
            throw new EventAdapterException("Event type named '" + alias +
                    "' has already been declared");
        }

        PlugInEventRepresentation handlingFactory = null;
        URI handledEventTypeURI = null;

        if ((resolutionURIs == null) || (resolutionURIs.length == 0))
        {
            throw new EventAdapterException("Event type named '" + alias + "' could not be created as" +
                    " no resolution URIs for dynamic resolution of event type aliases through a plug-in event representation have been defined");
        }

        for (URI eventTypeURI : resolutionURIs)
        {
            // Determine a list of event representations that may handle this type
            Map<URI, Object> allFactories = new HashMap<URI, Object>(plugInRepresentations);
            Collection<Map.Entry<URI, Object>> factories = URIUtil.filterSort(eventTypeURI, allFactories);

            if (factories.isEmpty())
            {
                continue;
            }

            // Ask each in turn to accept the type (the process of resolving the type)
            for (Map.Entry<URI, Object> entry : factories)
            {
                PlugInEventRepresentation factory = (PlugInEventRepresentation) entry.getValue();
                PlugInEventTypeHandlerContext context = new PlugInEventTypeHandlerContext(eventTypeURI, initializer, alias);
                if (factory.acceptsType(context))
                {
                    handlingFactory = factory;
                    handledEventTypeURI = eventTypeURI;
                    break;
                }
            }

            if (handlingFactory != null)
            {
                break;
            }
        }

        if (handlingFactory == null)
        {
            throw new EventAdapterException("Event type named '" + alias + "' could not be created as none of the " +
                    "registered plug-in event representations accepts any of the resolution URIs '" + Arrays.toString(resolutionURIs)
                    + "' and initializer");
        }

        PlugInEventTypeHandlerContext context = new PlugInEventTypeHandlerContext(handledEventTypeURI, initializer, alias);
        PlugInEventTypeHandler handler = handlingFactory.getTypeHandler(context);
        if (handler == null)
        {
            throw new EventAdapterException("Event type named '" + alias + "' could not be created as no handler was returned");
        }

        EventType eventType = handler.getType();
        aliasToTypeMap.put(alias, eventType);
        aliasToHandlerMap.put(alias, handler);

        return eventType;
    }

    public EventSender getStaticTypeEventSender(EPRuntimeEventSender runtimeEventSender, String eventTypeAlias) throws EventTypeException
    {
        EventType eventType = aliasToTypeMap.get(eventTypeAlias);
        if (eventType == null)
        {
            throw new EventTypeException("Event type named '" + eventTypeAlias + "' could not be found");
        }

        // handle built-in types
        if (eventType instanceof BeanEventType)
        {
            return new EventSenderBean(runtimeEventSender, (BeanEventType) eventType, this);
        }
        if (eventType instanceof MapEventType)
        {
            return new EventSenderMap(runtimeEventSender, (MapEventType) eventType);
        }
        if (eventType instanceof BaseXMLEventType)
        {
            return new EventSenderXMLDOM(runtimeEventSender, (BaseXMLEventType) eventType);
        }

        PlugInEventTypeHandler handlers = aliasToHandlerMap.get(eventTypeAlias);
        if (handlers != null)
        {
            return handlers.getSender(runtimeEventSender);
        }
        throw new EventTypeException("An event sender for event type named '" + eventTypeAlias + "' could not be created as the type is internal");
    }

    public void updateMapEventType(String mapEventTypeAlias, Map<String, Object> typeMap) throws EventAdapterException
    {
        EventType type = aliasToTypeMap.get(mapEventTypeAlias);
        if (type == null)
        {
            throw new EventAdapterException("Event type alias '" + mapEventTypeAlias + "' has not been declared");
        }
        if (!(type instanceof MapEventType))
        {
            throw new EventAdapterException("Event type by alias '" + mapEventTypeAlias + "' is not a Map event type");
        }

        MapEventType mapEventType = (MapEventType) type;
        mapEventType.addAdditionalProperties(typeMap, this);
    }

    public EventSender getDynamicTypeEventSender(EPRuntimeEventSender epRuntime, URI[] uri) throws EventTypeException
    {
        List<EventSenderURIDesc> handlingFactories = new ArrayList<EventSenderURIDesc>();
        for (URI resolutionURI : uri)
        {
            // Determine a list of event representations that may handle this type
            Map<URI, Object> allFactories = new HashMap<URI, Object>(plugInRepresentations);
            Collection<Map.Entry<URI, Object>> factories = URIUtil.filterSort(resolutionURI, allFactories);

            if (factories.isEmpty())
            {
                continue;
            }

            // Ask each in turn to accept the type (the process of resolving the type)
            for (Map.Entry<URI, Object> entry : factories)
            {
                PlugInEventRepresentation factory = (PlugInEventRepresentation) entry.getValue();
                PlugInEventBeanReflectorContext context = new PlugInEventBeanReflectorContext(resolutionURI);
                if (factory.acceptsEventBeanResolution(context))
                {
                    PlugInEventBeanFactory beanFactory = factory.getEventBeanFactory(context);
                    if (beanFactory == null)
                    {
                        log.warn("Plug-in event representation returned a null bean factory, ignoring entry");
                        continue;
                    }
                    EventSenderURIDesc desc = new EventSenderURIDesc(beanFactory, resolutionURI, entry.getKey());
                    handlingFactories.add(desc);
                }
            }
        }

        if (handlingFactories.isEmpty())
        {
            throw new EventTypeException("Event sender for resolution URIs '" + Arrays.toString(uri)
                    + "' did not return at least one event representation's event factory");
        }

        return new EventSenderImpl(handlingFactories, epRuntime);
    }

    public BeanEventTypeFactory getBeanEventTypeFactory() {
        return beanEventAdapter;
    }

    /**
     * Set the legacy Java class type information.
     * @param classToLegacyConfigs is the legacy class configs
     */
    public void setClassLegacyConfigs(Map<String, ConfigurationEventTypeLegacy> classToLegacyConfigs)
    {
        beanEventAdapter.setClassToLegacyConfigs(classToLegacyConfigs);
    }

    /**
     * Sets the default property resolution style.
     * @param defaultPropertyResolutionStyle is the default style
     */
    public void setDefaultPropertyResolutionStyle(Configuration.PropertyResolutionStyle defaultPropertyResolutionStyle)
    {
        beanEventAdapter.setDefaultPropertyResolutionStyle(defaultPropertyResolutionStyle);
    }

    public EventType getExistsTypeByAlias(String eventTypeAlias)
    {
        if (eventTypeAlias == null)
        {
            throw new IllegalStateException("Null event type alias parameter");
        }
        return aliasToTypeMap.get(eventTypeAlias);
    }

    /**
     * Add an alias and class as an event type.
     * @param eventTypeAlias is the alias
     * @param clazz is the Java class to add
     * @return event type
     * @throws EventAdapterException to indicate an error constructing the type
     */
    public synchronized EventType addBeanType(String eventTypeAlias, Class clazz, boolean isConfigured) throws EventAdapterException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".addBeanType Adding " + eventTypeAlias + " for type " + clazz.getName());
        }

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().equals(clazz))
            {
                return existingType;
            }

            throw new EventAdapterException("Event type named '" + eventTypeAlias +
                    "' has already been declared with differing underlying type information:" + existingType.getUnderlyingType().getName() +
                    " versus " + clazz.getName());
        }

        EventType eventType = beanEventAdapter.createBeanType(eventTypeAlias, clazz, isConfigured);
        aliasToTypeMap.put(eventTypeAlias, eventType);

        return eventType;
    }

    /**
     * Create an event bean given an event of object id.
     * @param event is the event class
     * @return event
     */
    public EventBean adapterForBean(Object event)
    {
        EventType eventType = typesPerJavaBean.get(event.getClass());
        if (eventType == null)
        {
            // This will update the typesPerJavaBean mapping
            eventType = beanEventAdapter.createBeanType(event.getClass().getName(), event.getClass(), false);
        }
        return new BeanEventBean(event, eventType);
    }

    /**
     * Add an event type for the given Java class name.
     * @param eventTypeAlias is the alias
     * @param fullyQualClassName is the Java class name
     * @return event type
     * @throws EventAdapterException if the Class name cannot resolve or other error occured
     */
    public synchronized EventType addBeanType(String eventTypeAlias, String fullyQualClassName, boolean considerAutoAlias) throws EventAdapterException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".addBeanType Adding " + eventTypeAlias + " for type " + fullyQualClassName);
        }

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().getName().equals(fullyQualClassName))
            {
                if (log.isDebugEnabled())
                {
                    log.debug(".addBeanType Returning existing type for " + eventTypeAlias);
                }
                return existingType;
            }

            throw new EventAdapterException("Event type named '" + eventTypeAlias +
                    "' has already been declared with differing underlying type information: Class " + existingType.getUnderlyingType().getName() +
                    " versus " + fullyQualClassName);
        }

        // Try to resolve as a fully-qualified class name first
        Class clazz = null;
        try
        {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(fullyQualClassName, true, cl);
        }
        catch (ClassNotFoundException ex)
        {
            if (!considerAutoAlias)
            {
                throw new EventAdapterException("Event type or class named '" + fullyQualClassName + "' was not found", ex);
            }

            // Attempt to resolve from auto-alias packages
            for (String javaPackageName : javaPackageNames)
            {
                String generatedClassName = javaPackageName + "." + fullyQualClassName;
                try
                {
                    ClassLoader cl = Thread.currentThread().getContextClassLoader();
                    Class resolvedClass = Class.forName(generatedClassName, true, cl);
                    if (clazz != null)
                    {
                        throw new EventAdapterException("Failed to resolve alias '" + eventTypeAlias + "', the class was ambigously found both in " +
                                "package '" + clazz.getPackage().getName() + "' and in " +
                                "package '" + resolvedClass.getPackage().getName() + "'" , ex);
                    }
                    clazz = resolvedClass;
                }
                catch (ClassNotFoundException ex1)
                {
                    // expected, class may not exists in all packages
                }
            }
            if (clazz == null)
            {
                throw new EventAdapterException("Event type or class named '" + fullyQualClassName + "' was not found", ex);
            }
        }

        EventType eventType = beanEventAdapter.createBeanType(eventTypeAlias, clazz, true);
        aliasToTypeMap.put(eventTypeAlias, eventType);

        return eventType;
    }

    public synchronized EventType addNestableMapType(String eventTypeAlias, Map<String, Object> propertyTypes, Set<String> optionalSuperType, boolean isConfigured, boolean namedWindow, boolean insertInto) throws EventAdapterException
    {
        Pair<EventType[], Set<EventType>> mapSuperTypes = getMapSuperTypes(optionalSuperType);
        EventTypeMetadata metadata = EventTypeMetadata.createMapType(eventTypeAlias, isConfigured, namedWindow, insertInto);
        MapEventType newEventType = new MapEventType(metadata, eventTypeAlias, this, propertyTypes, mapSuperTypes.getFirst(), mapSuperTypes.getSecond());

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            // The existing type must be the same as the type createdStatement
            if (!newEventType.equals(existingType))
            {
                String message = newEventType.getEqualsMessage(existingType);
                throw new EventAdapterException("Event type named '" + eventTypeAlias +
                        "' has already been declared with differing column name or type information: " + message);
            }

            // Since it's the same, return the existing type
            return existingType;
        }

        aliasToTypeMap.put(eventTypeAlias, newEventType);

        return newEventType;
    }

    public EventBean adapterForMap(Map<String, Object> event, String eventTypeAlias) throws EventAdapterException
    {
        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType == null)
        {
            throw new EventAdapterException("Event type alias '" + eventTypeAlias + "' has not been defined");
        }

        return adaptorForTypedMap(event, existingType);
    }

    public EventBean adapterForDOM(Node node)
    {
        Node namedNode;
        if (node instanceof Document)
        {
            namedNode = ((Document) node).getDocumentElement();
        }
        else if (node instanceof Element)
        {
            namedNode = node;
        }
        else
        {
            throw new EPException("Unexpected DOM node of type '" + node.getClass() + "' encountered, please supply a Document or Element node");
        }

        String rootElementName = namedNode.getLocalName();
        if (rootElementName == null)
        {
            rootElementName = namedNode.getNodeName();
        }

        EventType eventType = xmldomRootElementNames.get(rootElementName);
        if (eventType == null)
        {
            throw new EventAdapterException("DOM event root element name '" + rootElementName +
                    "' has not been configured");
        }

        return new XMLEventBean(node, eventType);
    }

    /**
     * Add a configured XML DOM event type.
     * @param eventTypeAlias is the alias name of the event type
     * @param configurationEventTypeXMLDOM configures the event type schema and namespace and XPath
     * property information.
     */
    public synchronized EventType addXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        if (configurationEventTypeXMLDOM.getRootElementName() == null)
        {
            throw new EventAdapterException("Required root element name has not been supplied");
        }

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            String message = "Event type named '" + eventTypeAlias + "' has already been declared with differing column name or type information";
            if (!(existingType instanceof BaseXMLEventType))
            {
                throw new EventAdapterException(message);
            }
            ConfigurationEventTypeXMLDOM config = ((BaseXMLEventType) existingType).getConfigurationEventTypeXMLDOM();
            if (!config.equals(configurationEventTypeXMLDOM))
            {
                throw new EventAdapterException(message);
            }

            return existingType;
        }

        EventTypeMetadata metadata = EventTypeMetadata.createXMLType(eventTypeAlias);
        EventType type;
        if (configurationEventTypeXMLDOM.getSchemaResource() == null)
        {
            type = new SimpleXMLEventType(metadata, configurationEventTypeXMLDOM);
        }
        else
        {
            SchemaModel schemaModel = XSDSchemaMapper.loadAndMap(configurationEventTypeXMLDOM.getSchemaResource(), 2);            
            type = new SchemaXMLEventType(metadata, configurationEventTypeXMLDOM, schemaModel);
        }

        aliasToTypeMap.put(eventTypeAlias, type);
        xmldomRootElementNames.put(configurationEventTypeXMLDOM.getRootElementName(), type);

        return type;
    }

    public final EventBean adaptorForTypedMap(Map<String, Object> properties, EventType eventType)
    {
        return new MapEventBean(properties, eventType);
    }

    public synchronized EventType addWrapperType(String eventTypeAlias, EventType underlyingEventType, Map<String, Object> propertyTypes, boolean isNamedWindow, boolean isInsertInto) throws EventAdapterException
	{
        // If we are wrapping an underlying type that is itself a wrapper, then this is a special case
        if (underlyingEventType instanceof WrapperEventType)
        {
            WrapperEventType underlyingWrapperType = (WrapperEventType) underlyingEventType;

            // the underlying type becomes the type already wrapped
            // properties are a superset of the wrapped properties and the additional properties
            underlyingEventType = underlyingWrapperType.getUnderlyingEventType();
            Map<String, Object> propertiesSuperset = new HashMap<String, Object>();
            propertiesSuperset.putAll(underlyingWrapperType.getUnderlyingMapType().getTypes());
            propertiesSuperset.putAll(propertyTypes);
            propertyTypes = propertiesSuperset;
        }

        EventTypeMetadata metadata = EventTypeMetadata.createWrapper(eventTypeAlias, isNamedWindow, isInsertInto); 
        WrapperEventType newEventType = new WrapperEventType(metadata, eventTypeAlias, underlyingEventType, propertyTypes, this);

	    EventType existingType = aliasToTypeMap.get(eventTypeAlias);
	    if (existingType != null)
	    {
	        // The existing type must be the same as the type created
	        if (!newEventType.equals(existingType))
	        {
                // It is possible that the wrapped event type is compatible: a child type of the desired type
                String message = isCompatibleWrapper(existingType, underlyingEventType, propertyTypes); 
                if (message == null)
                {
                    return existingType;
                }

                throw new EventAdapterException("Event type named '" + eventTypeAlias +
	                    "' has already been declared with differing column name or type information: " + message);
	        }

	        // Since it's the same, return the existing type
	        return existingType;
	    }

	    aliasToTypeMap.put(eventTypeAlias, newEventType);

	    return newEventType;
	}

    /**
     * Returns true if the wrapper type is compatible with an existing wrapper type, for the reason that
     * the underlying event is a subtype of the existing underlying wrapper's type.
     * @param existingType is the existing wrapper type
     * @param underlyingType is the proposed new wrapper type's underlying type
     * @param propertyTypes is the additional properties
     * @return true for compatible, or false if not
     */
    public static String isCompatibleWrapper(EventType existingType, EventType underlyingType, Map<String, Object> propertyTypes)
    {
        if (!(existingType instanceof WrapperEventType))
        {
            return "Type '" + existingType.getName() + "' is not compatible";
        }
        WrapperEventType existingWrapper = (WrapperEventType) existingType;

        String message = MapEventType.isDeepEqualsProperties(existingType.getName(), existingWrapper.getUnderlyingMapType().getTypes(), propertyTypes); 
        if (message != null)
        {
            return message;
        }
        EventType existingUnderlyingType = existingWrapper.getUnderlyingEventType();

        // If one of the supertypes of the underlying type is the existing underlying type, we are compatible
        if (underlyingType.getSuperTypes() == null)
        {
            return "Type '" + existingType.getName() + "' is not compatible";
        }
        for (EventType superUnderlying : underlyingType.getSuperTypes())
        {
            if (superUnderlying == existingUnderlyingType)
            {
                return null;
            }
        }
        return "Type '" + existingType.getName() + "' is not compatible";
    }

    public final EventType createAnonymousMapType(Map<String, Object> propertyTypes) throws EventAdapterException
    {
        String alias = UuidGenerator.generate();
        EventTypeMetadata metadata = EventTypeMetadata.createAnonymous(alias);
        return new MapEventType(metadata, alias, this, propertyTypes, null, null);
    }

    public EventType createSemiAnonymousMapType(Map<String, Pair<EventType, String>> taggedEventTypes, Map<String, Pair<EventType, String>> arrayEventTypes, boolean isUsedByChildViews)
    {
        Map<String, Object> mapProperties = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, Pair<EventType, String>> entry : taggedEventTypes.entrySet())
        {
            mapProperties.put(entry.getKey(), entry.getValue().getFirst());
        }
        for (Map.Entry<String, Pair<EventType, String>> entry : arrayEventTypes.entrySet())
        {
            mapProperties.put(entry.getKey(), new EventType[] {entry.getValue().getFirst()});
        }
        return createAnonymousMapType(mapProperties);
    }

    public final EventType createAnonymousWrapperType(EventType underlyingEventType, Map<String, Object> propertyTypes) throws EventAdapterException
    {
        String alias = UuidGenerator.generate();
        EventTypeMetadata metadata = EventTypeMetadata.createAnonymous(alias);

        // If we are wrapping an underlying type that is itself a wrapper, then this is a special case: unwrap
        if (underlyingEventType instanceof WrapperEventType)
        {
            WrapperEventType underlyingWrapperType = (WrapperEventType) underlyingEventType;

            // the underlying type becomes the type already wrapped
            // properties are a superset of the wrapped properties and the additional properties
            underlyingEventType = underlyingWrapperType.getUnderlyingEventType();
            Map<String, Object> propertiesSuperset = new HashMap<String, Object>();
            propertiesSuperset.putAll(underlyingWrapperType.getUnderlyingMapType().getTypes());
            propertiesSuperset.putAll(propertyTypes);
            propertyTypes = propertiesSuperset;
        }

    	return new WrapperEventType(metadata, alias, underlyingEventType, propertyTypes, this);
    }

	public final EventBean adaptorForWrapper(EventBean event, Map<String, Object> properties, EventType eventType)
	{
        if (event instanceof WrapperEventBean)
        {
            WrapperEventBean wrapper = (WrapperEventBean) event;
            properties.putAll(wrapper.getDecoratingProperties());
            return new WrapperEventBean(wrapper.getUnderlyingEvent(), properties, eventType);
        }
        else
        {
            return new WrapperEventBean(event, properties, eventType);
        }
    }

    public final EventBean adapterForTypedBean(Object bean, BeanEventType eventType)
    {
        return new BeanEventBean(bean, eventType);
    }

    public void addAutoAliasPackage(String javaPackageName)
    {
        javaPackageNames.add(javaPackageName);
    }

    private Pair<EventType[], Set<EventType>> getMapSuperTypes(Set<String> optionalSuperTypes)
            throws EventAdapterException
    {
        if ((optionalSuperTypes == null) || (optionalSuperTypes.isEmpty()))
        {
            return new Pair<EventType[], Set<EventType>>(null,null);
        }

        EventType[] superTypes = new EventType[optionalSuperTypes.size()];
        Set<EventType> deepSuperTypes = new LinkedHashSet<EventType>();

        int count = 0;
        for (String superAlias : optionalSuperTypes)
        {
            EventType type = this.aliasToTypeMap.get(superAlias);
            if (type == null)
            {
                throw new EventAdapterException("Map supertype by alias '" + superAlias + "' could not be found");
            }
            if (!(type instanceof MapEventType))
            {
                throw new EventAdapterException("Supertype by alias '" + superAlias + "' is not a Map, expected a Map event type as a supertype");
            }
            superTypes[count++] = type;
            deepSuperTypes.add(type);
            addRecursiveSupertypes(deepSuperTypes, type);
        }
        return new Pair<EventType[], Set<EventType>>(superTypes,deepSuperTypes);
    }

    private static void addRecursiveSupertypes(Set<EventType> superTypes, EventType child)
    {
        if (child.getSuperTypes() != null)
        {
            for (int i = 0; i < child.getSuperTypes().length; i++)
            {
                superTypes.add(child.getSuperTypes()[i]);
                addRecursiveSupertypes(superTypes, child.getSuperTypes()[i]);
            }
        }
    }

    public EventBean[] typeCast(List<EventBean> events, EventType targetType)
    {
        EventBean[] convertedArray = new EventBean[events.size()];
        int count = 0;
        for (EventBean event : events)
        {
            EventBean converted;
            if (event instanceof DecoratingEventBean)
            {
                DecoratingEventBean wrapper = (DecoratingEventBean) event;
                converted = adaptorForWrapper(wrapper.getUnderlyingEvent(), wrapper.getDecoratingProperties(), targetType);
            }
            else if (event instanceof MappedEventBean)
            {
                MappedEventBean mapEvent = (MappedEventBean) event;
                converted = this.adaptorForTypedMap(mapEvent.getProperties(), targetType);
            }
            else
            {
                throw new EPException("Unknown event type " + event.getEventType());
            }
            convertedArray[count] = converted;
            count++;
        }
        return convertedArray;
    }

    public boolean removeType(String alias)
    {
        EventType eventType = aliasToTypeMap.remove(alias);
        if (eventType == null)
        {
            return false;
        }

        if (eventType instanceof BaseXMLEventType)
        {
            BaseXMLEventType baseXML = (BaseXMLEventType) eventType;
            xmldomRootElementNames.remove(baseXML.getRootElementName());
        }

        aliasToHandlerMap.remove(alias);
        return true;
    }
}
